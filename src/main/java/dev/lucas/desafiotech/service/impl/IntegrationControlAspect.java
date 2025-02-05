package dev.lucas.desafiotech.service.impl;

import dev.lucas.desafiotech.model.domain.Resale;
import dev.lucas.desafiotech.model.entities.IntegrationControlEntity;
import dev.lucas.desafiotech.model.enums.RequestStatus;
import dev.lucas.desafiotech.service.IntegrationControlService;
import dev.lucas.desafiotech.service.annotations.UpdateIntegration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.Objects;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class IntegrationControlAspect {

    private final IntegrationControlService integrationControlService;

    @Transactional
    @Around("@annotation(dev.lucas.desafiotech.service.annotations.UpdateIntegration) && args(resale, ..)")
    public void validateAndUpdateResellerIntegrations(ProceedingJoinPoint joinPoint, Resale resale) throws Throwable {

        log.info("Intercepting integration update for resale UUID: {}", resale.uuid());

        IntegrationControlEntity integrationControlEntity = integrationControlService.findByResaleUUID(resale.uuid());

        if (Objects.isNull(integrationControlEntity)) {
            log.info("Creating new integration control for resale UUID: {}", resale.uuid());
            integrationControlEntity = integrationControlService.createNewIntegrationControlForReseller(resale);
        }
        try {
            log.info("Proceeding with integration process for resale UUID: {}", resale.uuid());
            joinPoint.proceed();
            integrationControlEntity.setStatus(RequestStatus.AGUARDAR_RECEBIMENTO);
            log.info("Integration process completed successfully for resale UUID: {}", resale.uuid());
        } catch (Exception ex) {
            RequestStatus requestStatus = setStatusAfterError(joinPoint, integrationControlEntity);
            integrationControlEntity.setStatus(requestStatus);
            log.error("Error processing integration for resale UUID: {}. Updated status to {}", resale.uuid(), requestStatus);
            throw ex;
        } finally {
            integrationControlEntity.setAttempts(integrationControlEntity.getAttempts() + 1);
            log.info("Saving integration control with updated attempts: {} for resale UUID: {}", integrationControlEntity.getAttempts(), resale.uuid());
            integrationControlService.save(integrationControlEntity);
        }
    }

    private static RequestStatus setStatusAfterError(ProceedingJoinPoint joinPoint, IntegrationControlEntity integrationControlEntity) {
        int maxAttempts = getMaxAttempts(joinPoint);
        boolean exceededAttemptLimit = exceededAttemptLimit(integrationControlEntity, maxAttempts);
        if (exceededAttemptLimit) {
            log.warn("Integration {} for resale UUID {} exceeded the maximum retry limit!", integrationControlEntity.getUuid(), integrationControlEntity.getResale().getUuid());
        }
        return exceededAttemptLimit ? RequestStatus.ERRO : RequestStatus.REPROCESSAR;
    }

    private static boolean exceededAttemptLimit(IntegrationControlEntity integrationControlEntity, int maxAttempts) {
        return integrationControlEntity.getAttempts() >= maxAttempts;
    }

    private static int getMaxAttempts(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        UpdateIntegration annotation = method.getAnnotation(UpdateIntegration.class);
        return annotation != null ? annotation.maxAttempts() : 3;
    }
}

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

        log.info("Interceptando atualização de integração para revenda: {}", resale.uuid());

        IntegrationControlEntity integrationControlEntity = integrationControlService.findByResaleUUID(resale.uuid());;

        if (Objects.isNull(integrationControlEntity)) {
            integrationControlEntity = integrationControlService.createNewIntegrationControlForReseller(resale);
        }
        try {
            joinPoint.proceed();
            integrationControlEntity.setStatus(RequestStatus.AGUARDAR_RECEBIMENTO);

        } catch (Exception ex) {

            RequestStatus requestStatus = setStatusAfterError(joinPoint, integrationControlEntity);
            integrationControlEntity.setStatus(requestStatus);
            log.error("Erro ao processar integração para resale {}. Status atualizado para {}.", resale.uuid(), requestStatus);
            throw ex;

        } finally {
            integrationControlEntity.setAttempts(integrationControlEntity.getAttempts() + 1);
            integrationControlService.save(integrationControlEntity);
        }
    }

    private static RequestStatus setStatusAfterError(ProceedingJoinPoint joinPoint, IntegrationControlEntity integrationControlEntity) {
        int maxTentativas = getMaxTentativas(joinPoint);
        boolean excedeuLimiteDeTentativas = excedeuLimiteDeTentativas(integrationControlEntity, maxTentativas);
        if (excedeuLimiteDeTentativas) {
            log.info("A integração {} para a resale {} excedeu o limite maximo para reprocessamento automatico!",
                    integrationControlEntity.getUuid(), integrationControlEntity.getResale().getUuid());
        }
        return excedeuLimiteDeTentativas ? RequestStatus.ERRO : RequestStatus.REPROCESSAR;
    }

    private static boolean excedeuLimiteDeTentativas(IntegrationControlEntity integrationControlEntity, int maxTentativas) {
        return integrationControlEntity.getAttempts() >= maxTentativas;
    }

    private static int getMaxTentativas(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        UpdateIntegration annotation = method.getAnnotation(UpdateIntegration.class);
        return annotation != null ? annotation.maxAttempts() : 3;
    }
}

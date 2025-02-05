package dev.lucas.desafiotech.service.impl;

import dev.lucas.desafiotech.mappers.IntegrationControlMapper;
import dev.lucas.desafiotech.model.domain.IntegrationControl;
import dev.lucas.desafiotech.model.domain.Resale;
import dev.lucas.desafiotech.model.entities.IntegrationControlEntity;
import dev.lucas.desafiotech.model.enums.RequestStatus;
import dev.lucas.desafiotech.repository.IntegrationControlRepository;
import dev.lucas.desafiotech.service.IntegrationControlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class IntegrationControlServiceImpl implements IntegrationControlService {

    private final IntegrationControlRepository integrationControlRepository;
    private final IntegrationControlMapper integrationControlMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(IntegrationControlEntity integrationControl) {
        integrationControlRepository.save(integrationControl);
    }

    @Override
    public IntegrationControlEntity findByResaleUUID(UUID uuid) {
        return integrationControlRepository.findByResaleUuid(uuid);
    }

    @Override
    public IntegrationControlEntity createNewIntegrationControlForReseller(Resale resale) {
        return integrationControlMapper.buildIntegrationControlEntity(resale);
    }

    @Override
    public IntegrationControl findIntegrationControlByResaleUUID(UUID resaleUuid) {
        IntegrationControlEntity integrationControlEntity = integrationControlRepository.findByResaleUuid(resaleUuid);
        return integrationControlMapper.to(integrationControlEntity);
    }

    @Override
    public void updateStatus(UUID uuid, UUID resaleUuid, RequestStatus status) {
        IntegrationControlEntity integrationControlEntity = integrationControlRepository.findByUuidAndResaleUuid(uuid, resaleUuid);
        integrationControlEntity.setStatus(status);
        integrationControlRepository.save(integrationControlEntity);
    }
}

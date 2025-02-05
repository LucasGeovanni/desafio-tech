package dev.lucas.desafiotech.service;

import dev.lucas.desafiotech.model.domain.IntegrationControl;
import dev.lucas.desafiotech.model.domain.Resale;
import dev.lucas.desafiotech.model.entities.IntegrationControlEntity;
import dev.lucas.desafiotech.model.enums.RequestStatus;

import java.util.UUID;

public interface IntegrationControlService {

    void save(IntegrationControlEntity integrationControl);

    IntegrationControlEntity findByResaleUUID(UUID uuid);

    IntegrationControlEntity createNewIntegrationControlForReseller(Resale resale);

    IntegrationControl findIntegrationControlByResaleUUID(UUID resaleUuid);

    void updateStatus(UUID uuid, UUID resaleUuid, RequestStatus status);
}

package dev.lucas.desafiotech.repository;

import dev.lucas.desafiotech.model.entities.IntegrationControlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface IntegrationControlRepository extends JpaRepository<IntegrationControlEntity, Long> {

    @Query("SELECT u FROM IntegrationControlEntity u WHERE u.resale.uuid = :resaleUuid")
    IntegrationControlEntity findByResaleUuid(UUID resaleUuid);

    @Query("SELECT u FROM IntegrationControlEntity u WHERE u.uuid = :uuid AND u.resale.uuid = :resaleUuid")
    IntegrationControlEntity findByUuidAndResaleUuid(UUID uuid, UUID resaleUuid);

}

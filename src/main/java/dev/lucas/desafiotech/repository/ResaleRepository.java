package dev.lucas.desafiotech.repository;

import dev.lucas.desafiotech.model.entities.ResaleEntity;
import dev.lucas.desafiotech.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ResaleRepository extends JpaRepository<ResaleEntity, Long> {

    boolean existsByCnpj(String cnpj);

    Optional<ResaleEntity> findByUuid(UUID uuid);

    @Query("""
        SELECT p.resale FROM OrderEntity p
            JOIN p.itens i
            LEFT JOIN IntegrationControlOrderEntity cp
            ON cp.orderId = p.id
            LEFT JOIN IntegrationControlEntity ci
            ON ci.id = cp.integrationControlId
        WHERE p.status = :status
            AND ci.status is NULL OR ci.status != 'ERRO'
        GROUP BY p.resale
        HAVING SUM(i.quantidade) >= :orderQuantity
    """)
    List<ResaleEntity> findResellersForOrderIssuance(OrderStatus status, Integer orderQuantity);

}

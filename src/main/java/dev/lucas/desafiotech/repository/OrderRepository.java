package dev.lucas.desafiotech.repository;

import dev.lucas.desafiotech.model.domain.Order;
import dev.lucas.desafiotech.model.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, Long>  {

    Optional<OrderEntity> findByUuid(UUID uuid);

    @Query("SELECT u FROM OrderEntity u WHERE u.resale.uuid = :resaleUuid")
    List<OrderEntity> buscarOrdersPorResaleUuid(UUID resaleUuid);

}

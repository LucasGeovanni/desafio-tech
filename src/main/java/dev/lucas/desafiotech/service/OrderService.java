package dev.lucas.desafiotech.service;

import dev.lucas.desafiotech.model.domain.Order;
import dev.lucas.desafiotech.model.enums.OrderStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    Order save(UUID resaleUuid, Order order);

    Order findByUUID(UUID uuid);

    List<Order> findByResaleUUID(UUID resaleUuid);
}

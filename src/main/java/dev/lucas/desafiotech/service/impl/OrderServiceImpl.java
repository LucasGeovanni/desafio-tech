package dev.lucas.desafiotech.service.impl;

import dev.lucas.desafiotech.exception.NotFoundException;
import dev.lucas.desafiotech.mappers.OrderMapper;
import dev.lucas.desafiotech.model.domain.Order;
import dev.lucas.desafiotech.model.domain.Resale;
import dev.lucas.desafiotech.model.entities.OrderEntity;
import dev.lucas.desafiotech.repository.OrderRepository;
import dev.lucas.desafiotech.service.OrderService;
import dev.lucas.desafiotech.service.ResaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ResaleService resaleService;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public Order save(UUID resaleUuid, Order order) {
        log.info("Creating order {} for resale UUID: {}", order, resaleUuid);
        Resale resale = resaleService.findByUUID(resaleUuid);
        OrderEntity orderEntity = orderRepository.save(orderMapper.to(order, resale.id()));
        log.info("Order created successfully with UUID: {}", orderEntity.getUuid());
        return orderMapper.to(orderEntity);
    }

    @Override
    public Order findByUUID(UUID uuid) {
        log.info("Fetching order by UUID: {}", uuid);
        return orderRepository.findByUuid(uuid)
                .map(orderMapper::to)
                .orElseThrow(() -> {
                    log.warn("Order not found for UUID: {}", uuid);
                    return new NotFoundException("Order not found");
                });
    }

    @Override
    public List<Order> findByResaleUUID(UUID resaleUuid) {
        log.info("Fetching orders for resale UUID: {}", resaleUuid);
        List<Order> orders = orderMapper.to(orderRepository.buscarOrdersPorResaleUuid(resaleUuid));
        log.info("Found {} orders for resale UUID: {}", orders.size(), resaleUuid);
        return orders;
    }
}

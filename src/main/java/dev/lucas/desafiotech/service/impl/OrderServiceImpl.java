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
        log.info("Criando order {} para a resale de codigo {}", order, resaleUuid);
        Resale resale = resaleService.findByUUID(resaleUuid);
        OrderEntity orderEntity = orderRepository.save(orderMapper.to(order, resale.id()));
        return orderMapper.to(orderEntity);
    }

    @Override
    public Order findByUUID(UUID uuid) {
        log.info("Buscando order por codigo {}", uuid);
        return orderRepository.findByUuid(uuid)
                .map(orderMapper::to)
                .orElseThrow(() -> new NotFoundException("Order nao encontrado"));
    }

    @Override
    public List<Order> findByResaleUUID(UUID resaleUuid) {
        return orderMapper.to(orderRepository.buscarOrdersPorResaleUuid(resaleUuid));
    }

}
package dev.lucas.desafiotech.model.domain;

import dev.lucas.desafiotech.model.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record Order(Long id,
                     UUID uuid,
                     LocalDateTime dataOrder,
                     OrderStatus status,
                     String cliente,
                     List<OrderItem> itens) {
}

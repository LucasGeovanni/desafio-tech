package dev.lucas.desafiotech.model.dto;

import dev.lucas.desafiotech.model.domain.OrderItem;
import dev.lucas.desafiotech.model.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderResponse(UUID uuid,
                             LocalDateTime dataOrder,
                             OrderStatus status,
                             String cliente,
                             List<OrderItem> itens) {
}

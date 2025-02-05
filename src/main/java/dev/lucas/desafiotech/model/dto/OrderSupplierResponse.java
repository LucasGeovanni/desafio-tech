package dev.lucas.desafiotech.model.dto;

import dev.lucas.desafiotech.model.enums.RequestStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderSupplierResponse(UUID uuid,
                                       UUID resaleCode,
                                       List<OrderResponse> orders,
                                       LocalDateTime dataOrder,
                                       Integer qtdeTentativas,
                                       RequestStatus requestStatus,
                                       String orderCodeSupplier) {
}

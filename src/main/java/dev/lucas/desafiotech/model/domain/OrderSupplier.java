package dev.lucas.desafiotech.model.domain;

import dev.lucas.desafiotech.model.dto.OrderResponse;
import dev.lucas.desafiotech.model.enums.RequestStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderSupplier(Long id,
                               UUID uuid,
                               UUID resaleCode,
                               List<OrderResponse> orders,
                               LocalDateTime dataOrder,
                               Integer qtdeTentativas,
                               RequestStatus requestStatus,
                               String orderCodeSupplier) {
}

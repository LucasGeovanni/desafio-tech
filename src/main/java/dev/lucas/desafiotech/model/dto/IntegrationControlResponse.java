package dev.lucas.desafiotech.model.dto;

import dev.lucas.desafiotech.model.enums.RequestStatus;

import java.util.List;
import java.util.UUID;

public record IntegrationControlResponse(UUID uuid,
                                         UUID resaleCode,
                                         List<OrderResponse> orders,
                                         RequestStatus status
                                         ) {
}

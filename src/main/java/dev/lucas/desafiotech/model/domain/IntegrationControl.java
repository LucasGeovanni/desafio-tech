package dev.lucas.desafiotech.model.domain;

import dev.lucas.desafiotech.model.enums.RequestStatus;

import java.util.List;
import java.util.UUID;

public record IntegrationControl(UUID uuid,
                                UUID resaleCode,
                                List<Order> orders,
                                RequestStatus status) {
}

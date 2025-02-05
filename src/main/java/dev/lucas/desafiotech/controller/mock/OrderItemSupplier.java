package dev.lucas.desafiotech.controller.mock;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record OrderItemSupplier(
        @NotNull UUID orderCode,
        @NotBlank String product,
        Integer quantity) {
}

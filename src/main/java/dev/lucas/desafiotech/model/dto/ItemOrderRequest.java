package dev.lucas.desafiotech.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ItemOrderRequest(
        @NotBlank String produto,
        @Min(1) Integer quantidade
) {}

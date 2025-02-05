package dev.lucas.desafiotech.model.dto;

import dev.lucas.desafiotech.model.enums.PhoneType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PhoneRequest(@NotBlank String ddd,
                           @NotBlank String numero,
                           @NotNull PhoneType tipo) {}


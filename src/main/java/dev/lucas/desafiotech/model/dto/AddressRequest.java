package dev.lucas.desafiotech.model.dto;

import jakarta.validation.constraints.NotBlank;

public record AddressRequest(@NotBlank String rua,
                             @NotBlank String numero,
                             @NotBlank String bairro,
                             @NotBlank String cep,
                             @NotBlank String cidade,
                             @NotBlank String estado,
                             String complemento) {}


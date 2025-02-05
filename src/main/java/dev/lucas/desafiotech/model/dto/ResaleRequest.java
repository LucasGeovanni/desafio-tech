package dev.lucas.desafiotech.model.dto;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ResaleRequest(
        @NotBlank String cnpj,
        @NotBlank String razaoSocial,
        @NotBlank String nomeFantasia,
        @NotBlank String email,
        List<PhoneRequest> phones,
        @NotNull List<AddressRequest> address
) {}


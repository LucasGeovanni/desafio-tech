package dev.lucas.desafiotech.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record OrderRequest(
        @NotBlank String cliente,
        @NotEmpty List<ItemOrderRequest> itens) {}
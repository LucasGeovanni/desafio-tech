package dev.lucas.desafiotech.api.v1.mock;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record SupplierRequest(@NotNull UUID resaleCode,
                                @NotEmpty List<OrderItemSupplier> itens,
                                @NotNull OrderAddressSupplierRequest address) {
}

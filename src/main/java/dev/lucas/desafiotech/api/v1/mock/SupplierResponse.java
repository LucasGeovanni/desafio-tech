package dev.lucas.desafiotech.api.v1.mock;

import java.util.List;

public record SupplierResponse(
        String orderCodeSupplier,
                                 String resaleCode,
                                 List<OrderItemSupplier> itens) {
}

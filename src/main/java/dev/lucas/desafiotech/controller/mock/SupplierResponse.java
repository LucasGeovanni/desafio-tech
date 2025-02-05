package dev.lucas.desafiotech.controller.mock;

import java.util.List;

public record SupplierResponse(
        String orderCodeSupplier,
                                 String resaleCode,
                                 List<OrderItemSupplier> itens) {
}

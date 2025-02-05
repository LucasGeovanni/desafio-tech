package dev.lucas.desafiotech.service;

import dev.lucas.desafiotech.model.domain.Resale;
import dev.lucas.desafiotech.service.annotations.UpdateIntegration;

public interface OrderSupplierService {
    @UpdateIntegration(maxAttempts = 5)
    void createOrderSupplier(Resale resale);
}

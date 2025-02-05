package dev.lucas.desafiotech.service;

import dev.lucas.desafiotech.model.domain.Resale;
import dev.lucas.desafiotech.model.enums.OrderStatus;

import java.util.List;
import java.util.UUID;

public interface ResaleService {
    UUID save(Resale resale);

    List<Resale> findAll();

    Resale findByUUID(UUID uuid);

    List<Resale> findPendingOrdersWithoutSupplier(OrderStatus orderStatus, Integer qtdMin);
}

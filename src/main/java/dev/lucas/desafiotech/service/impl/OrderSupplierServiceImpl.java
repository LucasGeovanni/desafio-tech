package dev.lucas.desafiotech.service.impl;

import dev.lucas.desafiotech.api.v1.mock.OrderAddressSupplierRequest;
import dev.lucas.desafiotech.api.v1.mock.SupplierRequest;
import dev.lucas.desafiotech.api.v1.mock.OrderItemSupplier;
import dev.lucas.desafiotech.client.SupplierClient;
import dev.lucas.desafiotech.mappers.OrderSupplierMapper;
import dev.lucas.desafiotech.model.domain.Resale;
import dev.lucas.desafiotech.service.OrderSupplierService;
import dev.lucas.desafiotech.service.annotations.UpdateIntegration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderSupplierServiceImpl implements OrderSupplierService {

    private final SupplierClient fornecedorClient;
    private final OrderSupplierMapper orderSupplierMapper;

    @Override
    @UpdateIntegration(maxAttempts = 5)
    public void createOrderSupplier(Resale resale) {
        log.info("Criando order fornecedor para a resale UUID: {}", resale.uuid());
        try {

            List<OrderItemSupplier> itensOrders = consolidateOrders(resale);
            fornecedorClient.solicitacaoOrder(new SupplierRequest(resale.uuid(), itensOrders, mapAddress(resale)));
            log.info("Order para fornecedor {} criado com sucesso!", resale.uuid());

        } catch (Exception e) {
            log.error("Erro ao criar order para fornecedor. Resale UUID: {}, Erro: {}", resale.uuid(), e.getMessage());
            throw e;
        }
    }

    private static List<OrderItemSupplier> consolidateOrders(Resale resale) {
        return resale.orders().stream()
                .flatMap(order -> order.itens().stream()
                        .map(item -> new OrderItemSupplier(order.uuid(), item.produto(), item.quantidade())))
                .toList();
    }

    private OrderAddressSupplierRequest mapAddress(Resale resale) {
        return resale.address().stream()
                .findFirst()
                .map(orderSupplierMapper::to).orElse(null);
    }

}

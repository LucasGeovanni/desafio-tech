package dev.lucas.desafiotech.service.impl;

import dev.lucas.desafiotech.model.domain.Resale;
import dev.lucas.desafiotech.model.enums.OrderStatus;
import dev.lucas.desafiotech.service.OrderSupplierService;
import dev.lucas.desafiotech.service.ResaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderProcessingScheduler {

    private static final int QUANTIDADE_MINIMA_ENVIO = 1000;

    private final ResaleService revendaService;
    private final OrderSupplierService controleOrderService;

    @Scheduled(fixedRate = 10000)
    @Transactional
    public void processarOrders() {
        log.info("Buscando revendas com orders pendentes para envio ao fornecedor...");
        List<Resale> revendas = revendaService.findPendingOrdersWithoutSupplier(OrderStatus.PENDENTE, QUANTIDADE_MINIMA_ENVIO);
        log.info("Quantidade de revendas com orders pendentes encontradas: {}", revendas.size());
        revendas.forEach(resale-> {
            try {
                controleOrderService.createOrderSupplier(resale);
            } catch (Exception e) {
                log.error("Erro ao processar os orders para a revenda: {} {}", resale.uuid(), e.getMessage());
            }
        });
    }
}

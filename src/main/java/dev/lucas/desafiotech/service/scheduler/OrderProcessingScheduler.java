package dev.lucas.desafiotech.service.scheduler;

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

    private static final int MINIMUM_ORDER_QUANTITY = 1000;

    private final ResaleService resaleService;
    private final OrderSupplierService orderSupplierService;

    @Scheduled(cron = "${scheduler.cron}")
    @Transactional
    public void processOrders() {
        log.info("Fetching resales with pending orders for supplier submission...");
        List<Resale> resales = resaleService.findPendingOrdersWithoutSupplier(OrderStatus.PENDENTE, MINIMUM_ORDER_QUANTITY);
        log.info("Number of resales with pending orders found: {}", resales.size());

        resales.forEach(resale -> {
            try {
                log.info("Processing orders for resale UUID: {}", resale.uuid());
                orderSupplierService.createOrderSupplier(resale);
                log.info("Successfully processed orders for resale UUID: {}", resale.uuid());
            } catch (Exception e) {
                log.error("Error processing orders for resale UUID: {} - Error: {}", resale.uuid(), e.getMessage());
            }
        });
    }
}

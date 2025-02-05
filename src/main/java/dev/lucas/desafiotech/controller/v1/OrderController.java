package dev.lucas.desafiotech.controller.v1;

import dev.lucas.desafiotech.controller.v1.api.OrderControllerApi;
import dev.lucas.desafiotech.mappers.OrderMapper;
import dev.lucas.desafiotech.model.domain.Order;
import dev.lucas.desafiotech.model.dto.OrderRequest;
import dev.lucas.desafiotech.model.dto.OrderResponse;
import dev.lucas.desafiotech.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController implements OrderControllerApi {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @PostMapping("/{resaleUuid}")
    public ResponseEntity<OrderResponse> createOrder(@PathVariable UUID resaleUuid, @Valid @RequestBody OrderRequest request) {
        log.info("Received request to create order for resale UUID: {}", resaleUuid);
        Order order = orderService.save(resaleUuid, orderMapper.to(request));
        log.info("Order created successfully with UUID: {}", order.uuid());
        URI location = URI.create(String.format("/orders/%s", order.uuid()));
        return ResponseEntity.created(location).body(orderMapper.toResponse(order));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<OrderResponse> findOrder(@PathVariable UUID uuid) {
        log.info("Fetching order with UUID: {}", uuid);
        Order order = orderService.findByUUID(uuid);
        log.info("Order found: {}", order);
        return ResponseEntity.ok(orderMapper.toResponse(order));
    }

    @GetMapping("/resale/{resaleUuid}")
    public ResponseEntity<List<OrderResponse>> findOrderByResaleUUID(@PathVariable("resaleUuid") UUID resaleUuid) {
        log.info("Fetching orders for resale UUID: {}", resaleUuid);
        List<Order> orders = orderService.findByResaleUUID(resaleUuid);
        log.info("Found {} orders for resale UUID: {}", orders.size(), resaleUuid);
        return ResponseEntity.ok(orderMapper.toResponse(orders));
    }
}

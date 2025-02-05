package dev.lucas.desafiotech.api.v1;

import dev.lucas.desafiotech.mappers.OrderMapper;
import dev.lucas.desafiotech.model.domain.Order;
import dev.lucas.desafiotech.model.dto.OrderRequest;
import dev.lucas.desafiotech.model.dto.OrderResponse;
import dev.lucas.desafiotech.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @PostMapping("/{resaleUuid}")
    public ResponseEntity<OrderResponse> createOrder(@PathVariable UUID resaleUuid, @Valid @RequestBody OrderRequest request) {
        Order order = orderService.save(resaleUuid, orderMapper.to(request));
        URI location = URI.create(String.format("/orders/%s", order.uuid()));
        return ResponseEntity.created(location).body(orderMapper.toResponse(order));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<OrderResponse> findOrder(@PathVariable UUID uuid) {
        Order order = orderService.findByUUID(uuid);
        return ResponseEntity.ok(orderMapper.toResponse(order));
    }

    @GetMapping("/resale/{resaleUuid}")
    public ResponseEntity<List<OrderResponse>> findOrderByResaleUUID(@PathVariable("resaleUuid") UUID resaleUuid) {

        List<Order> orders = orderService.findByResaleUUID(resaleUuid);

        return ResponseEntity.ok(orderMapper.toResponse(orders));
    }
}
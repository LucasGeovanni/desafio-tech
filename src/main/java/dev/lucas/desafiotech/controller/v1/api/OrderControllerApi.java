package dev.lucas.desafiotech.controller.v1.api;


import dev.lucas.desafiotech.model.dto.OrderRequest;
import dev.lucas.desafiotech.model.dto.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public interface OrderControllerApi {

    @Operation(summary = "Create an order for a resale")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping("/{resaleUuid}")
    ResponseEntity<OrderResponse> createOrder(@PathVariable UUID resaleUuid, @RequestBody OrderRequest request);

    @Operation(summary = "Find an order by UUID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order found"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @GetMapping("/{uuid}")
    ResponseEntity<OrderResponse> findOrder(@PathVariable UUID uuid);

    @Operation(summary = "Find orders by resale UUID")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "List of orders found"))
    @GetMapping("/resale/{resaleUuid}")
    ResponseEntity<List<OrderResponse>> findOrderByResaleUUID(@PathVariable("resaleUuid") UUID resaleUuid);
}


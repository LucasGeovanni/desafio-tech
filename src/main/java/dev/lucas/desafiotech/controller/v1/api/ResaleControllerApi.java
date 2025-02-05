package dev.lucas.desafiotech.controller.v1.api;

import dev.lucas.desafiotech.model.dto.IntegrationControlResponse;
import dev.lucas.desafiotech.model.dto.ResaleRequest;
import dev.lucas.desafiotech.model.dto.ResaleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public interface ResaleControllerApi {

    @Operation(summary = "Create a new resale")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Resale created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping
    ResponseEntity<Void> createNew(@RequestBody ResaleRequest request);

    @Operation(summary = "Retrieve all resales")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "List of resales"))
    @GetMapping
    ResponseEntity<List<ResaleResponse>> findAll();

    @Operation(summary = "Find a resale by UUID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resale found"),
            @ApiResponse(responseCode = "404", description = "Resale not found")
    })
    @GetMapping("/{uuid}")
    ResponseEntity<ResaleResponse> findByUUID(@PathVariable UUID uuid);

    @Operation(summary = "Find integration control by resale UUID")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Integration control found"))
    @GetMapping("/{uuid}/control")
    ResponseEntity<IntegrationControlResponse> findControleByResaleUUID(@PathVariable UUID uuid);

    @Operation(summary = "Reprocess a resale order")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Reprocessing initiated"))
    @PostMapping("/control/reprocess")
    ResponseEntity<Void> reprocess(@RequestParam("uuid") UUID uuid,
                                   @RequestParam("resaleUuid") UUID resaleUuid);
}

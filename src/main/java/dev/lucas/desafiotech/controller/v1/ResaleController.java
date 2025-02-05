package dev.lucas.desafiotech.controller.v1;

import dev.lucas.desafiotech.controller.v1.api.ResaleControllerApi;
import dev.lucas.desafiotech.mappers.IntegrationControlMapper;
import dev.lucas.desafiotech.mappers.ResaleMapper;
import dev.lucas.desafiotech.model.domain.IntegrationControl;
import dev.lucas.desafiotech.model.domain.Resale;
import dev.lucas.desafiotech.model.dto.IntegrationControlResponse;
import dev.lucas.desafiotech.model.dto.ResaleRequest;
import dev.lucas.desafiotech.model.dto.ResaleResponse;
import dev.lucas.desafiotech.model.enums.RequestStatus;
import dev.lucas.desafiotech.service.IntegrationControlService;
import dev.lucas.desafiotech.service.OrderSupplierService;
import dev.lucas.desafiotech.service.ResaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/resale")
@RequiredArgsConstructor
@Slf4j
public class ResaleController implements ResaleControllerApi {

    private final ResaleService resaleService;
    private final OrderSupplierService orderSupplierService;
    private final IntegrationControlService integrationControlService;
    private final ResaleMapper resaleMapper;
    private final IntegrationControlMapper integrationControlMapper;

    @PostMapping
    public ResponseEntity<Void> createNew(@Valid @RequestBody ResaleRequest request) {
        log.info("Received request to create a new resale: {}", request);
        Resale resale = resaleMapper.to(request);
        UUID uuid = resaleService.save(resale);
        log.info("Resale created successfully with UUID: {}", uuid);
        URI location = URI.create(String.format("/resale/%s", uuid));
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<ResaleResponse>> findAll() {
        log.info("Fetching all resales");
        List<ResaleResponse> response = resaleService.findAll().stream().map(resaleMapper::toResponse).toList();
        log.info("Found {} resales", response.size());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ResaleResponse> findByUUID(@PathVariable UUID uuid) {
        log.info("Fetching resale with UUID: {}", uuid);
        Resale resale = resaleService.findByUUID(uuid);
        log.info("Resale found: {}", resale);
        return ResponseEntity.ok(resaleMapper.toResponse(resale));
    }

    @GetMapping("/{uuid}/control")
    public ResponseEntity<IntegrationControlResponse> findControleByResaleUUID(@PathVariable UUID uuid) {
        log.info("Fetching integration control for resale UUID: {}", uuid);
        IntegrationControl integrationControl = integrationControlService.findIntegrationControlByResaleUUID(uuid);
        log.info("Integration control found: {}", integrationControl);
        return ResponseEntity.ok(integrationControlMapper.to(integrationControl));
    }

    @PostMapping("/control/reprocess")
    public ResponseEntity<Void> reprocess(@RequestParam("uuid") UUID uuid,
                                          @RequestParam("resaleUuid") UUID resaleUuid) {
        log.info("Received request to reprocess resale UUID: {}, integration control UUID: {}", resaleUuid, uuid);
        Resale resale = resaleService.findByUUID(resaleUuid);
        integrationControlService.updateStatus(uuid, resaleUuid, RequestStatus.PENDENTE);
        log.info("Updated integration control status to PENDENTE for resale UUID: {}", resaleUuid);
        orderSupplierService.createOrderSupplier(resale);
        log.info("Order supplier created for resale UUID: {}", resaleUuid);
        return ResponseEntity.ok().build();
    }
}
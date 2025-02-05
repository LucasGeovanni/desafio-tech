package dev.lucas.desafiotech.api.v1;

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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/resale")
@RequiredArgsConstructor
public class ResaleController {

    private final ResaleService resaleService;
    private final OrderSupplierService orderSupplierService;
    private final IntegrationControlService integrationControlService;
    private final ResaleMapper resaleMapper;
    private final IntegrationControlMapper integrationControlMapper;


    @PostMapping
    public ResponseEntity<Void> createNew(@Valid @RequestBody ResaleRequest request) {

        Resale resale = resaleMapper.to(request);
        UUID uuid = resaleService.save(resale);
        URI location = URI.create(String.format("/resale/%s", uuid));

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<ResaleResponse>> findAll() {
        return ResponseEntity.ok(resaleService.findAll().stream().map(resaleMapper::toResponse).toList());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Resale> findByUUID(@PathVariable UUID uuid) {
        return ResponseEntity.ok(resaleService.findByUUID(uuid));
    }

    @GetMapping("/{uuid}/control")
    public ResponseEntity<IntegrationControlResponse> findControleByResaleUUID(@PathVariable UUID uuid) {

        IntegrationControl integrationControl = integrationControlService.findIntegrationControlByResaleUUID(uuid);

        return ResponseEntity.ok(integrationControlMapper.to(integrationControl));
    }

    @PostMapping("/control/reprocess")
    public ResponseEntity<Void> reprocessar(@RequestParam("uuid") UUID uuid,
                                            @RequestParam("resaleUuid") UUID resaleUuid) {

        Resale resale = resaleService.findByUUID(resaleUuid);
        integrationControlService.updateStatus(uuid, resaleUuid, RequestStatus.PENDENTE);
        orderSupplierService.createOrderSupplier(resale);

        return ResponseEntity.ok().build();
    }

}

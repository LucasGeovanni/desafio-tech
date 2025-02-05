package dev.lucas.desafiotech.api.v1.mock;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/supplier")
@RequiredArgsConstructor
public class SupplierMockController {


    @PostMapping
    public ResponseEntity<Object> newOrder(@Valid @RequestBody SupplierRequest fornecedorRequest) {
        return orderGenerate(fornecedorRequest);
    }

    private ResponseEntity<Object> orderGenerate(SupplierRequest supplierRequest) {

        int qtts = supplierRequest.itens()
                .stream()
                .mapToInt(OrderItemSupplier::quantity)
                .sum();
        Random random = new Random();
        if (Objects.isNull(supplierRequest.resaleCode()) || qtts < 1000) {
            return ResponseEntity.unprocessableEntity().build();
        }
        if (random.nextBoolean() || qtts == 1111) { // apenas para testar um cenario de reprocessamento
            return new ResponseEntity<>("Serviço temporariamente indisponível. Tente novamente mais tarde.", HttpStatus.SERVICE_UNAVAILABLE);
        }
        return new ResponseEntity<>(new SupplierResponse(UUID.randomUUID().toString(), supplierRequest.resaleCode().toString(),supplierRequest.itens()), HttpStatus.CREATED);
    }

}

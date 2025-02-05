package dev.lucas.desafiotech.client;

import dev.lucas.desafiotech.api.v1.mock.SupplierRequest;
import dev.lucas.desafiotech.api.v1.mock.SupplierResponse;
import dev.lucas.desafiotech.config.FeignRetryConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "SupplierApi",
            url = "${fornecedor.host}",
            path = "/api/v1/",
            configuration = FeignRetryConfig.class
)
public interface SupplierClient {

    @PostMapping("/fornecedor")
    SupplierResponse solicitacaoOrder(@RequestBody SupplierRequest fornecedorRequest);

}

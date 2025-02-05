package dev.lucas.desafiotech.feign;

import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

import java.util.Date;

public class CustomFeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == HttpStatus.SERVICE_UNAVAILABLE.value()) {
            return new RetryableException(
                    response.status(),
                    "Serviço temporariamente indisponível, tentando novamente...",
                    response.request().httpMethod(),
                    new Date(),
                    response.request()
            );
        }
        return defaultErrorDecoder.decode(methodKey, response);
    }
}

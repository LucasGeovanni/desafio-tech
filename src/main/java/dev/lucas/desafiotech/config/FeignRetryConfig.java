package dev.lucas.desafiotech.config;

import dev.lucas.desafiotech.feign.CustomFeignErrorDecoder;
import dev.lucas.desafiotech.feign.CustomFeignRetryer;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignRetryConfig {

    // parametrizar no propriedades int maxAttempts, long interval
    @Bean
    public Retryer feignRetryer() {
        return new CustomFeignRetryer(3, 2000);
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomFeignErrorDecoder();
    }
}


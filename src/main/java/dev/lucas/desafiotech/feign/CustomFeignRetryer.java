package dev.lucas.desafiotech.feign;

import feign.RetryableException;
import feign.Retryer;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class CustomFeignRetryer implements Retryer {

    private final int maxAttempts;
    private final long interval;
    private int attempt = 1;

    public CustomFeignRetryer() {
        this(3, 2000);
    }

    public CustomFeignRetryer(int maxAttempts, long interval) {
        this.maxAttempts = maxAttempts;
        this.interval = interval;
    }

    @Override
    public void continueOrPropagate(RetryableException e) {
        if (attempt >= maxAttempts) {
            log.warn("Máximo de tentativas atingido ({}) para {}", maxAttempts, e.request().url());
            throw e;
        }

        log.warn("Tentativa {} de {} para {} devido a erro: {}", attempt, maxAttempts, e.request().url(), e.getMessage());
        attempt++;

        try {
            TimeUnit.MILLISECONDS.sleep(interval);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public Retryer clone() {
        return new CustomFeignRetryer(maxAttempts, interval);
    }
}
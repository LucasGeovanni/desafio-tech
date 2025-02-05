package dev.lucas.desafiotech.exception;

import feign.Request;
import feign.FeignException;

public class FeignRetryableException extends FeignException {
    public FeignRetryableException(int status, String message, Request request) {
        super(status, message, request);
    }
}

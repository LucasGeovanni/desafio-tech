package dev.lucas.desafiotech.exception;

public class DesafioTechException extends RuntimeException {
    public DesafioTechException() {
        super();
    }

    public DesafioTechException(String message) {
        super(message);
    }

    public DesafioTechException(String message, Throwable cause) {
        super(message, cause);
    }

    public DesafioTechException(Throwable cause) {
        super(cause);
    }

    protected DesafioTechException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

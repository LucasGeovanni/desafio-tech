package dev.lucas.desafiotech.exception;

public class RecordAlreadyExistsException extends DesafioTechException {

    public RecordAlreadyExistsException() {
        super();
    }

    public RecordAlreadyExistsException(String message) {
        super(message);
    }

    public RecordAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecordAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
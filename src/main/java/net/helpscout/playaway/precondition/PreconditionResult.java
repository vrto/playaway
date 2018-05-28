package net.helpscout.playaway.precondition;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import javax.validation.ConstraintViolation;

import java.util.Set;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class PreconditionResult {

    private HttpStatus violationStatus;

    @Getter
    private JsonError violationPayload;

    @Getter
    private String violationMessage;

    protected PreconditionResult() {}

    private PreconditionResult(HttpStatus violationStatus, JsonError violationPayload) {
        this.violationStatus = violationStatus;
        this.violationPayload = violationPayload;
    }

    private PreconditionResult(HttpStatus violationStatus, JsonError violationPayload, String violationMessage) {
        this.violationStatus = violationStatus;
        this.violationPayload = violationPayload;
        this.violationMessage = violationMessage;
    }

    public static PreconditionResult passed() {
        return new PreconditionResult();
    }

    public static PreconditionResult notFound(String message) {
        return new PreconditionResult(NOT_FOUND, JsonError.notFound(message), message);
    }

    public static <T> PreconditionResult badRequest(Set<ConstraintViolation<T>> errors) {
        return new PreconditionResult(BAD_REQUEST, JsonError.badRequest("Payload invalid")); // errors translation omitted for this demo
    }

    public static PreconditionResult badRequest(String message) {
        return new PreconditionResult(BAD_REQUEST, JsonError.badRequest(message), message);
    }

    public boolean isFailed() {
        return violationStatus != null;
    }

}

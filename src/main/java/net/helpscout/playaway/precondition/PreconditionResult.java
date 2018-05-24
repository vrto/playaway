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

//    public static PreconditionResult badRequestDetailed(List<FieldError> errors) {
//        return new PreconditionResult(BAD_REQUEST, DetailedJsonError.ofBadRequest(errors));
//    }

//    public static <T> PreconditionResult badRequestDetailed(Set<ConstraintViolation<T>> errors) {
//        return new PreconditionResult(BAD_REQUEST, DetailedJsonError.ofBadRequest(errors));
//    }

    public static PreconditionResult badRequest(String message) {
        return new PreconditionResult(BAD_REQUEST, JsonError.badRequest(message), message);
    }

//    public static PreconditionResult badRequestWithCustomMessage(String field, String message, ErrorMessageFormat format) {
//        ResponseError jsonError = format == ErrorMessageFormat.SIMPLE
//                ? JsonError.ofBadRequest(message)
//                : detailedFieldError(field, message);
//
//        return new PreconditionResult(BAD_REQUEST, jsonError, message);
//    }

//    public static PreconditionResult badRequestWithCustomMessage(String field, ValidationCode validationCode, ErrorMessageFormat format) {
//        ResponseError jsonError = format == ErrorMessageFormat.SIMPLE
//                ? JsonError.ofBadRequest(field + " - " + validationCode.getLabel())
//                : detailedFieldError(field, validationCode);
//
//        return new PreconditionResult(BAD_REQUEST, jsonError, validationCode.getLabel());
//    }

    public boolean isFailed() {
        return violationStatus != null;
    }

    //TODO cleanup

//    public boolean hasPayload() {
//        return violationPayload != null;
//    }

//    public int getViolationCode() {
//        return violationStatus.value();
//    }

}

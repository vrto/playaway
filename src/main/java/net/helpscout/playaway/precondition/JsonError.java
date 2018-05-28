package net.helpscout.playaway.precondition;

import lombok.Value;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.PAYMENT_REQUIRED;

@Value
public class JsonError {

    int code;
    String status;
    String message;

    public static JsonError internalServerError(String message) {
        return new JsonError(INTERNAL_SERVER_ERROR.value(), "Internal-Server-Error", message);
    }

    public static JsonError notFound(String message) {
        return new JsonError(NOT_FOUND.value(), "Not-Found", message);
    }

    public static JsonError badRequest(String message) {
        return new JsonError(BAD_REQUEST.value(), "Bad-Request", message);
    }

    public static JsonError paymentRequired(String message) {
        return new JsonError(PAYMENT_REQUIRED.value(), "Payment-Required", message);
    }
}

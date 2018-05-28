package net.helpscout.playaway.web.exception;

import lombok.Getter;
import net.helpscout.playaway.precondition.JsonError;
import org.springframework.http.HttpStatus;

public class InvalidJsonException extends AuthorizationException {

    @Getter
    private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public InvalidJsonException(String message) {
        super(message);
    }

    @Override
    public JsonError createErrorBody() {
        return JsonError.badRequest("Failure parsing request body!");
    }
}

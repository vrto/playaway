package net.helpscout.playaway.web.exception;

import lombok.Getter;
import net.helpscout.playaway.precondition.JsonError;
import org.springframework.http.HttpStatus;

public class MissingPaymentException extends AuthorizationException {

    @Getter
    private HttpStatus httpStatus = HttpStatus.PAYMENT_REQUIRED;

    public MissingPaymentException(String message) {
        super(message);
    }

    @Override
    public JsonError createErrorBody() {
        return JsonError.paymentRequired(getMessage());
    }
}

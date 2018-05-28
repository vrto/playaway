package net.helpscout.playaway.web.exception;

import net.helpscout.playaway.web.HttpStatusAware;
import net.helpscout.playaway.web.JsonErrorBodyAware;

public abstract class AuthorizationException extends RuntimeException implements HttpStatusAware, JsonErrorBodyAware {
    public AuthorizationException(String message) {
        super(message);
    }
}

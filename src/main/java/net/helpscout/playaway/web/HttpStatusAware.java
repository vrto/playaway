package net.helpscout.playaway.web;

import org.springframework.http.HttpStatus;

public interface HttpStatusAware {
    HttpStatus getHttpStatus();
}

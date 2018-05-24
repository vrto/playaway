package net.helpscout.playaway.stereotype;

import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Should be used on controllers that only perform reading operations using slave database.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@RestController
public @interface ReadingController {
}

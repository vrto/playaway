package net.helpscout.playaway.stereotype;

import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Service layer component that is only allowed to read data.
 * Components annotated with this annotation operate exclusively on slave database.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Service
public @interface Queries {
}

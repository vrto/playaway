package net.helpscout.playaway.stereotype;

import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Service layer component that is allowed to modify data.
 * Components annotated with this annotation operate exclusively on master database.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Service
public @interface Commands {
}

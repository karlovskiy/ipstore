package ipstore.aspect;

import ipstore.aspect.change.ChangeType;
import ipstore.aspect.change.ChangeMode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Action {
    ActionType value();

    ChangeType changeType() default ChangeType.NONE;

    ChangeMode changeMode() default ChangeMode.NONE;
}

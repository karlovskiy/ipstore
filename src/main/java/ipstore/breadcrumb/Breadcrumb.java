package ipstore.breadcrumb;

import java.lang.annotation.*;

/**
 * Here will be javadoc
 *
 * @author ravenstar
 * @since 4.1, 3/8/14
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Breadcrumb {
    String label() default "";
}

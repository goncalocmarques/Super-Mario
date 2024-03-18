package uni.ldts.events.collisions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * To help devs filter collision events, this annotation ensures that
 * only elements of a specific type trigger the listener
 * <p> (events are NOT required to adopt this annot.)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CollisionFilter {
    int typeA() default Integer.MIN_VALUE;
    int typeB() default Integer.MIN_VALUE;
}

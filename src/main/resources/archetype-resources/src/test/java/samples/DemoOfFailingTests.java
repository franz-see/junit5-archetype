package ${package}.samples;

import org.junit.jupiter.api.Tag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * http://junit.org/junit5/docs/current/user-guide/#writing-tests-meta-annotations
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Tag("demoOfFailingTests")
public @interface DemoOfFailingTests {
}

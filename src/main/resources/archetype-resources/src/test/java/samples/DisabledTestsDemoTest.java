package ${package}.samples;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * http://junit.org/junit5/docs/current/user-guide/#writing-tests-disabling
 */
class DisabledTestsDemoTest {

    @Disabled
    @Test
    void testWillBeSkipped() {
    }

    @Test
    void testWillBeExecuted() {
    }
}

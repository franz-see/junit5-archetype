package ${package}.samples;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * http://junit.org/junit5/docs/current/user-guide/#writing-tests-display-names
 */
@DisplayName("A special test case")
class DisplayNameDemoTest {

    @Test
    @DisplayName("Custom test name containing spaces")
    void testWithDisplayNameContainingSpaces() {
    }

    @Test
    @DisplayName("╯°□°）╯")
    void testWithDisplayNameContainingSpecialCharacters() {
    }

    @Test
    @DisplayName("😱")
    void testWithDisplayNameContainingEmoji() {
    }

}

package ${package}.samples;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.JavaTimeConversionPattern;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE;
import static org.junit.jupiter.params.provider.EnumSource.Mode.MATCH_ALL;

/**
 * http://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests
 */
class ParameterizedDemoTest {

    /**
     * http://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests-sources-ValueSource
     */
    @Nested
    class ValueSourceDemo {

        @ParameterizedTest
        @ValueSource(strings = { "racecar", "radar", "able was I ere I saw elba" })
        void palindromes(String candidate) {
            assertTrue(isPalindrome(candidate));
        }

        private boolean isPalindrome(String candidate) {
            if (candidate == null) {
                return true;
            }
            char[] chars = candidate.toCharArray();
            int length = chars.length;
            for(int i = 0; i < length / 2; i++) {
                if (chars[i] != chars[length - 1 - i]) {
                    return false;
                }
            }
            return true;
        }

        @ParameterizedTest
        @ValueSource(ints = { 1, 2, 3 })
        void testWithValueSource(int argument) {
            assertNotNull(argument);
        }

    }

    /**
     * http://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests-sources-EnumSource
     */
    @Nested
    class EnumSourceDemo {

        @ParameterizedTest
        @EnumSource(TimeUnit.class)
        void testWithEnumSource(TimeUnit timeUnit) {
            assertNotNull(timeUnit);
        }

        @ParameterizedTest
        @EnumSource(value = TimeUnit.class, names = { "DAYS", "HOURS" })
        void testWithEnumSourceInclude(TimeUnit timeUnit) {
            assertTrue(EnumSet.of(TimeUnit.DAYS, TimeUnit.HOURS).contains(timeUnit));
        }

        @ParameterizedTest
        @EnumSource(value = TimeUnit.class, mode = EXCLUDE, names = { "DAYS", "HOURS" })
        void testWithEnumSourceExclude(TimeUnit timeUnit) {
            assertFalse(EnumSet.of(TimeUnit.DAYS, TimeUnit.HOURS).contains(timeUnit));
            assertTrue(timeUnit.name().length() > 5);
        }

        @ParameterizedTest
        @EnumSource(value = TimeUnit.class, mode = MATCH_ALL, names = "^(M|N).+SECONDS$")
        void testWithEnumSourceRegex(TimeUnit timeUnit) {
            String name = timeUnit.name();
            assertTrue(name.startsWith("M") || name.startsWith("N"));
            assertTrue(name.endsWith("SECONDS"));
        }

    }

    /**
     * Note: @TestInstance is not needed for parameterized tests that uses @MethodSource not unless it's in a nested class
     *
     * http://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests-sources-MethodSource
     */
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    static class MethodSourceDemo {

        @ParameterizedTest
        @MethodSource("stringProvider")
        void testWithSimpleMethodSource(String argument) {
            assertNotNull(argument);
        }

        static Stream<String> stringProvider() {
            return Stream.of("foo", "bar");
        }

        @ParameterizedTest
        @MethodSource("range")
        void testWithRangeMethodSource(int argument) {
            assertNotEquals(9, argument);
        }

        static IntStream range() {
            return IntStream.range(0, 20).skip(10);
        }

        @ParameterizedTest
        @MethodSource("stringIntAndListProvider")
        void testWithMultiArgMethodSource(String str, int num, List<String> list) {
            assertEquals(3, str.length());
            assertTrue(num >=1 && num <=2);
            assertEquals(2, list.size());
        }

        static Stream<Arguments> stringIntAndListProvider() {
            return Stream.of(
                    Arguments.of("foo", 1, Arrays.asList("a", "b")),
                    Arguments.of("bar", 2, Arrays.asList("x", "y"))
            );
        }

    }

    /**
     * http://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests-sources-CsvFileSource
     */
    @Nested
    class CsvSourceDemo {
        @ParameterizedTest
        @CsvSource({ "foo, 1", "bar, 2", "'baz, qux', 3" })
        void testWithCsvSource(String first, int second) {
            assertNotNull(first);
            assertNotEquals(0, second);
        }

        @ParameterizedTest
        @CsvFileSource(resources = "/two-column.csv")
        void testWithCsvFileSource(String first, int second) {
            assertNotNull(first);
            assertNotEquals(0, second);
        }


    }

    /**
     * Note: @TestInstance is not needed for parameterized tests that uses @MethodSource not unless it's in a nested class
     *
     * http://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests-sources-ArgumentsSource
     */
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    static class ArgumentsSourceDemo {
        @ParameterizedTest
        @ArgumentsSource(MyArgumentsProvider.class)
        void testWithArgumentsSource(String argument) {
            assertNotNull(argument);
        }

        static class MyArgumentsProvider implements ArgumentsProvider {

            @Override
            public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
                return Stream.of("foo", "bar").map(Arguments::of);
            }
        }
    }

    /**
     * http://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests-argument-conversion
     */
    @Nested
    class ArgumentConversionDemo {

        @ParameterizedTest
        @ValueSource(strings = "SECONDS")
        void testWithImplicitArgumentConversion(TimeUnit argument) {
            assertNotNull(argument.name());
        }

    }

    /**
     * Note: @TestInstance is not needed for parameterized tests that uses @MethodSource not unless it's in a nested class
     *
     * http://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests-argument-conversion-explicit
     */
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    static class ExplicitConversionDemo {

        @ParameterizedTest
        @EnumSource(TimeUnit.class)
        void testWithExplicitArgumentConversion(@ConvertWith(ToStringArgumentConverter.class) String argument) {
            assertNotNull(TimeUnit.valueOf(argument));
        }

        static class ToStringArgumentConverter extends SimpleArgumentConverter {

            @Override
            protected Object convert(Object source, Class<?> targetType) {
                assertEquals(String.class, targetType, "Can only convert to String");
                return String.valueOf(source);
            }
        }

        @ParameterizedTest
        @ValueSource(strings = { "01.01.2017", "31.12.2017" })
        void testWithExplicitJavaTimeConverter(@JavaTimeConversionPattern("dd.MM.yyyy") LocalDate argument) {
            assertEquals(2017, argument.getYear());
        }
    }

    /**
     * http://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests-display-names
     */
    class CustomizingDisplayNamesDemo {
        @DisplayName("Display name of container")
        @ParameterizedTest(name = "{index} ==> first=''{0}'', second={1}")
        @CsvSource({ "foo, 1", "bar, 2", "'baz, qux', 3" })
        void testWithCustomDisplayNames(String first, int second) {
        }
    }

}

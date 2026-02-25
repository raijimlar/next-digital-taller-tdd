package p3dev;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PointNameTranslatorTest {

    static Stream<Arguments> validTranslationProvider() {
        return Stream.of(
                Arguments.of(0, "Love"),
                Arguments.of(1, "Fifteen"),
                Arguments.of(2, "Thirty"),
                Arguments.of(3, "Forty")
        );
    }

    static Stream<Integer> invalidPointsProvider() {
        return Stream.of(-1, -5, 4, 10, 100, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    @ParameterizedTest(name = "{0} → \"{1}\"")
    @MethodSource("validTranslationProvider")
    void should_translate_points_to_tennis_name(int points, String expectedName) {
        // Arrange
        PointNameTranslator translator = new PointNameTranslator();

        // Act
        String result = translator.translate(points);

        // Assert
        assertEquals(expectedName, result);
    }

    @ParameterizedTest(name = "translate({0}) → throws")
    @MethodSource("invalidPointsProvider")
    void should_throw_exception_for_invalid_points(int invalidPoints) {
        // Arrange
        PointNameTranslator translator = new PointNameTranslator();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> translator.translate(invalidPoints));
    }
}

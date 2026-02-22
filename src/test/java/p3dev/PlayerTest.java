package p3dev;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayerTest {

    static Stream<Arguments> initialStateProvider() {
        return Stream.of(
            Arguments.of("Player 1", 0),
            Arguments.of("Nadal", 0),
            Arguments.of("Federer", 0)
        );
    }

    @ParameterizedTest(name = "New player \"{0}\" should start with {1} points")
    @MethodSource("initialStateProvider")
    void should_start_with_zero_points_and_correct_name(String name, int expectedPoints) {
        // Arrange & Act
        Player player = new Player(name);

        // Assert
        assertEquals(name, player.getName());
        assertEquals(expectedPoints, player.getPoints());
    }

    static Stream<Arguments> scoreIncrementProvider() {
        return Stream.of(
            Arguments.of(1, 1),
            Arguments.of(2, 2),
            Arguments.of(3, 3),
            Arguments.of(5, 5)
        );
    }

    @ParameterizedTest(name = "After {0} scorePoint() calls → {1} points")
    @MethodSource("scoreIncrementProvider")
    void should_increment_points_correctly(int timesScored, int expectedPoints) {
        // Arrange
        Player player = new Player("Player 1");

        // Act
        for (int i = 0; i < timesScored; i++) {
            player.scorePoint();
        }

        // Assert
        assertEquals(expectedPoints, player.getPoints());
    }

    static Stream<Arguments> validInitialPointsProvider() {
        return Stream.of(
            Arguments.of("Player 1", 0, 0),
            Arguments.of("Player 1", 3, 3),
            Arguments.of("Nadal", 2, 2)
        );
    }

    @ParameterizedTest(name = "Player(\"{0}\", {1}) → {2} points")
    @MethodSource("validInitialPointsProvider")
    void should_accept_valid_initial_points(String name, int initialPoints, int expected) {
        // Arrange & Act
        Player player = new Player(name, initialPoints);

        // Assert
        assertEquals(expected, player.getPoints());
    }

    @Test
    void should_throw_on_negative_initial_points() {
        // Arrange, Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Player("Player 1", -1));
    }
}

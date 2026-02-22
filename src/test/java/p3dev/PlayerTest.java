package p3dev;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayerTest {

    @ParameterizedTest(name = "New player \"{0}\" should start with 0 points")
    @ValueSource(strings = {"Player 1", "Nadal", "Federer"})
    void should_start_with_zero_points_and_correct_name(String name) {
        // Arrange & Act
        Player player = new Player(name);

        // Assert
        assertEquals(name, player.getName());
        assertEquals(0, player.getPoints());
    }

    @ParameterizedTest(name = "After {0} scorePoint() calls → {0} points")
    @ValueSource(ints = {1, 2, 3, 5})
    void should_increment_points_correctly(int timesScored) {
        // Arrange
        Player player = new Player("Player 1");

        // Act
        for (int i = 0; i < timesScored; i++) {
            player.scorePoint();
        }

        // Assert
        assertEquals(timesScored, player.getPoints());
    }

    static Stream<Arguments> validInitialPointsProvider() {
        return Stream.of(
                Arguments.of("Player 1", 0),
                Arguments.of("Player 1", 3),
                Arguments.of("Nadal", 2)
        );
    }

    @ParameterizedTest(name = "Player(\"{0}\", {1}) → {1} points")
    @MethodSource("validInitialPointsProvider")
    void should_accept_valid_initial_points(String name, int initialPoints) {
        // Arrange & Act
        Player player = new Player(name, initialPoints);

        // Assert
        assertEquals(initialPoints, player.getPoints());
    }

    @Test
    void should_throw_on_negative_initial_points() {
        // Arrange, Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Player("Player 1", -1));
    }

    @ParameterizedTest(name = "Player(\"{0}\") → throws")
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void should_throw_on_invalid_name(String invalidName) {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Player(invalidName));
    }
}

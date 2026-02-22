package p3dev;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {

    static Stream<String> nameProvider() {
        return Stream.of("Player 1","Nadal","Federer");
    }

    @ParameterizedTest(name = "New player \"{0}\" should start with {1} points")
    @MethodSource("nameProvider")
    void should_start_with_zero_points_and_correct_name(String name) {
        // Arrange & Act
        Player player = new Player(name);

        // Assert
        assertEquals(name, player.getName());
        assertEquals(0, player.getPoints());
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
}
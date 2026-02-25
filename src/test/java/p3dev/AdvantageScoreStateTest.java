package p3dev;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static p3dev.TestHelper.playerWithPoints;

public class AdvantageScoreStateTest {

    private final AdvantageScoreState state = new AdvantageScoreState();

    @Test
    void should_return_advantage_player_one_when_player_one_leads() {
        // Arrange
        Player p1 = playerWithPoints("Player 1", 4);
        Player p2 = playerWithPoints("Player 2", 3);

        // Act & Assert
        assertEquals("Advantage Player 1", state.getScore(p1, p2));
    }

    @Test
    void should_return_advantage_player_two_when_player_two_leads() {
        // Arrange
        Player p1 = playerWithPoints("Player 1", 3);
        Player p2 = playerWithPoints("Player 2", 4);

        // Act & Assert
        assertEquals("Advantage Player 2", state.getScore(p1, p2));
    }

    @Test
    void should_use_player_name_in_advantage_message() {
        // Arrange
        Player p1 = playerWithPoints("Nadal", 5);
        Player p2 = playerWithPoints("Federer", 4);

        // Act & Assert
        assertEquals("Advantage Nadal", state.getScore(p1, p2));
    }

    @Test
    void should_not_be_terminal() {
        assertFalse(state.isTerminal());
    }

    static Stream<Arguments> validAdvantageProvider() {
        return Stream.of(
                Arguments.of(4, 3),
                Arguments.of(3, 4),
                Arguments.of(5, 4),
                Arguments.of(4, 5),
                Arguments.of(11, 10),
                Arguments.of(100, 99),
                Arguments.of(99, 100),
                Arguments.of(1000, 999)
        );
    }

    @ParameterizedTest(name = "applies({0},{1}) → true")
    @MethodSource("validAdvantageProvider")
    void should_apply_for_valid_advantage_scores(int p1, int p2) {
        assertTrue(state.applies(playerWithPoints("P1", p1), playerWithPoints("P2", p2)));
    }

    static Stream<Arguments> invalidAdvantageProvider() {
        return Stream.of(
                Arguments.of(0, 0),
                Arguments.of(3, 3),
                Arguments.of(4, 2),
                Arguments.of(5, 3),
                Arguments.of(2, 1),
                Arguments.of(100, 98),
                Arguments.of(1000, 0),
                Arguments.of(Integer.MAX_VALUE, 0)
        );
    }

    @ParameterizedTest(name = "applies({0},{1}) → false")
    @MethodSource("invalidAdvantageProvider")
    void should_not_apply_for_non_advantage_scores(int p1, int p2) {
        assertFalse(state.applies(playerWithPoints("P1", p1), playerWithPoints("P2", p2)));
    }

    @Test
    void should_throw_when_does_not_apply() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> state.getScore(playerWithPoints("P1", 5), playerWithPoints("P2", 3)));
    }
}

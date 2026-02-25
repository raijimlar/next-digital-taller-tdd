package p3dev;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static p3dev.TestHelper.playerWithPoints;

public class WinScoreStateTest {

    private final WinScoreState state = new WinScoreState();

    @Test
    void should_return_player_one_wins_when_player_one_leads() {
        // Arrange
        Player p1 = playerWithPoints("Player 1", 4);
        Player p2 = playerWithPoints("Player 2", 0);

        // Act & Assert
        assertEquals("Player 1 wins", state.getScore(p1, p2));
    }

    @Test
    void should_return_player_two_wins_when_player_two_leads() {
        // Arrange
        Player p1 = playerWithPoints("Player 1", 0);
        Player p2 = playerWithPoints("Player 2", 4);

        // Act & Assert
        assertEquals("Player 2 wins", state.getScore(p1, p2));
    }

    @Test
    void should_use_player_name_in_win_message() {
        // Arrange
        Player p1 = playerWithPoints("Nadal", 5);
        Player p2 = playerWithPoints("Federer", 3);

        // Act & Assert
        assertEquals("Nadal wins", state.getScore(p1, p2));
    }

    @Test
    void should_be_terminal() {
        assertTrue(state.isTerminal());
    }

    static Stream<Arguments> validWinProvider() {
        return Stream.of(
                Arguments.of(4, 0),
                Arguments.of(4, 2),
                Arguments.of(0, 4),
                Arguments.of(5, 3),
                Arguments.of(3, 5),
                Arguments.of(6, 4),
                Arguments.of(100, 98),
                Arguments.of(1000, 998),
                Arguments.of(Integer.MAX_VALUE, Integer.MAX_VALUE - 2)
        );
    }

    @ParameterizedTest(name = "applies({0},{1}) → true")
    @MethodSource("validWinProvider")
    void should_apply_for_valid_win_scores(int p1, int p2) {
        assertTrue(state.applies(playerWithPoints("P1", p1), playerWithPoints("P2", p2)));
    }

    static Stream<Arguments> invalidWinProvider() {
        return Stream.of(
                Arguments.of(0, 0),
                Arguments.of(3, 3),
                Arguments.of(4, 3),
                Arguments.of(3, 4),
                Arguments.of(3, 0),
                Arguments.of(100, 99),
                Arguments.of(1000, 1000)
        );
    }

    @ParameterizedTest(name = "applies({0},{1}) → false")
    @MethodSource("invalidWinProvider")
    void should_not_apply_for_non_win_scores(int p1, int p2) {
        assertFalse(state.applies(playerWithPoints("P1", p1), playerWithPoints("P2", p2)));
    }

    @Test
    void should_throw_when_does_not_apply() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> state.getScore(playerWithPoints("P1", 4), playerWithPoints("P2", 3)));
    }
}

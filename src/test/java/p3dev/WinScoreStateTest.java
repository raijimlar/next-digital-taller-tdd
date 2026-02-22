package p3dev;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class WinScoreStateTest {

    private static final WinScoreState STATE = new WinScoreState();

    private static Player playerWithPoints(String name, int points) {
        Player p = new Player(name);
        for (int i = 0; i < points; i++) p.scorePoint();
        return p;
    }

    static Stream<Arguments> player1WinsSituations() {
        return Stream.of(
            Arguments.of("Alice",  "Bob",    4, 0, "Alice wins"),
            Arguments.of("Carlos", "Diana",  4, 1, "Carlos wins"),
            Arguments.of("Eve",    "Frank",  4, 2, "Eve wins"),
            Arguments.of("Grace",  "Hank",   5, 3, "Grace wins"),
            Arguments.of("Ivy",    "Jack",   6, 4, "Ivy wins")
        );
    }

    @ParameterizedTest(name = "[{index}] {0} wins ({2}-{3} pts) => {4}")
    @MethodSource("player1WinsSituations")
    void getScore_player1HasMorePoints_returnsPlayer1WinsMessage(
            String name1, String name2, int p1Points, int p2Points, String expected) {
        Player player1 = playerWithPoints(name1, p1Points);
        Player player2 = playerWithPoints(name2, p2Points);
        assertEquals(expected, STATE.getScore(player1, player2));
    }

    static Stream<Arguments> player2WinsSituations() {
        return Stream.of(
            Arguments.of("Alice",  "Bob",    0, 4, "Bob wins"),
            Arguments.of("Carlos", "Diana",  1, 4, "Diana wins"),
            Arguments.of("Eve",    "Frank",  2, 4, "Frank wins"),
            Arguments.of("Grace",  "Hank",   3, 5, "Hank wins"),
            Arguments.of("Ivy",    "Jack",   4, 6, "Jack wins")
        );
    }

    @ParameterizedTest(name = "[{index}] {1} wins ({3}-{2} pts) => {4}")
    @MethodSource("player2WinsSituations")
    void getScore_player2HasMorePoints_returnsPlayer2WinsMessage(
            String name1, String name2, int p1Points, int p2Points, String expected) {
        Player player1 = playerWithPoints(name1, p1Points);
        Player player2 = playerWithPoints(name2, p2Points);
        assertEquals(expected, STATE.getScore(player1, player2));
    }

    @Test
    void isTerminal_returnsTrue() {
        assertTrue(STATE.isTerminal());
    }
}
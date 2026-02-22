package p3dev;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class NormalScoreStateTest {

    private static final PointNameTranslator TRANSLATOR = new PointNameTranslator();
    private static final NormalScoreState STATE = new NormalScoreState(TRANSLATOR);

    private static Player playerWithPoints(String name, int points) {
        Player p = new Player(name);
        for (int i = 0; i < points; i++) p.scorePoint();
        return p;
    }

    static Stream<Arguments> differentScores() {
        return Stream.of(
            Arguments.of(0, 1, "Love-Fifteen"),
            Arguments.of(0, 2, "Love-Thirty"),
            Arguments.of(0, 3, "Love-Forty"),
            Arguments.of(1, 0, "Fifteen-Love"),
            Arguments.of(1, 2, "Fifteen-Thirty"),
            Arguments.of(1, 3, "Fifteen-Forty"),
            Arguments.of(2, 0, "Thirty-Love"),
            Arguments.of(2, 1, "Thirty-Fifteen"),
            Arguments.of(2, 3, "Thirty-Forty"),
            Arguments.of(3, 0, "Forty-Love"),
            Arguments.of(3, 1, "Forty-Fifteen"),
            Arguments.of(3, 2, "Forty-Thirty")
        );
    }

    @ParameterizedTest(name = "[{index}] {0}-{1} pts => {2}")
    @MethodSource("differentScores")
    void getScore_differentPoints_returnsCorrectScore(int p1Points, int p2Points, String expected) {
        Player player1 = playerWithPoints("Alice", p1Points);
        Player player2 = playerWithPoints("Bob", p2Points);
        assertEquals(expected, STATE.getScore(player1, player2));
    }

    static Stream<Arguments> equalScores() {
        return Stream.of(
            Arguments.of(0, "Love-All"),
            Arguments.of(1, "Fifteen-All"),
            Arguments.of(2, "Thirty-All")
        );
    }

    @ParameterizedTest(name = "[{index}] {0} pts each => {1}")
    @MethodSource("equalScores")
    void getScore_equalPoints_returnsAllScore(int points, String expected) {
        Player player1 = playerWithPoints("Alice", points);
        Player player2 = playerWithPoints("Bob", points);
        assertEquals(expected, STATE.getScore(player1, player2));
    }

    @Test
    void isTerminal_returnsFalse() {
        assertFalse(STATE.isTerminal());
    }
}
package p3dev;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para ScoreStateResolver.
 * Verifica que el resolver selecciona el estado correcto según los puntos.
 */
public class ScoreStateResolverTest {

    private final PointNameTranslator translator = new PointNameTranslator();
    private final NormalScoreState normalState = new NormalScoreState(translator);
    private final DeuceScoreState deuceState = new DeuceScoreState();
    private final AdvantageScoreState advantageState = new AdvantageScoreState();
    private final WinScoreState winState = new WinScoreState();
    private final ScoreStateResolver resolver = new ScoreStateResolver(normalState, deuceState, advantageState, winState);

    static Stream<Arguments> normalStateProvider() {
        return Stream.of(
                Arguments.of(0, 0),
                Arguments.of(1, 0),
                Arguments.of(0, 1),
                Arguments.of(2, 1),
                Arguments.of(3, 0),
                Arguments.of(3, 2)
        );
    }

    @ParameterizedTest(name = "P1={0} P2={1} → Normal")
    @MethodSource("normalStateProvider")
    void should_resolve_normal_state(int p1, int p2) {
        // Arrange
        Player player1 = createPlayerWithPoints("P1", p1);
        Player player2 = createPlayerWithPoints("P2", p2);

        // Act
        IScoreState state = resolver.resolve(player1, player2);

        // Assert
        assertInstanceOf(NormalScoreState.class, state);
    }

    @Test
    void should_resolve_deuce_state_at_3_3() {
        // Arrange
        Player player1 = createPlayerWithPoints("P1", 3);
        Player player2 = createPlayerWithPoints("P2", 3);

        // Act
        IScoreState state = resolver.resolve(player1, player2);

        // Assert
        assertInstanceOf(DeuceScoreState.class, state);
    }

    @Test
    void should_resolve_advantage_state_at_4_3() {
        // Arrange
        Player player1 = createPlayerWithPoints("P1", 4);
        Player player2 = createPlayerWithPoints("P2", 3);

        // Act
        IScoreState state = resolver.resolve(player1, player2);

        // Assert
        assertInstanceOf(AdvantageScoreState.class, state);
    }

    @Test
    void should_resolve_win_state_at_4_2() {
        // Arrange
        Player player1 = createPlayerWithPoints("P1", 4);
        Player player2 = createPlayerWithPoints("P2", 2);

        // Act
        IScoreState state = resolver.resolve(player1, player2);

        // Assert
        assertInstanceOf(WinScoreState.class, state);
    }

    private Player createPlayerWithPoints(String name, int points) {
        Player player = new Player(name);
        for (int i = 0; i < points; i++) player.scorePoint();
        return player;
    }
}

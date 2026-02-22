package p3dev;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static p3dev.TestHelper.playerWithPoints;

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

    static Stream<Arguments> deuceStateProvider() {
        return Stream.of(
                Arguments.of(3, 3),
                Arguments.of(4, 4),
                Arguments.of(5, 5),
                Arguments.of(10, 10)
        );
    }

    static Stream<Arguments> advantageStateProvider() {
        return Stream.of(
                Arguments.of(4, 3),
                Arguments.of(3, 4),
                Arguments.of(5, 4),
                Arguments.of(4, 5)
        );
    }

    static Stream<Arguments> winStateProvider() {
        return Stream.of(
                Arguments.of(4, 0),
                Arguments.of(4, 2),
                Arguments.of(5, 3),
                Arguments.of(0, 4),
                Arguments.of(3, 5),
                Arguments.of(6, 4)
        );
    }

    @ParameterizedTest(name = "P1={0} P2={1} → Normal")
    @MethodSource("normalStateProvider")
    void should_resolve_normal_state(int p1, int p2) {
        // Act & Assert
        assertInstanceOf(NormalScoreState.class,
                resolver.resolve(playerWithPoints("P1", p1), playerWithPoints("P2", p2)));
    }

    @ParameterizedTest(name = "P1={0} P2={1} → Deuce")
    @MethodSource("deuceStateProvider")
    void should_resolve_deuce_state(int p1, int p2) {
        // Act & Assert
        assertInstanceOf(DeuceScoreState.class,
                resolver.resolve(playerWithPoints("P1", p1), playerWithPoints("P2", p2)));
    }

    @ParameterizedTest(name = "P1={0} P2={1} → Advantage")
    @MethodSource("advantageStateProvider")
    void should_resolve_advantage_state(int p1, int p2) {
        // Act & Assert
        assertInstanceOf(AdvantageScoreState.class,
                resolver.resolve(playerWithPoints("P1", p1), playerWithPoints("P2", p2)));
    }

    @ParameterizedTest(name = "P1={0} P2={1} → Win")
    @MethodSource("winStateProvider")
    void should_resolve_win_state(int p1, int p2) {
        // Act & Assert
        assertInstanceOf(WinScoreState.class,
                resolver.resolve(playerWithPoints("P1", p1), playerWithPoints("P2", p2)));
    }
}

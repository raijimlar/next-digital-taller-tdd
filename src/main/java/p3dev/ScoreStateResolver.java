package p3dev;

public class ScoreStateResolver {

    private final ScoreState[] states;
    private final ScoreState defaultState;

    public ScoreStateResolver(ScoreState normalState, ScoreState deuceState,
                              ScoreState advantageState, ScoreState winState) {
        this.states = new ScoreState[]{winState, deuceState, advantageState};
        this.defaultState = normalState;
    }

    public ScoreState resolve(Player player1, Player player2) {
        for (ScoreState state : states) {
            if (state.applies(player1, player2)) {
                return state;
            }
        }
        return defaultState;
    }
}

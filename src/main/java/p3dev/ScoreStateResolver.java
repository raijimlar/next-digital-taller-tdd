package p3dev;

public class ScoreStateResolver {

    private final IScoreState[] states;
    private final IScoreState defaultState;

    public ScoreStateResolver(IScoreState normalState, IScoreState deuceState,
                              IScoreState advantageState, IScoreState winState) {
        this.states = new IScoreState[]{winState, deuceState, advantageState};
        this.defaultState = normalState;
    }

    public IScoreState resolve(Player player1, Player player2) {
        for (IScoreState state : states) {
            if (state.applies(player1, player2)) {
                return state;
            }
        }
        return defaultState;
    }
}

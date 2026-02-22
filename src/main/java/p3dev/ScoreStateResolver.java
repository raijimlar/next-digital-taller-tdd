package p3dev;

public class ScoreStateResolver {

    private final IScoreState normalState;
    private final IScoreState deuceState;
    private final IScoreState advantageState;
    private final IScoreState winState;

    public ScoreStateResolver(IScoreState normalState, IScoreState deuceState,
                              IScoreState advantageState, IScoreState winState) {
        this.normalState = normalState;
        this.deuceState = deuceState;
        this.advantageState = advantageState;
        this.winState = winState;
    }

    public IScoreState resolve(Player player1, Player player2) {
        int p1 = player1.getPoints();
        int p2 = player2.getPoints();

        if (isWin(p1, p2)) {
            return winState;
        }
        if (isDeuce(p1, p2)) {
            return deuceState;
        }
        if (isAdvantage(p1, p2)) {
            return advantageState;
        }
        return normalState;
    }

    private boolean isWin(int p1, int p2) {
        return (p1 >= 4 || p2 >= 4) && Math.abs(p1 - p2) >= 2;
    }

    private boolean isDeuce(int p1, int p2) {
        return p2 >= 3 && p1 == p2;
    }

    private boolean isAdvantage(int p1, int p2) {
        return p1 >= 3 && p2 >= 3 && Math.abs(p1 - p2) == 1;
    }
}

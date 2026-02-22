package p3dev;

public class TennisGame {

    private final Player player1;
    private final Player player2;
    private final ScoreStateResolver stateResolver;

    public TennisGame(String player1Name, String player2Name) {
        this.player1 = new Player(player1Name);
        this.player2 = new Player(player2Name);
        this.stateResolver = createDefaultResolver();
    }

    public TennisGame(Player player1, Player player2, ScoreStateResolver stateResolver) {
        this.player1 = player1;
        this.player2 = player2;
        this.stateResolver = stateResolver;
    }

    public void playerOneScores() {
        player1.scorePoint();
    }

    public void playerTwoScores() {
        player2.scorePoint();
    }

    public String getScore() {
        IScoreState state = stateResolver.resolve(player1, player2);
        return state.getScore(player1, player2);
    }

    public boolean isGameOver() {
        return stateResolver.resolve(player1, player2).isTerminal();
    }

    public String getGameWinner() {
        if (!isGameOver()) {
            return null;
        }
        return player1.getPoints() > player2.getPoints() ? player1.getName() : player2.getName();
    }

    private static ScoreStateResolver createDefaultResolver() {
        PointNameTranslator translator = new PointNameTranslator();
        return new ScoreStateResolver(
                new NormalScoreState(translator),
                new DeuceScoreState(),
                new AdvantageScoreState(),
                new WinScoreState()
        );
    }
}

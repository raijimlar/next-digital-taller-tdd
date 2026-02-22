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

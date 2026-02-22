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

    private TennisGame(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.stateResolver = createDefaultResolver();
    }

    /**
     * @throws InvalidScoreException si la puntuación no es válida
     */
    public static TennisGame fromScore(String player1Name, String player2Name, int p1Points, int p2Points) {
        ScoreValidator.validateGameScore(p1Points, p2Points);
        return new TennisGame(new Player(player1Name, p1Points), new Player(player2Name, p2Points));
    }

    public void playerOneScores() {
        if (isGameOver()) {
            throw new IllegalStateException("Cannot score: game is already over");
        }
        player1.scorePoint();
    }

    public void playerTwoScores() {
        if (isGameOver()) {
            throw new IllegalStateException("Cannot score: game is already over");
        }
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

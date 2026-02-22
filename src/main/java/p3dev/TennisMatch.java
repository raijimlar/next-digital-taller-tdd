package p3dev;

public class TennisMatch {

    private final String player1Name;
    private final String player2Name;
    private int player1Games;
    private int player2Games;
    private TennisGame currentGame;

    public TennisMatch(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.player1Games = 0;
        this.player2Games = 0;
        this.currentGame = new TennisGame(player1Name, player2Name);
    }

    /**
     * @throws InvalidScoreException si la puntuación no es válida
     */
    public static TennisMatch fromScore(String p1Name, String p2Name,
                                        int p1Games, int p2Games,
                                        int p1Points, int p2Points) {
        ScoreValidator.validateMatchState(p1Games, p2Games, p1Points, p2Points);
        TennisMatch match = new TennisMatch(p1Name, p2Name);
        match.player1Games = p1Games;
        match.player2Games = p2Games;
        if (p1Points > 0 || p2Points > 0) {
            match.currentGame = TennisGame.fromScore(p1Name, p2Name, p1Points, p2Points);
        }
        return match;
    }

    public void playerOneScores() {
        if (isMatchOver()) {
            throw new IllegalStateException("Cannot score: match is already over");
        }
        currentGame.playerOneScores();
        checkAndAdvanceGame();
    }

    public void playerTwoScores() {
        if (isMatchOver()) {
            throw new IllegalStateException("Cannot score: match is already over");
        }
        currentGame.playerTwoScores();
        checkAndAdvanceGame();
    }

    private void checkAndAdvanceGame() {
        if (currentGame.isGameOver()) {
            String winner = currentGame.getGameWinner();
            if (winner.equals(player1Name)) {
                player1Games++;
            } else {
                player2Games++;
            }
            if (!isMatchOver()) {
                currentGame = new TennisGame(player1Name, player2Name);
            }
        }
    }

    public String getGameScore() {
        if (isMatchOver()) {
            return "Match over";
        }
        return currentGame.getScore();
    }

    public String getMatchScore() {
        return "Games: " + player1Games + "-" + player2Games;
    }

    public String getFullScore() {
        if (isMatchOver()) {
            return getMatchScore() + " | " + getMatchWinner() + " wins the match";
        }
        return getMatchScore() + " | " + getGameScore();
    }

    public boolean isMatchOver() {
        return ScoreValidator.isOver(player1Games, player2Games);
    }

    public String getMatchWinner() {
        if (!isMatchOver()) {
            return null;
        }
        return player1Games > player2Games ? player1Name : player2Name;
    }

    public int getPlayer1Games() {
        return player1Games;
    }

    public int getPlayer2Games() {
        return player2Games;
    }
}

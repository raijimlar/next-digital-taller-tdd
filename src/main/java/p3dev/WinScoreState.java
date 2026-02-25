package p3dev;

public class WinScoreState implements ScoreState {

    @Override
    public boolean applies(Player player1, Player player2) {
        return ScoreValidator.isGameOver(player1.getPoints(), player2.getPoints());
    }

    @Override
    public String getScore(Player player1, Player player2) {
        if (!applies(player1, player2)) {
            throw new IllegalArgumentException("WinScoreState does not apply for this score");
        }
        if (player1.getPoints() > player2.getPoints()) {
            return player1.getName() + " wins";
        }
        return player2.getName() + " wins";
    }

    @Override
    public boolean isTerminal() {
        return true;
    }
}

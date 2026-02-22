package p3dev;

public class DeuceScoreState implements IScoreState {

    @Override
    public boolean applies(Player player1, Player player2) {
        return player1.getPoints() >= 3 && player1.getPoints() == player2.getPoints();
    }

    @Override
    public String getScore(Player player1, Player player2) {
        if (!applies(player1, player2)) {
            throw new IllegalArgumentException("DeuceScoreState does not apply for this score");
        }
        return "Deuce";
    }
}

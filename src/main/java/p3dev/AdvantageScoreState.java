package p3dev;

public class AdvantageScoreState implements ScoreState {

    @Override
    public boolean applies(Player player1, Player player2) {
        int p1 = player1.getPoints();
        int p2 = player2.getPoints();
        return p1 >= 3 && p2 >= 3 && Math.abs(p1 - p2) == 1;
    }

    @Override
    public String getScore(Player player1, Player player2) {
        if (!applies(player1, player2)) {
            throw new IllegalArgumentException("AdvantageScoreState does not apply for this score");
        }
        if (player1.getPoints() > player2.getPoints()) {
            return "Advantage " + player1.getName();
        }
        return "Advantage " + player2.getName();
    }
}

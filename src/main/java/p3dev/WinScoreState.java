package p3dev;

public class WinScoreState implements IScoreState {

    @Override
    public boolean applies(Player player1, Player player2) {
        int p1 = player1.getPoints();
        int p2 = player2.getPoints();
        return (p1 >= 4 || p2 >= 4) && Math.abs(p1 - p2) >= 2;
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

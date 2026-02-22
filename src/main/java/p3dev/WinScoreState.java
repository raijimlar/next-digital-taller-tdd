package p3dev;

public class WinScoreState implements IScoreState {

    @Override
    public String getScore(Player player1, Player player2) {
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

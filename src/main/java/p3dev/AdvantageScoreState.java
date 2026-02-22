package p3dev;

public class AdvantageScoreState implements IScoreState {

    @Override
    public String getScore(Player player1, Player player2) {
        if (player1.getPoints() > player2.getPoints()) {
            return "Advantage " + player1.getName();
        }
        return "Advantage " + player2.getName();
    }
}

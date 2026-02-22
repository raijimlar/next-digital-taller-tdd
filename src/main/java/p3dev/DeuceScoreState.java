package p3dev;

public class DeuceScoreState implements IScoreState {

    @Override
    public String getScore(Player player1, Player player2) {
        return "Deuce";
    }
}

package p3dev;

public interface IScoreState {
    String getScore(Player player1, Player player2);

    default boolean isTerminal() {
        return false;
    }
}

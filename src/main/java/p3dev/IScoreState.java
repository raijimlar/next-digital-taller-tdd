package p3dev;

public interface IScoreState {
    boolean applies(Player player1, Player player2);

    String getScore(Player player1, Player player2);

    default boolean isTerminal() {
        return false;
    }
}

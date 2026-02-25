package p3dev;

public interface ScoreState {
    boolean applies(Player player1, Player player2);

    String getScore(Player player1, Player player2);

    default boolean isTerminal() {
        return false;
    }
}

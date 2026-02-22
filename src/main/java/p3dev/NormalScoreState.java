package p3dev;

public class NormalScoreState implements IScoreState {

    private final PointNameTranslator translator;

    public NormalScoreState(PointNameTranslator translator) {
        this.translator = translator;
    }

    @Override
    public boolean applies(Player player1, Player player2) {
        int p1 = player1.getPoints();
        int p2 = player2.getPoints();
        return p1 <= 3 && p2 <= 3 && !(p1 == 3 && p2 == 3);
    }

    @Override
    public String getScore(Player player1, Player player2) {
        if (!applies(player1, player2)) {
            throw new IllegalArgumentException("NormalScoreState does not apply for this score");
        }
        if (player1.getPoints() == player2.getPoints()) {
            return translator.translate(player1.getPoints()) + "-All";
        }
        return translator.translate(player1.getPoints()) + "-" + translator.translate(player2.getPoints());
    }
}

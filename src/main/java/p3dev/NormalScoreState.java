package p3dev;

public class NormalScoreState implements IScoreState {

    private final PointNameTranslator translator;

    public NormalScoreState(PointNameTranslator translator) {
        this.translator = translator;
    }

    @Override
    public String getScore(Player player1, Player player2) {
        if (player1.getPoints() == player2.getPoints()) {
            return translator.translate(player1.getPoints()) + "-All";
        }
        return translator.translate(player1.getPoints()) + "-" + translator.translate(player2.getPoints());
    }
}

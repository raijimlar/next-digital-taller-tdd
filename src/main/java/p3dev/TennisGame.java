package p3dev;

/**
 * Clase principal del juego de tenis.
 * Versión monolítica con lógica para puntuación normal, deuce, advantage y victoria.
 */
public class TennisGame {

    private final Player player1;
    private final Player player2;
    private final PointNameTranslator translator;

    public TennisGame(String player1Name, String player2Name) {
        this.player1 = new Player(player1Name);
        this.player2 = new Player(player2Name);
        this.translator = new PointNameTranslator();
    }

    public void playerOneScores() {
        player1.scorePoint();
    }

    public void playerTwoScores() {
        player2.scorePoint();
    }

    public String getScore() {
        int p1 = player1.getPoints();
        int p2 = player2.getPoints();

        // Win
        if ((p1 >= 4 || p2 >= 4) && Math.abs(p1 - p2) >= 2) {
            return (p1 > p2 ? player1.getName() : player2.getName()) + " wins";
        }

        // Deuce
        if (p1 >= 3 && p2 >= 3) {
            if (p1 == p2) {
                return "Deuce";
            }
            return "Advantage " + (p1 > p2 ? player1.getName() : player2.getName());
        }

        // Normal scoring
        if (p1 == p2) {
            return translator.translate(p1) + "-All";
        }
        return translator.translate(p1) + "-" + translator.translate(p2);
    }
}

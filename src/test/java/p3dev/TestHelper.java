package p3dev;

public final class TestHelper {

    private TestHelper() {
    }

    public static Player playerWithPoints(String name, int points) {
        Player p = new Player(name);
        for (int i = 0; i < points; i++) {
            p.scorePoint();
        }
        return p;
    }

    public static void scorePoints(TennisGame game, int p1, int p2) {
        for (int i = 0; i < p1; i++) game.playerOneScores();
        for (int i = 0; i < p2; i++) game.playerTwoScores();
    }

    public static void scoreAlternating(TennisGame game, int p1, int p2) {
        int min = Math.min(p1, p2);
        for (int i = 0; i < min; i++) {
            game.playerOneScores();
            game.playerTwoScores();
        }
        for (int i = min; i < p1; i++) game.playerOneScores();
        for (int i = min; i < p2; i++) game.playerTwoScores();
    }

    public static void scoreMatchPoints(TennisMatch match, int p1, int p2) {
        for (int i = 0; i < p1; i++) match.playerOneScores();
        for (int i = 0; i < p2; i++) match.playerTwoScores();
    }

    public static void winGameForPlayerOne(TennisMatch match) {
        scoreMatchPoints(match, 4, 0);
    }

    public static void winGameForPlayerTwo(TennisMatch match) {
        scoreMatchPoints(match, 0, 4);
    }
}

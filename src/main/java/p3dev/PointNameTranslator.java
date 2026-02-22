package p3dev;

public class PointNameTranslator {

    private static final String[] SCORE_NAMES = {"Love", "Fifteen", "Thirty", "Forty"};

    /**
     * @param points puntos del jugador (0, 1, 2, 3)
     * @return nombre del puntaje en tenis
     * @throws IllegalArgumentException si los puntos están fuera de rango
     */
    public String translate(int points) {
        if (points < 0 || points > 3) {
            throw new IllegalArgumentException("Points must be between 0 and 3, got: " + points);
        }
        return SCORE_NAMES[points];
    }
}

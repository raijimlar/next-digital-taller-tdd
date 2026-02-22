package p3dev;

public class ScoreValidator {

    private static final int MIN_TO_WIN = 4;
    private static final int MIN_LEAD = 2;

    private ScoreValidator() {
        // Utility class
    }

    public static void validateGameScore(int p1Points, int p2Points) {
        validateScore(p1Points, p2Points, "Game score");
    }

    public static void validateMatchScore(int p1Games, int p2Games) {
        validateScore(p1Games, p2Games, "Match score");
    }

    public static void validateMatchState(int p1Games, int p2Games, int p1Points, int p2Points) {
        validateMatchScore(p1Games, p2Games);

        if (isOver(p1Games, p2Games)) {
            if (p1Points != 0 || p2Points != 0) {
                throw new InvalidScoreException(
                    "Match is already over (" + p1Games + "-" + p2Games + "), cannot have a game in progress");
            }
            return;
        }

        validateGameScore(p1Points, p2Points);

        if (isOver(p1Points, p2Points)) {
            throw new InvalidScoreException(
                "Game points indicate a completed game (" + p1Points + "-" + p2Points
                    + ") — the game count should reflect this instead");
        }
    }

    private static void validateScore(int s1, int s2, String context) {
        if (s1 < 0 || s2 < 0) {
            throw new InvalidScoreException(context + " cannot be negative: " + s1 + "-" + s2);
        }

        int max = Math.max(s1, s2);
        int min = Math.min(s1, s2);
        int diff = max - min;

        if (max < MIN_TO_WIN) {
            return; // No one has reached the win threshold
        }
        if (max == MIN_TO_WIN && min <= MIN_TO_WIN - MIN_LEAD) {
            return; // Straight win (e.g., 4-0, 4-1, 4-2)
        }
        if (min >= MIN_TO_WIN - 1 && diff <= MIN_LEAD) {
            return; // Deuce zone (e.g., 3-3, 4-3, 5-3, 6-4)
        }

        throw new InvalidScoreException(context + " is impossible: " + s1 + "-" + s2);
    }

    static boolean isOver(int s1, int s2) {
        int max = Math.max(s1, s2);
        int diff = Math.abs(s1 - s2);
        return max >= MIN_TO_WIN && diff >= MIN_LEAD;
    }
}

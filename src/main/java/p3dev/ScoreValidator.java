package p3dev;

public class ScoreValidator {

    static final int GAME_MIN_TO_WIN = 4;
    static final int GAME_MIN_LEAD = 2;
    static final int MATCH_MIN_TO_WIN = 4;
    static final int MATCH_MIN_LEAD = 2;

    private ScoreValidator() {
        // Utility class
    }

    public static void validateGameScore(int p1Points, int p2Points) {
        validateScore(p1Points, p2Points, "Game score", GAME_MIN_TO_WIN, GAME_MIN_LEAD);
    }

    public static void validateMatchScore(int p1Games, int p2Games) {
        validateScore(p1Games, p2Games, "Match score", MATCH_MIN_TO_WIN, MATCH_MIN_LEAD);
    }

    public static void validateMatchState(int p1Games, int p2Games, int p1Points, int p2Points) {
        validateMatchScore(p1Games, p2Games);

        if (isMatchOver(p1Games, p2Games)) {
            if (p1Points != 0 || p2Points != 0) {
                throw new InvalidScoreException(
                    "Match is already over (" + p1Games + "-" + p2Games + "), cannot have a game in progress");
            }
            return;
        }

        validateGameScore(p1Points, p2Points);

        if (isGameOver(p1Points, p2Points)) {
            throw new InvalidScoreException(
                "Game points indicate a completed game (" + p1Points + "-" + p2Points
                    + ") — the game count should reflect this instead");
        }
    }

    private static void validateScore(int s1, int s2, String context, int minToWin, int minLead) {
        if (s1 < 0 || s2 < 0) {
            throw new InvalidScoreException(context + " cannot be negative: " + s1 + "-" + s2);
        }

        int max = Math.max(s1, s2);
        int min = Math.min(s1, s2);
        int diff = max - min;

        if (max < minToWin) {
            return;
        }
        if (max == minToWin && min <= minToWin - minLead) {
            return;
        }
        if (min >= minToWin - 1 && diff <= minLead) {
            return;
        }

        throw new InvalidScoreException(context + " is impossible: " + s1 + "-" + s2);
    }

    public static boolean isGameOver(int p1Points, int p2Points) {
        return isOver(p1Points, p2Points, GAME_MIN_TO_WIN, GAME_MIN_LEAD);
    }

    public static boolean isMatchOver(int p1Games, int p2Games) {
        return isOver(p1Games, p2Games, MATCH_MIN_TO_WIN, MATCH_MIN_LEAD);
    }

    private static boolean isOver(int s1, int s2, int minToWin, int minLead) {
        int max = Math.max(s1, s2);
        int diff = Math.abs(s1 - s2);
        return max >= minToWin && diff >= minLead;
    }
}

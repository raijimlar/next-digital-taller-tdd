package p3dev;

import java.util.Scanner;

public class TennisApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║          Tennis Scorer           ║");
        System.out.println("╠══════════════════════════════════╣");
        System.out.println("║  1. Play a new match             ║");
        System.out.println("║  2. Translate existing score     ║");
        System.out.println("║  3. Resume match from score      ║");
        System.out.println("╚══════════════════════════════════╝");
        System.out.print("Choose an option: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1" -> playMatch(scanner);
            case "2" -> translateScore(scanner);
            case "3" -> resumeMatch(scanner);
            default -> System.out.println("Invalid option.");
        }

        scanner.close();
    }

    private static void playMatch(Scanner scanner) {
        System.out.print("Player 1 name: ");
        String p1 = scanner.nextLine().trim();
        System.out.print("Player 2 name: ");
        String p2 = scanner.nextLine().trim();

        TennisMatch match = new TennisMatch(p1, p2);
        System.out.println("\nMatch started: " + p1 + " vs " + p2);
        System.out.println("Commands: '1' = " + p1 + " scores, '2' = " + p2 + " scores, 'q' = quit\n");

        runMatch(match, scanner);
    }

    private static void resumeMatch(Scanner scanner) {
        try {
            System.out.print("Player 1 name: ");
            String p1 = scanner.nextLine().trim();
            System.out.print("Player 2 name: ");
            String p2 = scanner.nextLine().trim();

            System.out.print("Games won by " + p1 + ": ");
            int g1 = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Games won by " + p2 + ": ");
            int g2 = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Current game - points for " + p1 + ": ");
            int pt1 = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Current game - points for " + p2 + ": ");
            int pt2 = Integer.parseInt(scanner.nextLine().trim());

            TennisMatch match = TennisMatch.fromScore(p1, p2, g1, g2, pt1, pt2);
            System.out.println("\nMatch resumed: " + match.getFullScore());

            if (match.isMatchOver()) {
                System.out.println(match.getMatchWinner() + " has already won!");
                return;
            }

            System.out.println("Commands: '1' = " + p1 + " scores, '2' = " + p2 + " scores, 'q' = quit\n");
            runMatch(match, scanner);
        } catch (InvalidScoreException e) {
            System.out.println("Invalid score: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: please enter a number.");
        }
    }

    private static void translateScore(Scanner scanner) {
        try {
            System.out.print("Points for Player 1 (0-n): ");
            int p1 = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Points for Player 2 (0-n): ");
            int p2 = Integer.parseInt(scanner.nextLine().trim());

            TennisGame game = TennisGame.fromScore("Player 1", "Player 2", p1, p2);
            System.out.println("\n→ " + game.getScore());
        } catch (InvalidScoreException e) {
            System.out.println("Invalid score: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: please enter a number.");
        }
    }

    private static void runMatch(TennisMatch match, Scanner scanner) {
        while (!match.isMatchOver()) {
            System.out.println("  " + match.getFullScore());
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1", "2" -> match.scorePoint(Integer.parseInt(input));
                case "q" -> {
                    System.out.println("Match paused at: " + match.getFullScore());
                    return;
                }
                default -> System.out.println("  Invalid. Use '1', '2', or 'q'.");
            }
        }

        System.out.println("\n  " + match.getFullScore());
        System.out.println("  Congratulations to " + match.getMatchWinner() + "!");
    }
}

package p3dev;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TennisAppTest {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream capturedOutput;

    @BeforeEach
    void setUp() {
        capturedOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturedOutput));
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    private String runAppWithInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        TennisApp.main(new String[]{});
        return capturedOutput.toString();
    }

    @Test
    void should_display_menu_on_start() {
        // Arrange
        String input = "1\nNadal\nFederer\nq\n";

        // Act
        String output = runAppWithInput(input);

        // Assert
        assertTrue(output.contains("Tennis Scorer"));
        assertTrue(output.contains("Play a new match"));
        assertTrue(output.contains("Translate existing score"));
        assertTrue(output.contains("Resume match from score"));
    }

    @Test
    void should_start_match_and_quit() {
        // Arrange
        String input = "1\nNadal\nFederer\nq\n";

        // Act
        String output = runAppWithInput(input);

        // Assert
        assertTrue(output.contains("Match started: Nadal vs Federer"));
        assertTrue(output.contains("Match paused at:"));
    }

    @Test
    void should_score_points_and_show_score() {
        // Arrange — score one point for player 1 then quit
        String input = "1\nNadal\nFederer\n1\nq\n";

        // Act
        String output = runAppWithInput(input);

        // Assert
        assertTrue(output.contains("Fifteen"));
    }

    @Test
    void should_play_full_game_until_win() {
        // Arrange
        String input = "1\nNadal\nFederer\n" + "1\n".repeat(4).repeat(4);

        // Act
        String output = runAppWithInput(input);

        // Assert
        assertTrue(output.contains("Congratulations to Nadal"));
        assertTrue(output.contains("wins the match"));
    }

    @Test
    void should_handle_invalid_command_during_match() {
        // Arrange
        String input = "1\nNadal\nFederer\nx\nq\n";

        // Act
        String output = runAppWithInput(input);

        // Assert
        assertTrue(output.contains("Invalid. Use '1', '2', or 'q'."));
    }

    static Stream<Arguments> translateScoreProvider() {
        return Stream.of(
            Arguments.of(0, 0, "Love-All"),
            Arguments.of(1, 0, "Fifteen-Love"),
            Arguments.of(3, 3, "Deuce"),
            Arguments.of(4, 3, "Advantage Player 1")
        );
    }

    @ParameterizedTest(name = "translate({0},{1}) → contains \"{2}\"")
    @MethodSource("translateScoreProvider")
    void should_translate_valid_score(int p1, int p2, String expectedFragment) {
        // Arrange
        String input = "2\n" + p1 + "\n" + p2 + "\n";

        // Act
        String output = runAppWithInput(input);

        // Assert
        assertTrue(output.contains(expectedFragment));
    }

    @Test
    void should_show_error_for_impossible_score_in_translate() {
        // Arrange
        String input = "2\n7\n3\n";

        // Act
        String output = runAppWithInput(input);

        // Assert
        assertTrue(output.contains("Invalid score"));
    }

    @Test
    void should_resume_match_from_valid_score() {
        // Arrange — resume at 2-1 games, 1-0 points, then quit
        String input = "3\nNadal\nFederer\n2\n1\n1\n0\nq\n";

        // Act
        String output = runAppWithInput(input);

        // Assert
        assertTrue(output.contains("Match resumed"));
        assertTrue(output.contains("Games: 2-1"));
    }

    @Test
    void should_show_error_for_invalid_resume_score() {
        // Arrange — 7-3 is impossible
        String input = "3\nNadal\nFederer\n7\n3\n0\n0\n";

        // Act
        String output = runAppWithInput(input);

        // Assert
        assertTrue(output.contains("Invalid score"));
    }

    @Test
    void should_detect_already_won_match_on_resume() {
        // Arrange — 4-0 games, 0-0 points → match already over
        String input = "3\nNadal\nFederer\n4\n0\n0\n0\n";

        // Act
        String output = runAppWithInput(input);

        // Assert
        assertTrue(output.contains("has already won"));
    }

    @Test
    void should_resume_and_continue_playing_until_match_won() {
        // Arrange — resume at 3-2 games, 3-0 points, then player 1 scores to win game and match
        String input = "3\nNadal\nFederer\n3\n2\n3\n0\n1\nq\n";

        // Act
        String output = runAppWithInput(input);

        // Assert
        assertTrue(output.contains("Congratulations to Nadal"));
    }

    @Test
    void should_handle_non_numeric_input_in_translate() {
        // Arrange
        String input = "2\nabc\n";

        // Act
        String output = runAppWithInput(input);

        // Assert
        assertTrue(output.contains("Invalid input: please enter a number"));
    }

    @Test
    void should_handle_non_numeric_input_in_resume() {
        // Arrange
        String input = "3\nNadal\nFederer\nabc\n";

        // Act
        String output = runAppWithInput(input);

        // Assert
        assertTrue(output.contains("Invalid input: please enter a number"));
    }

    @Test
    void should_handle_invalid_menu_option() {
        // Arrange
        String input = "9\n";

        // Act
        String output = runAppWithInput(input);

        // Assert
        assertTrue(output.contains("Invalid option"));
    }
}

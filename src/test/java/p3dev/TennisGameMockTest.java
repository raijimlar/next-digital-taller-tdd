package p3dev;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TennisGameMockTest {

    @Mock
    private ScoreStateResolver mockResolver;

    @Mock
    private ScoreState mockState;

    @Test
    void should_delegate_score_to_state_resolver() {
        // Arrange
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        TennisGame game = new TennisGame(player1, player2, mockResolver);

        when(mockResolver.resolve(player1, player2)).thenReturn(mockState);
        when(mockState.getScore(player1, player2)).thenReturn("Love-All");

        // Act
        String result = game.getScore();

        // Assert
        assertEquals("Love-All", result);
        verify(mockResolver, times(1)).resolve(player1, player2);
        verify(mockState, times(1)).getScore(player1, player2);
    }

    @Test
    void should_call_resolver_each_time_score_is_requested() {
        // Arrange
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        TennisGame game = new TennisGame(player1, player2, mockResolver);

        when(mockResolver.resolve(player1, player2)).thenReturn(mockState);
        when(mockState.getScore(player1, player2)).thenReturn("Love-All");

        // Act
        game.getScore();
        game.getScore();
        game.getScore();

        // Assert
        verify(mockResolver, times(3)).resolve(player1, player2);
    }

    @Test
    void should_increment_player_one_points_on_score() {
        // Arrange
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        TennisGame game = new TennisGame(player1, player2, mockResolver);
        when(mockResolver.resolve(player1, player2)).thenReturn(mockState);
        when(mockState.isTerminal()).thenReturn(false);

        // Act
        game.playerOneScores();

        // Assert
        assertEquals(1, player1.getPoints());
        assertEquals(0, player2.getPoints());
    }

    @Test
    void should_increment_player_two_points_on_score() {
        // Arrange
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        TennisGame game = new TennisGame(player1, player2, mockResolver);
        when(mockResolver.resolve(player1, player2)).thenReturn(mockState);
        when(mockState.isTerminal()).thenReturn(false);

        // Act
        game.playerTwoScores();

        // Assert
        assertEquals(0, player1.getPoints());
        assertEquals(1, player2.getPoints());
    }
}

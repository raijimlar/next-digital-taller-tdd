# Taller de TDD - NextDigital
# Tennis Kata

## Contexto

Ejercicio práctico realizado como parte del taller de TDD impartido por [Next Digital](https://nextdigital.es/) en colaboración con la Universidad de Sevilla

## Enunciado

Implementar un sistema de puntuación de tenis con las siguientes reglas:

### Puntuación de un juego (game)
- Los puntos se cuentan: **0 → 15 → 30 → 40**
- Se usan los nombres: **"Love", "Fifteen", "Thirty", "Forty"**
- Si ambos jugadores llegan a 40: **"Deuce"**
- Tras Deuce, si un jugador anota: **"Advantage [Player]"**
- Si el jugador con ventaja anota: **gana el juego**
- Si el jugador sin ventaja anota: **vuelven a Deuce**

### Puntuación de un partido (match)
- El partido lo gana el primero que consiga **4 juegos** con al menos **2 juegos de ventaja** sobre el oponente.

## Test Cases

| 0-0 | love-love          |
|-----|--------------------|
| 1-0 | fifteen-love       |
| 2-0 | thirty-love        |
| 3-0 | forty-love         |
| 0-1 | love-fifteen       |
| 0-2 | love-thirty        |
| ... |                    |
| 3-3 | deuce              |
| 4-3 | Advantage player 1 |
| 3-4 | Advantage player 2 |
| 5-3 | player 1 wins      |
| 3-5 | player 2 wins      |

### Basic Scoring
- `shouldReturnLoveLove_OnStart()` → Score is "Love-All" at start.
- `testPlayerOneScoresOnce_ShouldBeFifteenLove()` → "Fifteen-Love".
- `testPlayerTwoScoresOnce_ShouldBeLoveFifteen()` → "Love-Fifteen".
- `testBothPlayersScoreOnce_ShouldBeFifteenAll()` → "Fifteen-All".
- `testPlayerOneScoresTwice_ShouldBeThirtyLove()` → "Thirty-Love".
- `testPlayerTwoScoresTwice_ShouldBeLoveThirty()` → "Love-Thirty".

### Deuce and Advantage
- `testScoreIsFortyAll_ShouldBeDeuce()` → "Deuce".
- `testPlayerOneAdvantageAfterDeuce_ShouldBeAdvantagePlayerOne()` → "Advantage Player 1".
- `testPlayerTwoAdvantageAfterDeuce_ShouldBeAdvantagePlayerTwo()` → "Advantage Player 2".
- `testPlayerOneWinsAfterAdvantage_ShouldBeWinPlayerOne()` → "Player 1 wins".
- `testPlayerTwoWinsAfterAdvantage_ShouldBeWinPlayerTwo()` → "Player 2 wins".
- `testAdvantageLost_BackToDeuce()` → "Deuce" if the player with Advantage loses the next point.

### Winning the Game
- `testPlayerOneWinsByTwoPoints_ShouldBeWinPlayerOne()` → "Player 1 wins".
- `testPlayerTwoWinsByTwoPoints_ShouldBeWinPlayerTwo()` → "Player 2 wins".

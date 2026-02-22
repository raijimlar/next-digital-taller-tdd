# Taller de TDD - NextDigital
# Tennis Kata

## Contexto

Ejercicio práctico realizado como parte del taller de TDD impartido por [Next Digital](https://nextdigital.es/) en colaboración con la Universidad de Sevilla.

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

## Decisiones de diseño

### Nomenclatura de empates
He decidido usar el sufijo **"All"** para empates en puntuación normal (ej. `"Love-All"`, `"Fifteen-All"`, `"Thirty-All"`) en lugar de repetir el nombre (ej. ~~"Love-Love"~~).

### Modelo de puntuación interno
- La puntuación interna es **acumulativa**: los puntos no se resetean al volver a Deuce (ej. un juego con muchos Deuces puede tener puntuación interna 10-8).
- De esta forma no se pierde información.
- `fromScore()` acepta puntuaciones arbitrariamente altas siempre que sean lógicamente válidas (ej. `9-8` = Advantage tras muchos Deuces).
- Se validan las puntuaciones imposibles (ej. `7-3` no es válido porque el jugador ya habría ganado con `5-3`).
- Las reglas de victoria son las mismas para **juegos** y para el **partido**: necesitar al menos 4 y al menos 2 de ventaja.

## Test Cases

| Puntuación | Resultado              |
|------------|------------------------|
| 0-0        | Love-All               |
| 1-0        | Fifteen-Love           |
| 2-0        | Thirty-Love            |
| 3-0        | Forty-Love             |
| 0-1        | Love-Fifteen           |
| 0-2        | Love-Thirty            |
| ...        |                        |
| 3-3        | Deuce                  |
| 4-3        | Advantage Player 1     |
| 3-4        | Advantage Player 2     |
| 5-3        | Player 1 wins          |
| 3-5        | Player 2 wins          |

### Estructura de tests

| Clase de test               | Tipo        | Clase bajo test       |
|-----------------------------|-------------|-----------------------|
| `PlayerTest`                | Unitario    | `Player`              |
| `PointNameTranslatorTest`   | Unitario    | `PointNameTranslator` |
| `NormalScoreStateTest`      | Unitario    | `NormalScoreState`    |
| `DeuceScoreStateTest`       | Unitario    | `DeuceScoreState`     |
| `AdvantageScoreStateTest`   | Unitario    | `AdvantageScoreState` |
| `WinScoreStateTest`         | Unitario    | `WinScoreState`       |
| `ScoreStateResolverTest`    | Unitario    | `ScoreStateResolver`  |
| `ScoreValidatorTest`        | Unitario    | `ScoreValidator`      |
| `TennisGameTest`            | Unitario    | `TennisGame`          |
| `TennisMatchTest`           | Unitario    | `TennisMatch`         |
| `TennisGameMockTest`        | Mock        | `TennisGame`          |
| `TennisAppTest`             | Unitario    | `TennisApp`           |
| `GameFlowIntegrationTest`   | Integración | `TennisGame`          |
| `MatchFlowIntegrationTest`  | Integración | `TennisMatch`         |

## Extra: Interfaz de consola (`TennisApp`)

Como extra y para poder "testear manualmente", he añadido una aplicación de consola interactiva.

1. **Jugar un partido nuevo** — introduce nombres y marca puntos con `1` / `2`.
2. **Traducir puntuación** — convierte puntos numéricos (ej. 2-1) a terminología de tenis (Thirty-Fifteen).
3. **Reanudar un partido** — introduce un marcador parcial (juegos + puntos) y continúa jugando.

La clase valida entradas no numéricas y marcadores inválidos.
Se testea redirigiendo `System.in` / `System.out`.

## Ejecución

```bash
mvn test          # Ejecutar tests

mvn verify        # Tests + cobertura Jacoco

mvn exec:java     # Lanzar consola interactiva
```

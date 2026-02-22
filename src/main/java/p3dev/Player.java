package p3dev;

public class Player {

    private final String name;
    private int points;

    public Player(String name) {
        this(name, 0);
    }

    Player(String name, int initialPoints) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Player name cannot be null or empty");
        }
        if (initialPoints < 0) {
            throw new IllegalArgumentException("Points cannot be negative");
        }
        this.name = name;
        this.points = initialPoints;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public void scorePoint() {
        this.points++;
    }
}

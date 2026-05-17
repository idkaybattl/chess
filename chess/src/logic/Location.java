package logic;

public class Location {
    // TODO: make private
    public int x;
    public int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Location(Location other) {
        this.x = other.x;
        this.y = other.y;
    }

    public String algebraicNotation() {
        return (String.valueOf((char) (97 + x)) + String.valueOf(y + 1));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean inBoard() {
        return (x >= 0 && x < 8 && y >= 0 && y < 8);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Location)) {
            return false;
        }

        Location location = (Location) other;
        return x == location.x && y == location.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }
}

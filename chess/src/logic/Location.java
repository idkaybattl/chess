package logic;

public class Location {
    // TODO: make private
    public int x;
    public int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
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
}

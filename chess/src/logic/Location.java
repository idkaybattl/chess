package logic;

public class Location {
    public int x;
    public int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String algebraicNotation() {
        return (String.valueOf((char) (97 + x)) + String.valueOf(y + 1));
    }
}

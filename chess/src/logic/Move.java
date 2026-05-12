package logic;

public class Move {
    private Location start;
    private Location target;

    public Move(Location start, Location target) {
        this.start = start;
        this.target = target;
    }

    public String algebraicNotation() {
        return (start.algebraicNotation() + target.algebraicNotation());
    }

    public Location getStart() {
        return start;
    }

    public Location getTarget() {
        return target;
    }
}

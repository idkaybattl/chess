package logic.engine;

import logic.Location;
import logic.pieces.*;

public class MoveChoice {
    private final Location start;
    private final Location target;
    private Class<? extends Promotable> promotion;

    public MoveChoice(Location start, Location target, Class<? extends Promotable> promotion) {
        this.start = start;
        this.target = target;
        this.promotion = promotion;
    }

    public MoveChoice(String moveUCI) {
        this.start = new Location(moveUCI.substring(0, 2));
        this.target = new Location(moveUCI.substring(2, 4));
        if (moveUCI.length() > 4) {
            switch (moveUCI.charAt(4)) {
                case 'q': {
                    promotion = Queen.class;
                    break;
                }
                case 'r': {
                    promotion = Rook.class;
                    break;
                }
                case 'n': {
                    promotion = Knight.class;
                    break;
                }
                case 'b': {
                    promotion = Bishop.class;
                    break;
                }
            }
        }
    }

    public Location getStart() {
        return start;
    }

    public Location getTarget() {
        return target;
    }

    public Class<? extends Promotable> getPromotion() {
        return promotion;
    }
}

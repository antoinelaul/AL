package entities.brick;

import gameframework.base.Overlappable;

import java.awt.*;


public abstract class BreakableBrick extends AbstractBrick implements Overlappable {

    public BreakableBrick(Canvas canvas, int x, int y, int width, int height) {
        super(canvas, x, y, width, height);
    }

    public BreakableBrick(Canvas canvas, int x, int y, int width, int height, String filename) {
        super(canvas, x, y, width, height, filename);
    }

    public abstract int getValue();
}

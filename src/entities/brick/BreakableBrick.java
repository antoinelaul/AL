package entities.brick;

import gameframework.base.Overlappable;

import java.awt.*;


public abstract class BreakableBrick extends AbstractBrick implements Overlappable {

    public BreakableBrick(Canvas defaultCanvas, int x, int y, int width, int height) {
        super(defaultCanvas, x, y, width, height);
    }

    public abstract int getValue();
}

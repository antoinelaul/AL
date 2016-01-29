package entities;

import gameframework.base.DrawableImage;

import java.awt.*;

public class BreakableBrick extends AbstractBrick {

	public BreakableBrick(Canvas defaultCanvas, int x, int y, int width, int height) {
        super(defaultCanvas, x, y, width, height);
    }

    @Override
    public String getImage() {
        return "assets/images/basic_brick.png";
    }

    @Override
    public int getValue() {
        return 100;
    }
}
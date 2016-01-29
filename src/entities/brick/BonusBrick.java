package entities.brick;

import java.awt.*;


public class BonusBrick extends BreakableBrick {
    public BonusBrick(Canvas defaultCanvas, int x, int y, int width, int height) {
        super(defaultCanvas, x, y, width, height);
    }

    @Override
    public String getImage() {
        return "assets/images/bonus_brick.png";
    }

    @Override
    public int getValue() {
        return 500;
    }
}

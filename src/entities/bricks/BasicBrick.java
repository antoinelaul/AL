package entities.bricks;

import java.awt.*;

public class BasicBrick extends BreakableBrick {

    public BasicBrick(Canvas canvas, int x, int y, int width, int height) {
        super(canvas, x, y, width, height);
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
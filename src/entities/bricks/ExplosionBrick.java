package entities.bricks;

import java.awt.*;


public class ExplosionBrick extends BreakableBrick {
    public ExplosionBrick(Canvas defaultCanvas, int x, int y, int width, int height) {
        super(defaultCanvas, x, y, width, height);
    }

    @Override
    public int getValue() {
        return 300;
    }

    @Override
    public String getImage() {
        return "assets/images/explosion_brick.png";
    }
}

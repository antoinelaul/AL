package entities;


import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;

import java.awt.*;

public abstract class AbstractBrick implements Drawable, Overlappable, GameEntity {
    private final static int WIDTH = 16;
    private final static int HEIGHT = 16;

    protected static DrawableImage image = null;
    int x, y;

    public AbstractBrick(Canvas defaultCanvas, int x, int y) {
        image = new DrawableImage(getImage(), defaultCanvas);
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image.getImage(), x, y, WIDTH, HEIGHT, null);
    }

    @Override
    public Rectangle getBoundingBox() {
        return (new Rectangle(x, y, WIDTH, HEIGHT));
    }

    @Override
    public Point getPosition() {
        return (new Point(x, y));
    }

    public abstract String getImage();
    public abstract int getValue();
}

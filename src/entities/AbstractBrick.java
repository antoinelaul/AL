package entities;


import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;

import java.awt.*;

public abstract class AbstractBrick implements Drawable, Overlappable, GameEntity {
    protected static DrawableImage image = null;
    int x, y;
    int width;
    int height;

    public AbstractBrick(Canvas defaultCanvas, int x, int y, int width, int height) {
        image = new DrawableImage(getImage(), defaultCanvas);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image.getImage(), x, y, width, height, null);
    }

    @Override
    public Rectangle getBoundingBox() {
        return (new Rectangle(x, y, width, height));
    }

    @Override
    public Point getPosition() {
        return (new Point(x, y));
    }

    public abstract String getImage();
    public abstract int getValue();
}

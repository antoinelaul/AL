package entities.bricks;

import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.game.GameEntity;

import java.awt.*;


public abstract class AbstractBrick implements Drawable, GameEntity, Cloneable {
    private DrawableImage image;
    Point pos;
    private int width;
    private int height;
    private boolean broken = false;


    public AbstractBrick(Canvas canvas, int x, int y, int width, int height) {
        this.image = new DrawableImage(getImage(), canvas);
        this.pos = new Point(x, y);
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image.getImage(), pos.x, pos.y, width, height, null);
    }

    public Rectangle getBoundingBox() {
        return (new Rectangle(pos.x, pos.y, width, height));
    }

    public Point getPosition() {
        return pos;
    }

    public void setPosition(Point point) {
        this.pos = point;
    }

    @Override
    public AbstractBrick clone() {
        try {
            return (AbstractBrick) super.clone();
        }
        catch (CloneNotSupportedException e) { }

        return null;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public boolean isBroken() {
        return broken;
    }

    public void broke() {   // Ok, it's not english, but 'break' was already taken.
        broken = true;
    }

    public abstract String getImage();
}

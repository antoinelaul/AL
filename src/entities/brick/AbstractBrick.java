package entities.brick;

import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.game.GameEntity;

import java.awt.*;


public abstract class AbstractBrick implements Drawable, GameEntity, Cloneable {
    private DrawableImage image;
    Point pos;
    int width;
    int height;


    public AbstractBrick(Canvas defaultCanvas, int x, int y, int width, int height) {
        image = new DrawableImage(getImage(), defaultCanvas);
        pos = new Point(x, y);
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
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public abstract String getImage();
}

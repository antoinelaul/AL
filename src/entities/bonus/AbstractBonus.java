package entities.bonus;

import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;
import gameframework.game.GameMovable;

import java.awt.*;


abstract public class AbstractBonus extends GameMovable implements Drawable, GameEntity,
        Overlappable, Cloneable {
    protected DrawableImage image;
    private int size;


    public AbstractBonus(Canvas canvas, int size) {
        this.image = new DrawableImage(getImage(), canvas);
        this.size = size;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image.getImage(), (int) getPosition().getX(),
                (int) getPosition().getY(), size, size, null);
    }

    @Override
    public void oneStepMoveAddedBehavior() { }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(0, 0, size, size);
    }

    public abstract String getImage();

    @Override
    public AbstractBonus clone() {
        try {
            return (AbstractBonus) super.clone();
        } catch (CloneNotSupportedException e) { }

        return null;
    }
}

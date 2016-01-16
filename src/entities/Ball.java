package entities;

import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;
import gameframework.game.GameMovable;

import java.awt.*;

public class Ball extends GameMovable implements Drawable, GameEntity, Overlappable {
    protected static DrawableImage image;

    private int size;


    public Ball(Canvas canvas, int size) {
        image = new DrawableImage("assets/images/ball.png", canvas);
        this.size = size;
    }


    @Override
    public void draw(Graphics g) {
        g.drawImage(image.getImage(), (int) getPosition().getX(),
                (int) getPosition().getY(), size, size, null);
    }

    @Override
    public void oneStepMoveAddedBehavior() {
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(0, 0, size, size);

    }
}

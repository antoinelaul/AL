package entities;

import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;
import gameframework.game.GameMovable;

import java.awt.*;

public class Ball extends GameMovable implements Drawable, GameEntity, Overlappable {
    protected static DrawableImage image;

    public static final int RENDERING_SIZE = 16;


    public Ball(Canvas canvas) {
        image = new DrawableImage("assets/images/ball.png", canvas);
    }


    @Override
    public void draw(Graphics g) {
        g.drawImage(image.getImage(), (int) getPosition().getX(),
                (int) getPosition().getY(), RENDERING_SIZE, RENDERING_SIZE,
                null);
    }

    @Override
    public void oneStepMoveAddedBehavior() {

    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(0, 0, RENDERING_SIZE, RENDERING_SIZE);

    }
}

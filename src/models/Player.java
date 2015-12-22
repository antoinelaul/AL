package models;


import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;
import gameframework.game.GameMovable;

import java.awt.*;


public class Player extends GameMovable implements Drawable, GameEntity, Overlappable {
    protected static DrawableImage image;

    public static final int RENDERING_WIDTH = 32;
    public static final int RENDERING_HEIGHT = 16;


    public Player(Canvas defaultCanvas) {
        image = new DrawableImage("assets/images/player.png", defaultCanvas);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image.getImage(), (int) getPosition().getX(),
                (int) getPosition().getY(), RENDERING_WIDTH, RENDERING_HEIGHT,
                null);
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(0, 0, RENDERING_WIDTH, RENDERING_HEIGHT);
    }

    @Override
    public void oneStepMoveAddedBehavior() {

    }
}

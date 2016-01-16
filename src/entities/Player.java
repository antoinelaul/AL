package entities;

import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;
import gameframework.game.GameMovable;
import gameframework.game.SpriteManager;
import gameframework.game.SpriteManagerDefaultImpl;

import java.awt.*;


public class Player extends GameMovable implements Drawable, GameEntity, Overlappable {
    protected static DrawableImage image;

    private int width;
    private int height;


    public Player(Canvas canvas, int width, int height) {
        image = new DrawableImage("assets/images/player.png", canvas);
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image.getImage(), (int) getPosition().getX(),
                (int) getPosition().getY(), width, height, null);
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(0, 0, width, height);
    }

    @Override
    public void oneStepMoveAddedBehavior() {

    }
}

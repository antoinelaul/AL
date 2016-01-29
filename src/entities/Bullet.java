package entities;

import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;
import gameframework.game.GameMovable;

import java.awt.*;


public class Bullet extends GameMovable implements Drawable, GameEntity, Overlappable {
    private DrawableImage image;
    private int width;
    private int height;


    public Bullet(Canvas canvas, int width, int height) {
        image = new DrawableImage("assets/images/bullet.png", canvas);
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image.getImage(), (int) getPosition().getX(),
                (int) getPosition().getY(), width, height, null);
    }

    @Override
    public void oneStepMoveAddedBehavior() {

    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(0, 0, width, height);
    }
}

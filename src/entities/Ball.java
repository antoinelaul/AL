package entities;

import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;
import gameframework.game.GameMovable;

import java.awt.*;

public class Ball extends GameMovable implements Drawable, GameEntity, Overlappable {
    protected static DrawableImage image;

    private boolean onFire = false;
    private int timeOnFire = 0;
    private int size;
    private Canvas canvas;


    public Ball(Canvas canvas, int size) {
        image = new DrawableImage("assets/images/ball.png", canvas);
        this.canvas = canvas;
        this.size = size;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image.getImage(), (int) getPosition().getX(),
                (int) getPosition().getY(), size, size, null);
    }

    @Override
    public void oneStepMoveAddedBehavior() {
        timeOnFire--;
        if (timeOnFire == 0) changeImage();

        /* if(timeOnFire <= 0) {    // Here, the image is re affected every time... not so good for performances.
            changeImage();
            setOffFire();
        } */
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(0, 0, size, size);
    }


    /**
     * To change ball asset according ball state
     */
     public void changeImage(){
        if (this.isOnFire())
            this.image = new DrawableImage("assets/images/fireball.png", canvas);
        else
            this.image = new DrawableImage("assets/images/ball.png", canvas);
    }

    /**
     * Methods about ball fire state
     */
    // public void setOnFire(){ this.onFire = true;}
    // public void setOffFire(){ this.onFire = false;}

    /* public boolean isStillOnFire() {
        return timeOnFire > 0;
    } */

    public boolean isOnFire(){
        // return this.onFire;
        return timeOnFire > 0;
    }

    public void setTimeFire(int time) {
        timeOnFire = time;
    }

    public int getSize() {return this.size; }
}

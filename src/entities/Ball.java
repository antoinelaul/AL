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
        timeOnFire--;
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(0, 0, size, size);

    }


    /**
     * To change ball asset according ball state
     */
    public void changeImage(Canvas canvas){
        if(this.onFire)
            this.image = new DrawableImage("assets/images/fireball.png", canvas);
        else
            this.image = new DrawableImage("assets/images/ball.png", canvas);
    }

    /**
     * Methods about ball fire state
     */
    public void setOnFire(){ this.onFire = true;}
    public void setOffFire(){ this.onFire = false;}
    public boolean isStillOnFire() { return timeOnFire > 0;}
    public boolean isOnFire(){
        return this.onFire;
    }
    public void setTimeFire(int time) {
        timeOnFire = time;
    }

    public int getSize() {return this.size; }
}

package models;

import gameframework.base.MoveStrategy;
import gameframework.base.SpeedVector;
import gameframework.base.SpeedVectorDefaultImpl;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class MoveStrategyHorizontal extends KeyAdapter implements MoveStrategy {
    protected SpeedVector speedVector = new SpeedVectorDefaultImpl(new Point(0, 0));


    @Override
    public SpeedVector getSpeedVector() {
        return speedVector;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        switch(event.getKeyCode()) {
            case 37:
                speedVector.setDirection(new Point(-1, 0));
                break;
            case 39:
                speedVector.setDirection(new Point(1, 0));
                break;

            default: break;
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        speedVector.setDirection(new Point(0, 0));
    }
}

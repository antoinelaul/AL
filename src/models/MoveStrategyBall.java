package models;

import gameframework.base.MoveStrategy;
import gameframework.base.SpeedVector;
import gameframework.base.SpeedVectorDefaultImpl;

import java.awt.*;


public class MoveStrategyBall implements MoveStrategy {
    protected SpeedVector speedVector;


    public MoveStrategyBall(int dx, int dy) {
        speedVector = new SpeedVectorDefaultImpl(new Point(dx, dy));
    }

    @Override
    public SpeedVector getSpeedVector() {
        return speedVector;
    }
}

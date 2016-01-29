package controllers;

import entities.Ball;
import entities.InvisibleWall;
import entities.brick.UnbreakableBrick;
import gameframework.base.MoveStrategy;
import gameframework.base.SpeedVector;
import gameframework.game.GameMovableDriverDefaultImpl;
import gameframework.game.IllegalMoveException;
import gameframework.game.MoveBlockerRulesApplierDefaultImpl;
import models.BreakoutBallDriver;
import models.MoveStrategyLine;

import java.awt.*;


public class BallMoveBlockerApplier extends MoveBlockerRulesApplierDefaultImpl {

    public void moveBlockerRule(Ball ball, InvisibleWall wall) throws IllegalMoveException {
        SpeedVector sv = ball.getSpeedVector();
        Point p = sv.getDirection();
        MoveStrategyLine ballStr;


        if (wall.isHztl())
            ballStr = new MoveStrategyLine(p.x, -p.y);
        else
            ballStr = new MoveStrategyLine(-p.x, p.y);

        GameMovableDriverDefaultImpl bdriver = (GameMovableDriverDefaultImpl) ball.getDriver();
        bdriver.setStrategy(ballStr);

        throw new IllegalMoveException();
    }
    
    public void moveBlockerRule(Ball ball, UnbreakableBrick ubrick) throws IllegalMoveException {
        Point dir = ball.getSpeedVector().getDirection();

        // If the direction is null, no need to set strategies... in any case, the ball will stay
        // in place.
        ((BreakoutBallDriver) ball.getDriver()).setStrategies(new MoveStrategy[] {
                // Every direction change is tested.
                new MoveStrategyLine(-dir.x, dir.y),
                new MoveStrategyLine(dir.x, -dir.y),
                new MoveStrategyLine(-dir.x, -dir.y),
        });

        throw new IllegalMoveException();
    }
}

package controllers;

import entities.Ball;
import entities.InvisibleWall;
import entities.UnbreakableBrick;
import gameframework.base.MoveStrategy;
import gameframework.base.SpeedVector;
import gameframework.game.GameMovableDriverDefaultImpl;
import gameframework.game.IllegalMoveException;
import gameframework.game.MoveBlockerRulesApplierDefaultImpl;
import models.BreakoutBallDriver;
import models.MoveStrategyBall;

import java.awt.*;


public class BallMoveBlockerApplier extends MoveBlockerRulesApplierDefaultImpl {

    public void moveBlockerRule(Ball ball, InvisibleWall wall) throws IllegalMoveException {
        SpeedVector sv = ball.getSpeedVector();
        Point p = sv.getDirection();
        MoveStrategyBall ballStr;


        if (wall.isHztl())
            ballStr = new MoveStrategyBall(p.x, -p.y);
        else
            ballStr = new MoveStrategyBall(-p.x, p.y);

        GameMovableDriverDefaultImpl bdriver = (GameMovableDriverDefaultImpl) ball.getDriver();
        bdriver.setStrategy(ballStr);

        throw new IllegalMoveException();
    }
    
    public void moveBlockerRule(Ball ball, UnbreakableBrick ubrick) throws IllegalMoveException {
        // THIS IS A AWFUL WAY.. we must change it and it does not work...
        // Problem when the ball hit two block at the same time !
      /*  if (brick.getPosition().x < ball.getPosition().x) {
            ballStr = new MoveStrategyBall(-p.x, p.y);
        } else if (brick.getPosition().x >= ball.getPosition().x) {
            ballStr = new MoveStrategyBall(-p.x, p.y);
        } else if (brick.getPosition().y < ball.getPosition().y) {
            ballStr = new MoveStrategyBall(p.x, -p.y);
        } else if (brick.getPosition().y >= ball.getPosition().y) {
            ballStr = new MoveStrategyBall(p.x, -p.y);
        } else {
            ballStr = new MoveStrategyBall(-p.x, -p.y);
        }
*/

        Point dir = ball.getSpeedVector().getDirection();

        // If the direction is null, no need to set strategies... in any case, the ball will stay
        // in place.
        ((BreakoutBallDriver) ball.getDriver()).setStrategies(new MoveStrategy[] {
                // Every direction change is tested.
                new MoveStrategyBall(-dir.x, dir.y),
                new MoveStrategyBall(dir.x, -dir.y),
                new MoveStrategyBall(-dir.x, -dir.y),
        });

        throw new IllegalMoveException();
    }
}

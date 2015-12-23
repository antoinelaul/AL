package models;

import entities.Ball;
import entities.InvisibleWall;
import gameframework.base.SpeedVector;
import gameframework.game.GameMovableDriverDefaultImpl;
import gameframework.game.IllegalMoveException;
import gameframework.game.MoveBlockerRulesApplierDefaultImpl;

import java.awt.*;


public class BallMoveBlocker extends MoveBlockerRulesApplierDefaultImpl {

    public void moveBlockerRule(Ball b, InvisibleWall w) throws IllegalMoveException {
        SpeedVector sv = b.getSpeedVector();
        Point p = sv.getDirection();
        MoveStrategyBall ballStr;

        if (w.isHztl())
            ballStr = new MoveStrategyBall(p.x, -p.y);
        else
            ballStr = new MoveStrategyBall(-p.x, p.y);

        GameMovableDriverDefaultImpl bdriver = (GameMovableDriverDefaultImpl) b.getDriver();
        bdriver.setStrategy(ballStr);

        throw new IllegalMoveException();
    }
}

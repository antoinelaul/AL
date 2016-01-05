package controllers;

import entities.Ball;
import entities.BreakableBrick;
import entities.InvisibleWall;
import entities.UnbreakableBrick;
import gameframework.base.SpeedVector;
import gameframework.game.GameMovableDriverDefaultImpl;
import gameframework.game.IllegalMoveException;
import gameframework.game.MoveBlockerRulesApplierDefaultImpl;
import models.MoveStrategyBall;

import java.awt.*;


public class BallMoveBlocker extends MoveBlockerRulesApplierDefaultImpl {

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
    
    public void moveBlockerRule(Ball b, UnbreakableBrick w) throws IllegalMoveException {
        SpeedVector sv = b.getSpeedVector();
        Point p = sv.getDirection();
        MoveStrategyBall ballStr;


        // THIS IS A AWFUL WAY.. we must change it and it does not work...
        // Problem when the ball hit two block at the same time !
        if (w.getPosition().x < b.getPosition().x) {
            ballStr = new MoveStrategyBall(-p.x, p.y);
        } else if (w.getPosition().x >= b.getPosition().x) {
            ballStr = new MoveStrategyBall(-p.x, p.y);
        } else if (w.getPosition().y < b.getPosition().y) {
            ballStr = new MoveStrategyBall(p.x, -p.y);
        } else if (w.getPosition().y >= b.getPosition().y) {
            ballStr = new MoveStrategyBall(p.x, -p.y);
        } else {
            ballStr = new MoveStrategyBall(-p.x, -p.y);
        }

        GameMovableDriverDefaultImpl bdriver = (GameMovableDriverDefaultImpl) b.getDriver();
        bdriver.setStrategy(ballStr);

        throw new IllegalMoveException();
    }
}

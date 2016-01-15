package controllers;

import entities.Ball;
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
    
    public void moveBlockerRule(Ball ball, UnbreakableBrick ubrick) throws IllegalMoveException {
        SpeedVector sv = ball.getSpeedVector();
        Point p = sv.getDirection();
        //MoveStrategyBall ballStr;

       /* Point bp = ball.getPosition();
        Point up = ubrick.getPosition();
        Rectangle ubb = ubrick.getBoundingBox();

        int dx = p.x;
        int dy = p.y;
        int deltax = bp.x - up.x;
        int deltay = bp.y - up.y;

        System.out.println("-----------");
        System.out.println(deltax + ", " + deltay);
        System.out.println(dx + ", " + dy);

        if (deltax <= 0 || deltax >= ubb.getWidth())
            dx = -dx;

        if (deltay <= 0 || deltay >= ubb.getHeight())
            dy = -dy;

        System.out.println(dx + ", " + dy); */

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
        GameMovableDriverDefaultImpl bdriver = (GameMovableDriverDefaultImpl) ball.getDriver();
        bdriver.setStrategy(new MoveStrategyBall(-p.x, p.y));

        SpeedVector newSv = bdriver.getSpeedVector(ball);
       // System.out.println(newSv.getDirection());
        if (newSv.getDirection().x == 0 && newSv.getDirection().y == 0) {
            System.out.println(p.x + ", " + p.y);
            bdriver.setStrategy(new MoveStrategyBall(p.x, -p.y));
        }

        throw new IllegalMoveException();
    }
}

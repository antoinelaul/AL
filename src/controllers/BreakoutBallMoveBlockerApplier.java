package controllers;

import entities.Ball;
import entities.Blocker;
import entities.InvisibleWall;
import entities.brick.UnbreakableBrick;
import gameframework.base.MoveStrategy;
import gameframework.game.*;
import models.MoveStrategyLine;

import java.awt.*;


public class BreakoutBallMoveBlockerApplier extends MoveBlockerRulesApplierDefaultImpl {

    public void moveBlockerRule(Ball ball, InvisibleWall wall) throws IllegalMoveException {
        Point direction = ball.getSpeedVector().getDirection();
        MoveStrategyLine ballStr;

        if (wall.isHorizontal())
            ballStr = new MoveStrategyLine(direction.x, -direction.y);
        else
            ballStr = new MoveStrategyLine(-direction.x, direction.y);

        ((BreakoutBallDriver) ball.getDriver()).setStrategy(ballStr);
        ball.setPosition(getApproximation(ball, wall, direction));

        throw new IllegalMoveException();
    }
    
    public void moveBlockerRule(Ball ball, UnbreakableBrick ubrick) throws IllegalMoveException {
        Point direction = ball.getSpeedVector().getDirection();

        // If the direction is null, no need to set strategies... in any case, the ball will stay
        // in place.
        ((BreakoutBallDriver) ball.getDriver()).setStrategies(new MoveStrategy[] {
                // Every direction change is tested.
                new MoveStrategyLine(-direction.x, direction.y),
                new MoveStrategyLine(direction.x, -direction.y),
                new MoveStrategyLine(-direction.x, -direction.y),
        });

        ball.setPosition(getApproximation(ball, ubrick, direction));

        throw new IllegalMoveException();
    }


    /*
     * Check if the ball is really aside the blocker.
     * If it's not the case, a new point is computed, corresponding to the real ball position.
     * Otherwise, the given ball position is returned.
     * Here, just the abscissa are handled.
     */
    private Point getApproximation(Ball ball, Blocker blocker, Point direction) {
        Point ballPosition = ball.getPosition();
        Point blockerPosition = blocker.getPosition();
        Rectangle ballBoundingBox = ball.getBoundingBox();
        Rectangle blockerBoundingBox = blocker.getBoundingBox();

        // Compute the ball and blocker centers to have a common referencial.
        Point ballCenter = new Point(ballPosition.x + ballBoundingBox.width / 2,
                ballPosition.y + ballBoundingBox.height / 2);
        Point blockerCenter = new Point(blockerPosition.x + blockerBoundingBox.width / 2,
                blockerPosition.y + blockerBoundingBox.height / 2);

        // Minimum distance between the two x centers is, of course, the addition of the half ball
        // and blocker width.
        int dist = Math.abs(blockerCenter.x - ballCenter.x);
        int minDist = ballBoundingBox.width / 2 + blockerBoundingBox.width / 2;

        if (dist > minDist) {
            // Just an affine function, to compute the missing distance to add in order to reach
            // correctly the blocker.
            return new Point(
                    ballPosition.x + (dist - minDist),
                    ballPosition.y + (int) ((dist - minDist) * ((double) direction.y / direction.x))
            );
        }

        return ballPosition;
    }
}

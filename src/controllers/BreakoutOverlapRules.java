package controllers;

import entities.Ball;
import entities.Player;
import gameframework.base.SpeedVector;
import gameframework.game.GameMovableDriverDefaultImpl;
import gameframework.game.GameUniverse;
import gameframework.game.OverlapRulesApplierDefaultImpl;
import models.MoveStrategyBall;

import java.awt.*;


public class BreakoutOverlapRules extends OverlapRulesApplierDefaultImpl {
    protected GameUniverse universe;

    @Override
    public void setUniverse(GameUniverse universe) {
        this.universe = universe;
    }

    public void overlapRule(Player player, Ball ball) {
        // Change strategy.
        SpeedVector sv = ball.getSpeedVector();
        Point p = sv.getDirection();

        MoveStrategyBall ballStr = new MoveStrategyBall(p.x, -p.y);
        GameMovableDriverDefaultImpl bdriver = (GameMovableDriverDefaultImpl) ball.getDriver();
        bdriver.setStrategy(ballStr);

        // Place the ball out of the player, to avoid multiple collisions.
        Point bpp = ball.getPosition();
        Point ppp = player.getPosition();
        Rectangle pbb = player.getBoundingBox();

        ball.setPosition(new Point(bpp.x, ppp.y - pbb.height));
    }
}


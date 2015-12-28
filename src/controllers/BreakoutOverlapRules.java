package controllers;

import entities.Ball;
import entities.BreakableWall;
import entities.Player;
import entities.UnbreakableWall;
import gameframework.base.ObservableValue;
import gameframework.base.SpeedVector;
import gameframework.game.GameMovableDriverDefaultImpl;
import gameframework.game.GameUniverse;
import gameframework.game.OverlapRulesApplierDefaultImpl;
import models.MoveStrategyBall;

import java.awt.*;



public class BreakoutOverlapRules extends OverlapRulesApplierDefaultImpl {
    protected GameUniverse universe;
    private int totalBreakableWalls = 0;
	private int wallBroken = 0;
	private final ObservableValue<Boolean> endOfGame;

	public BreakoutOverlapRules(ObservableValue<Boolean> eof) {
		this.endOfGame = eof;
	}
	
	public void setTotalBreakableWalls(int totalBkw) {
		this.totalBreakableWalls = totalBkw;
	}
	
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
    
    public void overlapRule(Ball ball, BreakableWall bkw) {
    	universe.removeGameEntity(bkw);
    	wallBrokenHandler();
	}
    
    public void overlapRule(Ball ball, UnbreakableWall unbkw){
    }
    
    private void wallBrokenHandler() {
		wallBroken++;
		if (wallBroken >= totalBreakableWalls) {
			endOfGame.setValue(true);
		}
	}

}


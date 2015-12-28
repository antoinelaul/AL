package models;

import entities.Ball;
import entities.BreakableWall;
import entities.InvisibleWall;
import entities.UnbreakableWall;
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
    
    public void moveBlockerRule(Ball b, UnbreakableWall w) throws IllegalMoveException {
    	SpeedVector sv = b.getSpeedVector();
        Point p = sv.getDirection();
        MoveStrategyBall ballStr;
        
        
        // THIS IS A AWFUL WAY.. we must change it and it does not work...
        // Problem when the ball hit two block at the same time !
        if(w.getPos().x < b.getPosition().x){
            ballStr = new MoveStrategyBall(-p.x, p.y);
        }else if(w.getPos().x >= b.getPosition().x){
            ballStr = new MoveStrategyBall(-p.x, p.y);
        }else if(w.getPos().y < b.getPosition().y){
        	ballStr = new MoveStrategyBall(p.x, -p.y);
        }else if(w.getPos().y >= b.getPosition().y ){
        	ballStr = new MoveStrategyBall(p.x, -p.y);
        }else{
        	ballStr = new MoveStrategyBall(-p.x, -p.y);
        }
        
        GameMovableDriverDefaultImpl bdriver = (GameMovableDriverDefaultImpl) b.getDriver();
        bdriver.setStrategy(ballStr);

        throw new IllegalMoveException();
    }
    
    //code duplication :s
    public void moveBlockerRule(Ball b, BreakableWall w) throws IllegalMoveException {
    	SpeedVector sv = b.getSpeedVector();
        Point p = sv.getDirection();
        MoveStrategyBall ballStr;
        
        
        // THIS IS A AWFUL WAY.. we must change it and it does not work...
        // Problem when the ball hit two block at the same time !
        if(w.getPos().x < b.getPosition().x){
            ballStr = new MoveStrategyBall(-p.x, p.y);
        }else if(w.getPos().x >= b.getPosition().x){
            ballStr = new MoveStrategyBall(-p.x, p.y);
        }else if(w.getPos().y < b.getPosition().y){
        	ballStr = new MoveStrategyBall(p.x, -p.y);
        }else if(w.getPos().y >= b.getPosition().y ){
        	ballStr = new MoveStrategyBall(p.x, -p.y);
        }else{
        	ballStr = new MoveStrategyBall(-p.x, -p.y);
        }
        
        GameMovableDriverDefaultImpl bdriver = (GameMovableDriverDefaultImpl) b.getDriver();
        bdriver.setStrategy(ballStr);

        throw new IllegalMoveException();
    }
}

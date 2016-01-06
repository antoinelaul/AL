package controllers;

import entities.Ball;
import entities.BreakableBrick;
import entities.EndLine;
import entities.Player;
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
    private final ObservableValue<Integer> score;
    private final ObservableValue<Integer> life;
    private final ObservableValue<Boolean> endOfGame;


    public BreakoutOverlapRules(ObservableValue<Integer> life, ObservableValue<Integer> score,
                                ObservableValue<Boolean> endOfGame) {
        this.life = life;
        this.score = score;
        this.endOfGame = endOfGame;
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

    public void overlapRule(Ball ball, BreakableBrick bkw) {
        SpeedVector sv = ball.getSpeedVector();
        Point p = sv.getDirection();
        MoveStrategyBall ballStr;
        ballStr = new MoveStrategyBall(p.x, p.y);

        // First way
        /*if(bkw.getPosition().x < ball.getPosition().x){
            ballStr = new MoveStrategyBall(-p.x, p.y);
        }else if(bkw.getPosition().x >= ball.getPosition().x){
            ballStr = new MoveStrategyBall(-p.x, p.y);
        }else if(bkw.getPosition().y < ball.getPosition().y){
            ballStr = new MoveStrategyBall(p.x, -p.y);
        }else if(bkw.getPosition().y >= ball.getPosition().y ){
            ballStr = new MoveStrategyBall(p.x, -p.y);
        }else{
            ballStr = new MoveStrategyBall(-p.x, -p.y);
        }*/

        //Second way
        if(bkw.getPosition().getX() == ball.getPosition().getX()){
            ballStr = new MoveStrategyBall(p.x, -p.y);
        }else if(bkw.getPosition().getY() == ball.getPosition().getY()) {
            ballStr = new MoveStrategyBall(-p.x, p.y);
        }

        //Third way, still does not work..
        // Avant ça ne marchait pas car je ne prenais pas en compte la taille de la balle ou de la brique
        // pour le calcul des positions. Là je l'ai rajouter avec aussi un setPosition() pour que la balle
        // ne soit pas "enfoncée" dans la brique mais ça fait de la merde toujours
        /*
        if ((bkw.getPosition().getY() + bkw.getBoundingBox().getHeight()) >= ball.getPosition().getY()) {
            ball.setPosition(new Point(ball.getPosition().x,
                                      (bkw.getPosition().y + (int)bkw.getBoundingBox().getHeight())));
            ballStr = new MoveStrategyBall(p.x, -p.y);
        }else if (bkw.getPosition().getY() <= (ball.getPosition().getY() + ball.getBoundingBox().getHeight())){
            ball.setPosition(new Point(ball.getPosition().x,
                                      (bkw.getPosition().y - (int)bkw.getBoundingBox().getHeight())));
            ballStr = new MoveStrategyBall(p.x, -p.y);
        }else if(bkw.getPosition().getX() <= (ball.getPosition().getY() + ball.getBoundingBox().getWidth())) {
            ball.setPosition(new Point(ball.getPosition().y,
                                      (bkw.getPosition().x - (int)bkw.getBoundingBox().getWidth())));
            ballStr = new MoveStrategyBall(-p.x, p.y);
        }else if((bkw.getPosition().getX() + bkw.getBoundingBox().getWidth()) >= ball.getPosition().getX()) {
            ball.setPosition(new Point(ball.getPosition().y,
                                      (bkw.getPosition().x + (int)bkw.getBoundingBox().getWidth())));
            ballStr = new MoveStrategyBall(-p.x, p.y);
        }else{
            ballStr = new MoveStrategyBall(-p.x, -p.y);
        }*/

        GameMovableDriverDefaultImpl bdriver = (GameMovableDriverDefaultImpl) ball.getDriver();
        bdriver.setStrategy(ballStr);

        score.setValue(score.getValue() + bkw.getValue());

        universe.removeGameEntity(bkw);
        wallBrokenHandler();
    }

    public void overlapRule(Ball ball, EndLine line) {
        life.setValue(life.getValue() - 1);
    }

    private void wallBrokenHandler() {
        wallBroken++;
        if (wallBroken >= totalBreakableWalls) {
            endOfGame.setValue(true);
        }
    }

}


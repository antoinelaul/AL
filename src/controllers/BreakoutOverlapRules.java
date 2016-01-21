package controllers;

import entities.Ball;
import entities.BreakableBrick;
import entities.EndLine;
import entities.Player;
import gameframework.base.MoveStrategy;
import gameframework.base.ObservableValue;
import gameframework.base.SpeedVector;
import gameframework.game.GameMovableDriverDefaultImpl;
import gameframework.game.GameUniverse;
import gameframework.game.OverlapRulesApplierDefaultImpl;
import models.BreakoutBallDriver;
import models.MoveStrategyBall;

import java.awt.*;



public class BreakoutOverlapRules extends OverlapRulesApplierDefaultImpl {
    private static final int SPRITE_SIZE = 16;

    protected GameUniverse universe;
    private int totalBreakableWalls = 0;
    private int wallBroken = 0;
    private final ObservableValue<Integer> score;
    private final ObservableValue<Integer> life;
    private final ObservableValue<Boolean> endOfGame;
    private final ObservableValue<Player> observablePlayer;
    private final ObservableValue<Ball> observableBall;


    public BreakoutOverlapRules(ObservableValue<Integer> life, ObservableValue<Integer> score,
                                ObservableValue<Boolean> endOfGame,
                                ObservableValue<Player> player, ObservableValue<Ball> ball) {
        this.life = life;
        this.score = score;
        this.endOfGame = endOfGame;
        this.observablePlayer = player;
        this.observableBall = ball;
    }

    public void setTotalBreakableWalls(int totalBkw) {
        this.totalBreakableWalls = totalBkw;
    }

    @Override
    public void setUniverse(GameUniverse universe) {
        this.universe = universe;
    }

    public void overlapRule(Player player, Ball ball) {
        SpeedVector speedVector = ball.getSpeedVector();
        Point dir = speedVector.getDirection();
        Point ballPosition = ball.getPosition();
        Point playerPosition = player.getPosition();
        Rectangle playerBoundingBox = player.getBoundingBox();

        // Change x speed according ball position. Unfortunately, it's not possible to use floats.
        if (ballPosition.x - playerPosition.x < playerBoundingBox.width / 2)
            ((GameMovableDriverDefaultImpl) ball.getDriver()).setStrategy(
                    new MoveStrategyBall(dir.x - 1, -dir.y));

        else
            ((GameMovableDriverDefaultImpl) ball.getDriver()).setStrategy(
                    new MoveStrategyBall(dir.x + 1, -dir.y));

        // Place the ball out of the player, to avoid multiple collisions.
        ball.setPosition(new Point(ballPosition.x, playerPosition.y - playerBoundingBox.height));
    }

    public void overlapRule(Ball ball, BreakableBrick brick) {
      //  SpeedVector speedVector = ball.getSpeedVector();
      //  Point point = speedVector.getDirection();
       // MoveStrategyBall ballStr = new MoveStrategyBall(point.x, point.y);

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
      /*  if(brick.getPosition().getX() == ball.getPosition().getX()){
            ballStr = new MoveStrategyBall(point.x, -point.y);
        }else if(brick.getPosition().getY() == ball.getPosition().getY()) {
            ballStr = new MoveStrategyBall(-point.x, point.y);
        } */

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

     /*   GameMovableDriverDefaultImpl ballDriver = (GameMovableDriverDefaultImpl) ball.getDriver();
        ballDriver.setStrategy(ballStr); */

        Point ballPosition = ball.getPosition();
        Point brickPosition = brick.getPosition();
        Rectangle brickBoundingBox = brick.getBoundingBox();
        Point dir = ball.getSpeedVector().getDirection();
        int dx = dir.x, dy = dir.y;
        int deltax = ballPosition.x - brickPosition.x,
                deltay = ballPosition.y - brickPosition.y;

        /*if (deltax <= 0 || deltax >= brickBoundingBox.width / 2)
            dx = -dx;

        if (deltay <= 0 || deltay >= brickBoundingBox.height / 2)
            dy = -dy;*/

       // System.out.println(deltax + ", " + deltay);
        ((GameMovableDriverDefaultImpl) ball.getDriver()).setStrategy(new MoveStrategyBall(dx, dy));

        score.setValue(score.getValue() + brick.getValue());
        universe.removeGameEntity(brick);
        wallBrokenHandler();
    }

    public void overlapRule(Ball ball, EndLine line) {
        // Strategy change.
        BreakoutBallDriver driver = (BreakoutBallDriver) ball.getDriver();
        driver.setStrategy(new MoveStrategyBall(2, -1));

        // Put the ball just above the player.
        Player player = observablePlayer.getValue();
        Point playerPosition = player.getPosition();
        Rectangle playerBoundingBox = player.getBoundingBox();
        ball.setPosition(
                new Point(playerPosition.x / SPRITE_SIZE * SPRITE_SIZE,     // To correct place the ball according the 'grid'.
                        playerPosition.y - playerBoundingBox.height));

        life.setValue(life.getValue() - 1);
    }

    private void wallBrokenHandler() {
        wallBroken++;
        if (wallBroken >= totalBreakableWalls) {
            endOfGame.setValue(true);
        }
    }

}


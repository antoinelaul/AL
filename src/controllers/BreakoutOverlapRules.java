package controllers;

import entities.*;
import entities.Image;
import entities.bonus.*;
import entities.bricks.BasicBrick;
import entities.bricks.BonusBrick;
import entities.bricks.BreakableBrick;
import entities.bricks.ExplosionBrick;
import entities.movables.Ball;
import entities.movables.Bullet;
import entities.movables.Player;
import gameframework.base.ObservableValue;
import gameframework.base.Overlap;
import gameframework.base.SpeedVector;
import gameframework.game.GameEntity;
import gameframework.game.GameMovableDriverDefaultImpl;
import gameframework.game.GameUniverse;
import gameframework.game.OverlapRulesApplierDefaultImpl;
import models.MoveStrategyLine;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;


public class BreakoutOverlapRules extends OverlapRulesApplierDefaultImpl {
    private static final int SPRITE_SIZE = 16;
    private static final Random rand = new Random();
    private static AbstractBonus bonus[];

    private Canvas canvas;
    private GameUniverse universe;
    private int totalBreakableWalls = 0;
    private int wallBroken = 0;
    private final ObservableValue<Integer> score;
    private final ObservableValue<Integer> life;
    private final ObservableValue<Boolean> endOfGame;
    private final ObservableValue<Player> observablePlayer;
    private final ObservableValue<Ball> observableBall;


    public BreakoutOverlapRules(Canvas canvas,
                                ObservableValue<Integer> life, ObservableValue<Integer> score,
                                ObservableValue<Boolean> endOfGame,
                                ObservableValue<Player> player, ObservableValue<Ball> ball) {
        this.canvas = canvas;
        this.life = life;
        this.score = score;
        this.endOfGame = endOfGame;
        this.observablePlayer = player;
        this.observableBall = ball;

        // Bonus to clone.
        bonus = new AbstractBonus[] {
                new BombBonus(canvas, SPRITE_SIZE * 2),         // Same chance to appear.
                new WeaponBonus(canvas, SPRITE_SIZE * 2),
                new FireBallBonus(canvas, SPRITE_SIZE * 2),

                new LifeBonus(canvas, SPRITE_SIZE * 2),         // Rarer.
                new MysteriousBonus(canvas, SPRITE_SIZE * 2),   // Very rarer.
        };
    }

    public void setTotalBreakableWalls(int totalBkw) {
        this.totalBreakableWalls = totalBkw;
    }

    @Override
    public void applyOverlapRules(Vector<Overlap> overlappables) {
        Player p = observablePlayer.getValue();
        if (p.isFiring()) playerFire(p);

        super.applyOverlapRules(overlappables);
    }

    public void playerFire(Player player) {
        Point playerPosition = player.getPosition();
        Rectangle playerBoundingBox = player.getBoundingBox();

        Bullet b1 = new Bullet(canvas, SPRITE_SIZE / 2, SPRITE_SIZE * 2);
        Bullet b2 = new Bullet(canvas, SPRITE_SIZE / 2, SPRITE_SIZE * 2);
        Rectangle ballBoundingBox = b1.getBoundingBox();

        b1.setPosition(new Point(playerPosition.x - ballBoundingBox.width, playerPosition.y - SPRITE_SIZE));
        b2.setPosition(new Point(playerPosition.x + playerBoundingBox.width, playerPosition.y - SPRITE_SIZE));
        ((GameMovableDriverDefaultImpl) b1.getDriver()).setStrategy(new MoveStrategyLine(0, -2));
        ((GameMovableDriverDefaultImpl) b2.getDriver()).setStrategy(new MoveStrategyLine(0, -2));

        universe.addGameEntity(b1);
        universe.addGameEntity(b2);
    }

    @Override
    public void setUniverse(GameUniverse universe) {
        this.universe = universe;
    }


    /**
     * Interaction between player and ball.
     */
    public void overlapRule(Player player, Ball ball) {
        SpeedVector speedVector = ball.getSpeedVector();
        Point dir = speedVector.getDirection();
        Point ballPosition = ball.getPosition();
        Point playerPosition = player.getPosition();
        Rectangle playerBoundingBox = player.getBoundingBox();

        // Change x speed according ball position. Unfortunately, it's not possible to use floats.
        if (ballPosition.x - playerPosition.x < playerBoundingBox.width / 2)
            ((GameMovableDriverDefaultImpl) ball.getDriver()).setStrategy(
                    new MoveStrategyLine(dir.x - 1, -dir.y));

        else
            ((GameMovableDriverDefaultImpl) ball.getDriver()).setStrategy(
                    new MoveStrategyLine(dir.x + 1, -dir.y));

        // Place the ball out of the player, to avoid multiple collisions.
        ball.setPosition(new Point(ballPosition.x, playerPosition.y - playerBoundingBox.height));
    }


    /**
     *  Bonus handling when player catch one.
     */
    public void overlapRule(Player player, AbstractBonus bonus) {
        universe.removeGameEntity(bonus);
    }

    public void overlapRule(Player player, LifeBonus bonus) {
        overlapRule(player, (AbstractBonus) bonus);
        life.setValue(life.getValue() + 1);
    }

    public void overlapRule(Player player, WeaponBonus bonus) {
        overlapRule(player, (AbstractBonus) bonus);
        player.setTimeBullet(15);
    }

    public void overlapRule(Player player, FireBallBonus bonus) {
        overlapRule(player, (AbstractBonus) bonus);

        Ball ball = observableBall.getValue();
        ball.setTimeFire(100);
        ball.changeImage();
    }

    public void overlapRule(Player player, BombBonus bonus) {
        overlapRule(player, (AbstractBonus) bonus);

        ArrayList<BreakableBrick> bricks = new ArrayList<>();
        Iterator<GameEntity> it =  universe.gameEntities();

        while (it.hasNext()) {
            GameEntity entity = it.next();
            if (entity instanceof BreakableBrick)
                bricks.add((BreakableBrick) entity);
        }

        explosionHandler(bricks.get(rand.nextInt(bricks.size())));
    }

    public void overlapRule(Player player, MysteriousBonus bonus) {
        overlapRule(player, (AbstractBonus) bonus);

        Point position = player.getPosition();
        entities.Image image = new Image(canvas, "assets/images/mysterious.png",
                0, position.y - 150, 150, 150);
        universe.addGameEntity(image);
    }


    /**
     * Collision between ball and bricks handling.
     */
    public void overlapRule(Ball ball, BreakableBrick brick) {
        Point ballPosition = ball.getPosition();
        Point brickPosition = brick.getPosition();
        Point direction = ball.getSpeedVector().getDirection();
        MoveStrategyLine ballStr = new MoveStrategyLine(direction.x, direction.y);

        //Brick corners position
        Point brickLeftUpCorner = brickPosition;
        Point brickLeftDownCorner = new Point(brickPosition.x, brickPosition.y + brick.getHeight());
        Point brickRightUpCorner = new Point(brickPosition.x + brick.getWidth(), brickPosition.y);
        Point brickRightDownCorner = new Point(
                brickPosition.x + brick.getWidth(),
                brickPosition.y + brick.getHeight()
        );

        //Ball hit points position
        int sizeBall = ball.getSize();
        Point ballUpMiddle = new Point(ballPosition.x + sizeBall/2, ballPosition.y);
        Point ballDownMiddle = new Point(ballPosition.x + sizeBall/2, ballPosition.y + sizeBall);
        Point ballRightMiddle = new Point(ballPosition.x + sizeBall, ballPosition.y + sizeBall/2);
        Point ballLeftMiddle = new Point(ballPosition.x, ballPosition.y + sizeBall/2);

        //Sout to understrand the behavior
        System.out.println("FUCKING HIT BITCH");
        System.out.println();
        System.out.println("leDo/up/riDo");
        System.out.println("" + brickLeftDownCorner + " / " + ballUpMiddle + " / " + brickRightDownCorner);
        System.out.println("leUp/down/riUp");
        System.out.println("" + brickLeftUpCorner + " / " + ballDownMiddle + " / " + brickRightUpCorner);
        System.out.println("leUp/right/leDo");
        System.out.println("" + brickLeftUpCorner + " / " + ballRightMiddle + " / " + brickLeftDownCorner);
        System.out.println("riDo/left/riUp");
        System.out.println("" + brickRightDownCorner + " / " + ballLeftMiddle + " / " + brickRightUpCorner);
        System.out.println();

        if(!ball.isOnFire()) {
            if(((brickLeftDownCorner.x <= ballUpMiddle.x) &&
                    (ballUpMiddle.x <= brickRightDownCorner.x )) ||
                    ((brickLeftUpCorner.x <= ballDownMiddle.x) &&
                            (ballDownMiddle.x <= brickRightUpCorner.x))
                    ){
                ballStr = new MoveStrategyLine(direction.x, -direction.y);
            }
            else if(((brickLeftUpCorner.y <= ballRightMiddle.y) &&
                    (ballRightMiddle.y <= brickLeftDownCorner.y)) ||
                    ((brickRightUpCorner.y <= ballLeftMiddle.y) &&
                            (ballLeftMiddle.y <= brickRightDownCorner.y))
                    ){
                ballStr = new MoveStrategyLine(-direction.x, direction.y);
            }
        }

        GameMovableDriverDefaultImpl ballDriver = (GameMovableDriverDefaultImpl) ball.getDriver();
        ballDriver.setStrategy(ballStr);

        wallBrokenHandler(brick);
    }

    public void overlapRule(Ball ball, BasicBrick brick) {
        overlapRule(ball, (BreakableBrick) brick);
    }

    public void overlapRule(Ball ball, BonusBrick brick) {
        overlapRule(ball, (BreakableBrick) brick);
        bonusHandler(brick.getPosition());
    }


    public void overlapRule(Ball ball, ExplosionBrick brick) {
        overlapRule(ball, (BreakableBrick) brick);
        explosionHandler(brick);
    }

    // Avoid infinite display of explosion.
    public void overlapRule(Ball ball, ExplosionBonus bonus) {
        universe.removeGameEntity(bonus);
    }




    /**
     * Interaction between bullets and bricks handling.
     */
    public void overlapRule(Bullet bullet, BreakableBrick brick) {
        universe.removeGameEntity(bullet);
        wallBrokenHandler(brick);
    }

    public void overlapRule(Bullet bullet, BasicBrick brick) {
        overlapRule(bullet, (BreakableBrick) brick);
    }

    public void overlapRule(Bullet bullet, BonusBrick brick) {
        overlapRule(bullet, (BreakableBrick) brick);
        bonusHandler(brick.getPosition());
    }

    public void overlapRule(Bullet bullet, ExplosionBrick brick) {
        overlapRule(bullet, (BreakableBrick) brick);
        explosionHandler(brick);
    }


    /**
     * Collision between explosion and bricks handling.
     */
    public void overlapRule(BreakableBrick brick, ExplosionBonus bonus) {
        universe.removeGameEntity(bonus);
        wallBrokenHandler(brick);
    }

    public void overlapRule(BasicBrick brick, ExplosionBonus bonus) {
        overlapRule((BreakableBrick) brick, bonus);
    }

    public void overlapRule(BonusBrick brick, ExplosionBonus bonus) {
        overlapRule((BreakableBrick) brick, bonus);
        bonusHandler(brick.getPosition());
    }

    public void overlapRule(ExplosionBrick brick, ExplosionBonus bonus) {
        overlapRule((BreakableBrick) brick, bonus);
        explosionHandler(brick);
    }


    /**
     * The ball has reached the end line... player loses whether a live, whether the game.
     * If the former option is possible, the ball is put just above the player.
     */
    public void overlapRule(Ball ball, EndLine line) {
        // Strategy change.
        ((BreakoutBallDriver) ball.getDriver()).setStrategy(new MoveStrategyLine(2, -1));

        // Put the ball just above the player.
        Player player = observablePlayer.getValue();
        Point playerPosition = player.getPosition();
        Rectangle playerBoundingBox = player.getBoundingBox();
        ball.setPosition(
                new Point(playerPosition.x / SPRITE_SIZE * SPRITE_SIZE,     // To correct place the ball according the 'grid'.
                        playerPosition.y - playerBoundingBox.height));

        life.setValue(life.getValue() - 1);
    }


    /**
     * Entities are removed when they reach end line (except the ball).
     */
    public void overlapRule(LifeBonus bonus, EndLine line) {
        universe.removeGameEntity(bonus);
    }

    public void overlapRule(WeaponBonus bonus, EndLine line) {
        universe.removeGameEntity(bonus);
    }

    public void overlapRule(BombBonus bonus, EndLine line) {
        universe.removeGameEntity(bonus);
    }

    public void overlapRule(MysteriousBonus bonus, EndLine line) {
        universe.removeGameEntity(bonus);
    }

    public void overlapRule(Bullet bullet, EndLine line) {
        universe.removeGameEntity(bullet);
    }

    public void overlapRule(FireBallBonus bonus, EndLine line) {
        universe.removeGameEntity(bonus);
    }

    /**
     * End game handling.
     */
    private void wallBrokenHandler(BreakableBrick brick) {
        score.setValue(score.getValue() + brick.getValue());
        universe.removeGameEntity(brick);
        wallBroken++;

        if (wallBroken >= totalBreakableWalls)
            endOfGame.setValue(true);
    }


    /**
     * Choose a bonus in the possible ones. Put its start point at the given position.
     */
    private void bonusHandler(Point position) {
        AbstractBonus b = bonus[randomBonus()].clone();

        b.setPosition(position);
        ((GameMovableDriverDefaultImpl) b.getDriver()).setStrategy(new MoveStrategyLine(0, 1));
        universe.addGameEntity(b);
    }


    /**
     * Explosion, with variable diameter.
     */
    private void explosionHandler(BreakableBrick brick) {
        Point brickPosition = brick.getPosition();
        AbstractBonus bonus = new ExplosionBonus(canvas, SPRITE_SIZE * (6 + rand.nextInt(4)));      // Random size.
        Rectangle bbb = brick.getBoundingBox();
        Rectangle bonusBoundingBox = bonus.getBoundingBox();

        bonus.setPosition(new Point(
                brickPosition.x + (bbb.width - bonusBoundingBox.width) / 2,     // Center explosion according the bricks.
                brickPosition.y + (bbb.height - bonusBoundingBox.height) / 2));
        universe.addGameEntity(bonus);
    }


    private int randomBonus() {
        int alea = rand.nextInt(100);
        System.out.println(alea);

        if (alea <= 75) return rand.nextInt(3); // 75% of time, a normal bonus (bomb, weapon, fireball.
        else if (alea <= 98) return 3;          // Life bonus.
        else return 4;                          // Mysterious bonus, very difficult to obtain.
    }
}
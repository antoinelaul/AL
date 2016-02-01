package models;


import controllers.BreakoutBallMoveBlockerApplier;
import controllers.BreakoutBallDriver;
import controllers.BreakoutOverlapRules;
import entities.*;
import entities.brick.*;
import gameframework.base.ObservableValue;
import gameframework.game.*;

import java.awt.*;
import java.io.*;
import java.util.Scanner;


public class BreakoutGameLevel extends GameLevelDefaultImpl {
    private final static int WIDTH = 640;
    private final static int HEIGHT = 480;
    private final static int SPRITE_SIZE = 16;
    private final static int SPRITE_OFFSET_X = 4;  // offset for placement.
    private final static int SPRITE_OFFSET_Y = 3;  // offset for placement.

    private static AbstractBrick bricks[];

    private ObservableValue<Player> observablePlayer;
    private ObservableValue<Ball> observableBall;

    private int values[][];
    private Canvas canvas;
    private int width;
    private int height;


    public BreakoutGameLevel(Game g, String filename) throws IOException {
        super(g);
        canvas = g.getCanvas();

        Scanner sc = new Scanner(new File(filename));
        width = sc.nextInt();
        height = sc.nextInt();

        values = new int[height][width];
        int cursor = 0;

        while (sc.hasNextInt()) {
            values[cursor / width][cursor % width] = sc.nextInt();
            cursor++;
        }

        // Bricks to clone for level configuration.
        bricks = new AbstractBrick[] {
            new UnbreakableBrick(canvas, 0, 0, SPRITE_SIZE * 2, SPRITE_SIZE),
            new BasicBrick(canvas, 0, 0, SPRITE_SIZE * 2, SPRITE_SIZE),
            new BonusBrick(canvas, 0, 0, SPRITE_SIZE * 2, SPRITE_SIZE),
            new ExplosionBrick(canvas, 0, 0, SPRITE_SIZE * 2, SPRITE_SIZE),
        };

        observablePlayer = new ObservableValue<>(new Player(canvas, 4 * SPRITE_SIZE, SPRITE_SIZE));
        observableBall = new ObservableValue<>(new Ball(canvas, SPRITE_SIZE));
    }

    @Override
    protected void init() {
        OverlapProcessor overlapProcessor = new OverlapProcessorDefaultImpl();
        MoveBlockerChecker moveBlockerChecker = new MoveBlockerCheckerDefaultImpl();
        moveBlockerChecker.setMoveBlockerRules(new BreakoutBallMoveBlockerApplier());

        BreakoutOverlapRules overlapRules =
                new BreakoutOverlapRules(canvas, life[0], score[0], endOfGame, observablePlayer, observableBall);
        overlapProcessor.setOverlapRules(overlapRules);

        universe = new GameUniverseDefaultImpl(moveBlockerChecker, overlapProcessor);
        overlapRules.setUniverse(universe);

        gameBoard = new BreakoutUniverseViewPort(canvas, universe);
        ((CanvasDefaultImpl) canvas).setDrawingGameBoard(gameBoard);

        int totalBreakableWalls = 0;

        // Filling up the universe with basic non movable entities and inclusion in the universe
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                 if (values[i][j] != 0) {
                    AbstractBrick brick = bricks[values[i][j] - 1].clone();

                    if (values[i][j] > 1)               // Brick 1 is Unbreakable.
                        totalBreakableWalls++;

                    brick.setPosition(new Point(
                            (2 * j  + SPRITE_OFFSET_X) * SPRITE_SIZE,           // x
                            (i      + SPRITE_OFFSET_Y) * SPRITE_SIZE));         // y

                    universe.addGameEntity(brick);
                }
            }
        }

        overlapRules.setTotalBreakableWalls(totalBreakableWalls);

        // Borders.
        universe.addGameEntity(new InvisibleWall(-10, 0, 10, HEIGHT, false));
        universe.addGameEntity(new InvisibleWall(WIDTH, 0, 10, HEIGHT, false));
        universe.addGameEntity(new InvisibleWall(0, -10, WIDTH, 10, true));

        universe.addGameEntity(new EndLine(0, HEIGHT, WIDTH, 10));      // For ball and bonus.
        universe.addGameEntity(new EndLine(0, -20, WIDTH, 10));      // For bullets.


        // Player configuration.
        Player player = observablePlayer.getValue();

        GameMovableDriverDefaultImpl playerDriver = new GameMovableDriverDefaultImpl();
        MoveStrategyPlayer keyStr = new MoveStrategyPlayer();
        playerDriver.setStrategy(keyStr);
        playerDriver.setmoveBlockerChecker(moveBlockerChecker);
        player.setPosition(new Point(WIDTH / 2, HEIGHT - SPRITE_SIZE));

        canvas.addKeyListener(keyStr);
        player.setDriver(playerDriver);
        universe.addGameEntity(player);

        // Ball configuration.
        Ball ball = observableBall.getValue();
        GameMovableDriverDefaultImpl ballDriver = new BreakoutBallDriver();
        MoveStrategyLine ballStr = new MoveStrategyLine(2, -1);
        ballDriver.setStrategy(ballStr);
        ballDriver.setmoveBlockerChecker(moveBlockerChecker);

        ball.setPosition(new Point(WIDTH / 2,  HEIGHT - 2 * SPRITE_SIZE));
        ball.setDriver(ballDriver);
        universe.addGameEntity(ball);
    }
}

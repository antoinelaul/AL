package models;


import controllers.BallMoveBlockerApplier;
import controllers.BreakoutOverlapRules;
import entities.*;
import gameframework.base.ObservableValue;
import gameframework.game.*;

import java.awt.*;
import java.io.*;
import java.util.Scanner;


public class BreakoutGameLevel extends GameLevelDefaultImpl {
    private final static int WIDTH = 640;
    private final static int HEIGHT = 480;
    public static final int SPRITE_SIZE = 16;
    public static final int SPRITE_OFFSET_X = 4;  // offset for placement.
    public static final int SPRITE_OFFSET_Y = 3;  // offset for placement.

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

        observablePlayer = new ObservableValue<>(new Player(canvas, 4 * SPRITE_SIZE, SPRITE_SIZE));
        observableBall = new ObservableValue<>(new Ball(canvas, SPRITE_SIZE));
    }

    @Override
    protected void init() {
        OverlapProcessor overlapProcessor = new OverlapProcessorDefaultImpl();
        MoveBlockerChecker moveBlockerChecker = new MoveBlockerCheckerDefaultImpl();
        moveBlockerChecker.setMoveBlockerRules(new BallMoveBlockerApplier());

        BreakoutOverlapRules overlapRules =
                new BreakoutOverlapRules(life[0], score[0], endOfGame, observablePlayer, observableBall);
        overlapProcessor.setOverlapRules(overlapRules);

        universe = new GameUniverseDefaultImpl(moveBlockerChecker, overlapProcessor);
        overlapRules.setUniverse(universe);

        gameBoard = new BreakoutUniverseViewPort(canvas, universe);
        ((CanvasDefaultImpl) canvas).setDrawingGameBoard(gameBoard);

        int totalBreakableWalls = 0;

        // Filling up the universe with basic non movable entities and inclusion in the universe
        // Width is twice bigger than height.
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                if (values[i][j] == 1) {
                    universe.addGameEntity(new UnbreakableBrick(canvas,
                            (2 * j + SPRITE_OFFSET_X) * SPRITE_SIZE,  // x
                            (i     + SPRITE_OFFSET_Y) * SPRITE_SIZE,  // y
                            2 * SPRITE_SIZE, SPRITE_SIZE));
                }
                if(values[i][j] == 2) {
                    universe.addGameEntity(new BreakableBrick(canvas,
                            (2 * j + SPRITE_OFFSET_X) * SPRITE_SIZE,  // x
                            (i     + SPRITE_OFFSET_Y) * SPRITE_SIZE,  // y
                            2 * SPRITE_SIZE, SPRITE_SIZE));
                    totalBreakableWalls++;
                }
            }
        }
        overlapRules.setTotalBreakableWalls(totalBreakableWalls);

        // Borders.
        universe.addGameEntity(new InvisibleWall(-10, 0, 10, HEIGHT, false));
        universe.addGameEntity(new InvisibleWall(WIDTH, 0, 10, HEIGHT, false));
        universe.addGameEntity(new InvisibleWall(0, -10, WIDTH, 10, true));
        universe.addGameEntity(new EndLine(0, HEIGHT, WIDTH, 10));


        // Player configuration.
        Player player = observablePlayer.getValue();

        GameMovableDriverDefaultImpl pdriver = new GameMovableDriverDefaultImpl();
        MoveStrategyPlayer keyStr = new MoveStrategyPlayer();
        pdriver.setStrategy(keyStr);
        pdriver.setmoveBlockerChecker(moveBlockerChecker);
        player.setPosition(new Point(WIDTH / 2, HEIGHT - SPRITE_SIZE));

        canvas.addKeyListener(keyStr);
        player.setDriver(pdriver);
        universe.addGameEntity(player);

        // Ball configuration.
        Ball ball = observableBall.getValue();
        GameMovableDriverDefaultImpl bdriver = new BreakoutBallDriver();
        MoveStrategyBall ballStr = new MoveStrategyBall(2, -1);
        bdriver.setStrategy(ballStr);
        bdriver.setmoveBlockerChecker(moveBlockerChecker);

        ball.setPosition(new Point(WIDTH / 2,  HEIGHT - 2 * SPRITE_SIZE));
        ball.setDriver(bdriver);
        universe.addGameEntity(ball);
    }
}

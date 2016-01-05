package models;


import controllers.BallMoveBlocker;
import controllers.BreakoutOverlapRules;
import entities.*;
import gameframework.game.*;

import java.awt.*;
import java.io.*;
import java.util.Scanner;


public class BreakoutGameLevel extends GameLevelDefaultImpl {
    private final static int WIDTH = 640;
    private final static int HEIGHT = 480;
    private int values[][];

    private Canvas canvas;

    public static final int SPRITE_SIZE = 16;

    public BreakoutGameLevel(Game g, String filename) throws IOException {
        super(g);
        canvas = g.getCanvas();

        Scanner sc = new Scanner(new File(filename));
        int width = sc.nextInt();
        int height = sc.nextInt();

        values = new int[height][width];
        int cursor = 0;

        while (sc.hasNextInt()) {
            values[cursor / width][cursor % width] = sc.nextInt();
            cursor++;
        }
    }

    @Override
    protected void init() {
        OverlapProcessor overlapProcessor = new OverlapProcessorDefaultImpl();
        MoveBlockerChecker moveBlockerChecker = new MoveBlockerCheckerDefaultImpl();
        moveBlockerChecker.setMoveBlockerRules(new BallMoveBlocker());

        BreakoutOverlapRules overlapRules = new BreakoutOverlapRules(life[0], score[0], endOfGame);
        overlapProcessor.setOverlapRules(overlapRules);

        universe = new GameUniverseDefaultImpl(moveBlockerChecker, overlapProcessor);
        overlapRules.setUniverse(universe);

        gameBoard = new BreakoutUniverseViewPort(canvas, universe);
        ((CanvasDefaultImpl) canvas).setDrawingGameBoard(gameBoard);


        int totalBreakableWalls = 0;

        // Filling up the universe with basic non movable entities and inclusion in the universe
        for (int i = 0; i < 32; ++i) {
            for (int j = 0; j < 41; ++j) {
                if (values[i][j] == 1) {
                    universe.addGameEntity(new UnbreakableBrick(canvas, j * SPRITE_SIZE, i * SPRITE_SIZE, 16, 16));
                }
                if(values[i][j] == 2) {
                    universe.addGameEntity(new BreakableBrick(canvas, j * SPRITE_SIZE, i * SPRITE_SIZE));
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
        Player player = new Player(canvas);

        GameMovableDriverDefaultImpl pdriver = new GameMovableDriverDefaultImpl();
        MoveStrategyHorizontal keyStr = new MoveStrategyHorizontal();
        pdriver.setStrategy(keyStr);
        pdriver.setmoveBlockerChecker(moveBlockerChecker);
        player.setPosition(new Point(WIDTH / 2,  HEIGHT - 16));

        canvas.addKeyListener(keyStr);
        player.setDriver(pdriver);
        universe.addGameEntity(player);

        // Ball configuration.
        Ball ball = new Ball(canvas);
        GameMovableDriverDefaultImpl bdriver = new GameMovableDriverDefaultImpl();
        MoveStrategyBall ballStr = new MoveStrategyBall(2, 1);
        bdriver.setStrategy(ballStr);
        bdriver.setmoveBlockerChecker(moveBlockerChecker);

        ball.setPosition(new Point(WIDTH / 2, HEIGHT - 16));
        ball.setDriver(bdriver);
        universe.addGameEntity(ball);
    }
}

import controllers.BreakoutOverlapRules;
import entities.Ball;
import entities.InvisibleWall;
import gameframework.base.MoveStrategyStraightLine;
import models.BallMoveBlocker;
import models.BreakoutUniverseViewPort;
import models.MoveStrategyBall;
import models.MoveStrategyHorizontal;
import entities.Player;

import gameframework.game.*;

import java.awt.*;


public class GameLevelOne extends GameLevelDefaultImpl {
    private Canvas canvas;
    private int width;
    private int height;

    public GameLevelOne(Game g, int w, int h) {
        super(g);

        width = w;
        height = h;
        canvas = g.getCanvas();
    }

    @Override
    protected void init() {
        OverlapProcessor overlapProcessor = new OverlapProcessorDefaultImpl();
        MoveBlockerChecker moveBlockerChecker = new MoveBlockerCheckerDefaultImpl();
        moveBlockerChecker.setMoveBlockerRules(new BallMoveBlocker());

        BreakoutOverlapRules overlapRules = new BreakoutOverlapRules();
        overlapProcessor.setOverlapRules(overlapRules);

        universe = new GameUniverseDefaultImpl(moveBlockerChecker, overlapProcessor);
        overlapRules.setUniverse(universe);

        gameBoard = new BreakoutUniverseViewPort(canvas, universe);
        ((CanvasDefaultImpl) canvas).setDrawingGameBoard(gameBoard);

        // Borders.
        universe.addGameEntity(new InvisibleWall(-10, 0, 10, height, false));
        universe.addGameEntity(new InvisibleWall(width, 0, 10, height, false));
        universe.addGameEntity(new InvisibleWall(0, -10, width, 10, true));

        // Player configuration.
        Player player = new Player(canvas);

        GameMovableDriverDefaultImpl pdriver = new GameMovableDriverDefaultImpl();
        MoveStrategyHorizontal keyStr = new MoveStrategyHorizontal();
        pdriver.setStrategy(keyStr);
        pdriver.setmoveBlockerChecker(moveBlockerChecker);
        player.setPosition(new Point(width / 2,  height - 16));

        canvas.addKeyListener(keyStr);
        player.setDriver(pdriver);
        universe.addGameEntity(player);

        // Ball configuration.
        Ball ball = new Ball(canvas);
        GameMovableDriverDefaultImpl bdriver = new GameMovableDriverDefaultImpl();
        MoveStrategyBall ballStr = new MoveStrategyBall(2, 1);
        bdriver.setStrategy(ballStr);
        bdriver.setmoveBlockerChecker(moveBlockerChecker);

        ball.setPosition(new Point(width / 2, height / 2));
        ball.setDriver(bdriver);
        universe.addGameEntity(ball);
    }
}

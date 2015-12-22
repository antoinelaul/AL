import controllers.BreakoutOverlapRules;
import entities.InvisibleWall;
import models.BreakoutUniverseViewPort;
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

        BreakoutOverlapRules overlapRules = new BreakoutOverlapRules();
        overlapProcessor.setOverlapRules(overlapRules);

        universe = new GameUniverseDefaultImpl(moveBlockerChecker, overlapProcessor);
        overlapRules.setUniverse(universe);

        gameBoard = new BreakoutUniverseViewPort(canvas, universe);
        ((CanvasDefaultImpl) canvas).setDrawingGameBoard(gameBoard);

        // Borders.
        universe.addGameEntity(new InvisibleWall(canvas, 0, 0, 10, height));
        universe.addGameEntity(new InvisibleWall(canvas, width - 10, 0, 10, height));
        universe.addGameEntity(new InvisibleWall(canvas, 0, 0, width, 10));

        // Player and strategy.
        Player player = new Player(canvas);

        GameMovableDriverDefaultImpl driver = new GameMovableDriverDefaultImpl();
        MoveStrategyHorizontal keyStr = new MoveStrategyHorizontal();
        driver.setStrategy(keyStr);
        player.setPosition(new Point(width / 2,  height - 16));

        canvas.addKeyListener(keyStr);
        player.setDriver(driver);
        universe.addGameEntity(player);
    }
}

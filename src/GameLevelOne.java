import controllers.BreakoutOverlapRules;
import models.MoveStrategyHorizontal;
import models.Player;

import gameframework.base.MoveStrategyKeyboard;
import gameframework.game.*;

import java.awt.*;


public class GameLevelOne extends GameLevelDefaultImpl {
    Canvas canvas;

    public GameLevelOne(Game g) {
        super(g);
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

        gameBoard = new GameUniverseViewPortDefaultImpl(canvas, universe);
        ((CanvasDefaultImpl) canvas).setDrawingGameBoard(gameBoard);


        Player player = new Player(canvas);

        GameMovableDriverDefaultImpl driver = new GameMovableDriverDefaultImpl();
        MoveStrategyHorizontal keyStr = new MoveStrategyHorizontal();
        driver.setStrategy(keyStr);
        player.setPosition(new Point(200, 450));

        canvas.addKeyListener(keyStr);
        player.setDriver(driver);
        universe.addGameEntity(player);
    }
}

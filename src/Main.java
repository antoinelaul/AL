import gameframework.game.GameDefaultImpl;
import gameframework.game.GameLevel;
import models.BreakoutGame;

import java.util.ArrayList;


public class Main {
    private static final int width = 640;
    private static final int height = 480;


    public static void main(String[] args) {
        BreakoutGame g = new BreakoutGame(width, height);
        ArrayList<GameLevel> levels = new ArrayList<>();
        levels.add(new GameLevelOne(g, width, height));

        g.setLevels(levels);
        g.start();
    }
}

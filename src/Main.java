import gameframework.game.GameDefaultImpl;
import gameframework.game.GameLevel;
import models.BreakoutGame;

import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        BreakoutGame g = new BreakoutGame();
        ArrayList<GameLevel> levels = new ArrayList<>();
        levels.add(new GameLevelOne(g));

        g.setLevels(levels);
        g.start();
    }
}

import gameframework.game.GameDefaultImpl;
import gameframework.game.GameLevel;
import models.BreakoutGame;
import models.BreakoutGameLevel;

import java.io.IOException;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) throws IOException {
        BreakoutGame g = new BreakoutGame();
        ArrayList<GameLevel> levels = new ArrayList<>();

        levels.add(new BreakoutGameLevel(g, "assets/levels/two.txt"));

        g.setLevels(levels);
        g.start();
    }
}

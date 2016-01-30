import gameframework.game.GameLevel;
import models.BreakoutGame;
import models.BreakoutGameLevel;

import java.io.IOException;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) throws IOException {
        BreakoutGame g = new BreakoutGame();
        g.setLevels(new String[] {
                "assets/levels/four.txt",
        });

        g.start();
    }
}
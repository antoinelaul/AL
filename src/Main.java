import models.BreakoutGame;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        BreakoutGame g = new BreakoutGame();
        g.setLevels(new String[] {
                "assets/levels/demo.txt",
        });

        g.start();
    }
}
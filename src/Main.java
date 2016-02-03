import models.BreakoutGame;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        BreakoutGame g = new BreakoutGame();
        g.setLevels(new String[] {
                "assets/levels/five.txt",
                "assets/levels/final2.txt",
                "assets/levels/final1.txt",
                "assets/levels/final3.txt",
        });

        g.start();
    }
}
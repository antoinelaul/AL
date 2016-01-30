package entities.bonus;

import java.awt.*;


public class BombBonus extends AbstractBonus {
    public BombBonus(Canvas canvas, int size) {
        super(canvas, size);
    }

    @Override
    public String getImage() {
        return "assets/images/bomb.png";
    }
}

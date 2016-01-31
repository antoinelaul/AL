package entities.bonus;

import java.awt.*;


public class FireBallBonus extends AbstractBonus {
    public FireBallBonus(Canvas canvas, int size) {
        super(canvas, size);
    }

    @Override
    public String getImage() {
        return "assets/images/fireball.png";
    }
}

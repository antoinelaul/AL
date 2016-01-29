package entities;


import java.awt.*;

public class LifeBonus extends AbstractBonus {

    public LifeBonus(Canvas canvas, int size) {
        super(canvas, size);
    }

    @Override
    String getImage() {
        return "assets/images/life.png";
    }
}

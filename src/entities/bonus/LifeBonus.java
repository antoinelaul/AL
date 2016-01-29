package entities.bonus;


import entities.bonus.AbstractBonus;

import java.awt.*;

public class LifeBonus extends AbstractBonus {

    public LifeBonus(Canvas canvas, int size) {
        super(canvas, size);
    }

    @Override
    public String getImage() {
        return "assets/images/life.png";
    }
}

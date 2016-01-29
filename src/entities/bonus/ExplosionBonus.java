package entities.bonus;

import entities.bonus.AbstractBonus;

import java.awt.*;


public class ExplosionBonus extends AbstractBonus {

    public ExplosionBonus(Canvas canvas, int size) {
        super(canvas, size);
    }

    @Override
    public String getImage() {
        return "assets/images/explosion.png";
    }
}

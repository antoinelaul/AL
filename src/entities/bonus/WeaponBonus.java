package entities.bonus;

import entities.bonus.AbstractBonus;

import java.awt.*;

public class WeaponBonus extends AbstractBonus {

    public WeaponBonus(Canvas canvas, int size) {
        super(canvas, size);
    }

    @Override
    public String getImage() {
        return "assets/images/weapon.png";
    }
}
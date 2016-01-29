package entities;

import java.awt.*;

public class WeaponBonus extends AbstractBonus {

    public WeaponBonus(Canvas canvas, int size) {
        super(canvas, size);
    }

    @Override
    String getImage() {
        return "assets/images/weapon.png";
    }
}
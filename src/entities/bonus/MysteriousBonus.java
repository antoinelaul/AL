package entities.bonus;

import java.awt.*;

public class MysteriousBonus extends AbstractBonus {
    public MysteriousBonus(Canvas canvas, int size) {
        super(canvas, size);
    }

    @Override
    public String getImage() {
        return "assets/images/question.png";
    }
}

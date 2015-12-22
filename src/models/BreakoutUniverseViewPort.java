package models;

import gameframework.game.GameUniverse;
import gameframework.game.GameUniverseViewPortDefaultImpl;

import java.awt.*;


public class BreakoutUniverseViewPort extends GameUniverseViewPortDefaultImpl {
    public BreakoutUniverseViewPort(Canvas canvas, GameUniverse universe) {
        super(canvas, universe);
    }

    @Override
    protected String backgroundImage() {
        return "assets/images/background_image.gif";
    }
}

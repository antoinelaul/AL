package entities;

import gameframework.base.Overlappable;
import gameframework.game.GameEntity;

import java.awt.*;


public class EndLine implements Overlappable, GameEntity {
    int x, y;
    int width;
    int height;

    public EndLine(int _x, int _y, int w, int h) {
        x = _x;
        y = _y;
        width = w;
        height = h;
    }

    @Override
    public Point getPosition() {
        return new Point(x, y);
    }

    @Override
    public Rectangle getBoundingBox() {
        return (new Rectangle(x, y, width, height));
    }
}

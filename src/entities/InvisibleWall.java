package entities;

import gameframework.game.GameEntity;
import gameframework.game.MoveBlocker;

import java.awt.*;


public class InvisibleWall implements MoveBlocker, GameEntity, Blocker {
    int x, y;
	int width;
    int height;
    boolean horizontal;


	public InvisibleWall(int _x, int _y, int w, int h, boolean horizontal) {
        x = _x;
		y = _y;
		width = w;
		height = h;

        this.horizontal = horizontal;
	}

	public Point getPosition() {
		return (new Point(x, y));
	}

	public Rectangle getBoundingBox() {
        return (new Rectangle(x, y, width, height));
	}

    public boolean isHorizontal() {
        return horizontal;
    }
}

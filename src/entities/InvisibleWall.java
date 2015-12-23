package entities;

import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.game.GameEntity;
import gameframework.game.MoveBlocker;

import java.awt.*;

public class InvisibleWall implements MoveBlocker, GameEntity {
    int x, y;
	int width;
    int height;
    boolean hztl;


	public InvisibleWall(int _x, int _y, int w, int h, boolean hztl) {
        x = _x;
		y = _y;
		width = w;
		height = h;

        this.hztl = hztl;
	}

	public Point getPos() {
		return (new Point(x, y));
	}

	public Rectangle getBoundingBox() {
        return (new Rectangle(x, y, width, height));
	}

    public boolean isHztl() {
        return hztl;
    }
}

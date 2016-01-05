package entities;

import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;
import gameframework.game.MoveBlocker;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class UnbreakableBrick implements Drawable, MoveBlocker, GameEntity {
	protected static DrawableImage image = null;
	int x, y;
	int width;
    int height;

	public UnbreakableBrick(Canvas defaultCanvas, int x, int y, int w, int h) {
		image = new DrawableImage("assets/images/wall.gif", defaultCanvas);
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
	}

	public void draw(Graphics g) {
		g.drawImage(image.getImage(), x, y, width, height,
				null);
	}

	public Rectangle getBoundingBox() {
		return (new Rectangle(x, y, width, height));
	}

	public Point getPosition() {
		return (new Point(x, y));
	}
}
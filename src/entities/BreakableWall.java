package entities;

import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.game.GameEntity;
import gameframework.game.MoveBlocker;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class BreakableWall implements Drawable, MoveBlocker, GameEntity {
	protected static DrawableImage image = null;
	int x, y;
	int width;
    int height;
    
	public BreakableWall(Canvas defaultCanvas, int x, int y, int w, int h) {
		image = new DrawableImage("assets/images/redWall.png", defaultCanvas);
		this.x = x;
		this.y = y;
		this.height = h;
		this.width = w;
	}

	public void draw(Graphics g) {
		g.drawImage(image.getImage(), x, y, width, height,
				null);
	}

	public Point getPos() {
		return (new Point(x, y));
	}

	public Rectangle getBoundingBox() {
		return (new Rectangle(x, y, width, height));
	}
}
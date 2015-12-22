package entities;

import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.game.GameEntity;
import gameframework.game.MoveBlocker;

import java.awt.*;

public class InvisibleWall implements Drawable, MoveBlocker, GameEntity {
    protected static DrawableImage image = null;
    int x, y;
	int width;
	int height;


	public InvisibleWall(Canvas canvas, int _x, int _y, int w, int h) {
        image = new DrawableImage("assets/images/wall.gif", canvas);

        x = _x;
		y = _y;
		width = w;
		height = h;
	}

	public Point getPos() {
		return (new Point(x, y));
	}

	public Rectangle getBoundingBox() {
        System.out.println("truc");
        return (new Rectangle(x, y, width, height));
	}

    @Override
    public void draw(Graphics g) {
        g.drawImage(image.getImage(), x, y, width, height, null);
    }
}

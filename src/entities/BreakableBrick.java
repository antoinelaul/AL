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

public class BreakableBrick extends AbstractBrick {
	protected static DrawableImage image = null;
	int x, y;
	int width;
    int height;
    
	public BreakableBrick(Canvas defaultCanvas, int x, int y) {
        super(defaultCanvas, x, y);
    }

    @Override
    public String getImage() {
        return "assets/images/redWall.png";
    }

    @Override
    public int getValue() {
        return 100;
    }
}
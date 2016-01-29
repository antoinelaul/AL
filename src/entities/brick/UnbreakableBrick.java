package entities.brick;

import gameframework.game.MoveBlocker;

import java.awt.Canvas;

public class UnbreakableBrick extends AbstractBrick implements MoveBlocker {

	public UnbreakableBrick(Canvas defaultCanvas, int x, int y, int width, int height) {
		super(defaultCanvas, x, y, width, height);
	}

	@Override
	public String getImage() {
		return "assets/images/wall.gif";
	}
}
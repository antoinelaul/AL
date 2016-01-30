package entities;

import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.game.GameEntity;

import java.awt.*;


public class Image implements GameEntity, Drawable {
    private DrawableImage image;
    private int x, y;
    private int width, height;

    public Image(Canvas canvas, String filename, int x, int y, int width, int height) {
        image = new DrawableImage(filename, canvas);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image.getImage(), x, y, width, height, null);
    }
}

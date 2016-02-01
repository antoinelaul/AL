package entities.brick;

import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.game.GameEntity;

import java.awt.*;
import java.util.ArrayList;


public abstract class AbstractBrick implements Drawable, GameEntity, Cloneable {
    private DrawableImage image;

    // Just an optimization to shared brick images according given paths.
    private static class Asset {
        public String path;
        public DrawableImage image;

        public Asset(String path, DrawableImage image) {
            this.path = path;
            this.image = image;
        }
    }

    private static class AssetCollection {
        private ArrayList<Asset> assets = new ArrayList<>();

        public int add(String filename, Canvas canvas) {
            Asset asset = null;
            for (Asset a: assets)
                if (a.path.equals(filename)) {
                    asset = a;
                    break;
                }

            if (asset == null) {
                assets.add(new Asset(filename, new DrawableImage(filename, canvas)));
                return assets.size() - 1;
            }
            else
                return assets.indexOf(asset);
        }

        public DrawableImage get(int index) {
            return assets.get(index).image;
        }
    }

    private static AssetCollection assets = new AssetCollection();

    Point pos;
    private int width;
    private int height;
    private int indexAsset = -1;


    public AbstractBrick(Canvas canvas, int x, int y, int width, int height, String filename) {
        this.indexAsset = assets.add(filename, canvas);

        this.pos = new Point(x, y);
        this.width = width;
        this.height = height;
    }

    // Here, images are not shared.
    public AbstractBrick(Canvas canvas, int x, int y, int width, int height) {
        this.image = new DrawableImage(getImage(), canvas);
        this.pos = new Point(x, y);
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(assets.get(indexAsset).getImage(), pos.x, pos.y, width, height, null);
        // g.drawImage(image.getImage(), pos.x, pos.y, width, height, null);
    }

    public Rectangle getBoundingBox() {
        return (new Rectangle(pos.x, pos.y, width, height));
    }

    public Point getPosition() {
        return pos;
    }

    public void setPosition(Point point) {
        this.pos = point;
    }

    @Override
    public AbstractBrick clone() {
        try {
            return (AbstractBrick) super.clone();
        }
        catch (CloneNotSupportedException e) { }

        return null;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public abstract String getImage();
}

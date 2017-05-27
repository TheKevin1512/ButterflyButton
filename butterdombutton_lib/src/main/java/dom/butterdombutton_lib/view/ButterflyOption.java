package dom.butterdombutton_lib.view;

import android.graphics.Bitmap;

/**
 * Created by kevindom on 27/05/17.
 */

public class ButterflyOption {
    private float x;
    private float y;
    private int position;
    private Bitmap icon;

    public ButterflyOption(int position, Bitmap icon) {
        this.position = position;
        this.icon = icon;
    }

    public float getOffsetX() {
        return x - icon.getWidth() / 2;
    }

    public float getOffsetY() {
        return y - icon.getHeight() / 2;
    }

    public float getPostsetX() {
        return x + icon.getWidth() / 2;
    }

    public float getPostsetY() {
        return y + icon.getHeight() / 2;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public int getPosition() {
        return position;
    }
}

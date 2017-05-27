package dom.butterdombutton_lib.model;

import android.graphics.Paint;

import java.util.List;

import dom.butterdombutton_lib.view.ButterflyOption;

/**
 * Created by kevindom on 27/05/17.
 */

public interface IButterdomModel {
    void update();
    void setOpening(boolean isOpening);
    boolean isOpened();
    boolean isOpening();
    void setBubbleColor(int color);
    int getBubbleColor();
    void setSelectedColor(int color);
    int getSelectedColor();
    Paint getBackgroundPaint();
    Paint getSelectedPaint();
    float getRadius();
    void setMaxRadius(float size);
    List<ButterflyOption> getOptions();
    void addButterflyOption(int resId, int position);
    ButterflyOption getSelectedOption();
    void setSelectedOption(ButterflyOption butterflyOption);
    float getMaxRadius();
    float getWidth();
    void setDimensions(float width, float height);
    float getHeight();
    boolean isCloseOnSelect();
    void setCloseOnSelect(boolean enabled);
}

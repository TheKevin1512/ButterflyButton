package dom.butterflybutton.view;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

import dom.butterflybutton.adapter.IButterflyModel;
import dom.butterflybutton.exception.InvalidOptionException;
import dom.butterflybutton.model.ButterflyOption;

import static dom.butterflybutton.view.ButterflyButton.EAST;
import static dom.butterflybutton.view.ButterflyButton.NORTH;
import static dom.butterflybutton.view.ButterflyButton.NORTH_EAST;
import static dom.butterflybutton.view.ButterflyButton.NORTH_WEST;
import static dom.butterflybutton.view.ButterflyButton.SOUTH;
import static dom.butterflybutton.view.ButterflyButton.SOUTH_EAST;
import static dom.butterflybutton.view.ButterflyButton.SOUTH_WEST;
import static dom.butterflybutton.view.ButterflyButton.WEST;

/**
 * Created by kevindom on 27/05/17.
 */

class ButterflyModel implements IButterflyModel {
    private static final float INITIAL_DELTA = 1.2f;
    private static final float INITIAL_RADIUS = 30f;
    private static final float INITIAL_MAX_RADIUS = 600f;

    private static final float ACCELERATION = 0.2f;
    private static final float DECELERATION = 0.1F;

    /**
     * Defines how fast to accelerate the opening of the bubble.
     */
    private static final float ACCELERATION_INTERVAL = 5f;
    /**
     * Defines how fast to decelerate the closing of the bubble.
     */
    private static final float DECELERATION_INTERVAL = 10f;

    private static final String TAG = "BubbleView";

    /**
     * The maximum radius for the bubble;
     */
    private float maxRadius;
    /**
     * The current radius of the bubble.
     */
    private float radius;
    /**
     * The current delta of the bubble.
     */
    private float delta;

    private boolean isOpening;
    private boolean isOpened;

    private float width;
    private float height;

    private Context context;
    private List<ButterflyOption> options;
    private ButterflyOption selectedOption;
    private Paint background, selected;
    private boolean isCloseOnSelect;

    ButterflyModel(Context context) {
        this.context = context;
        this.options = new ArrayList<>();
        this.maxRadius = INITIAL_MAX_RADIUS;
        this.radius = INITIAL_RADIUS;
        this.delta = INITIAL_DELTA;
        this.background = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.background.setColor(Color.parseColor("#cc0044"));
        this.selected = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.selected.setColor(Color.parseColor("#a30036"));
    }

    @Override
    public void update() {
        if (isOpening) {
            if (radius < maxRadius / 2) {
                if (radius % ACCELERATION_INTERVAL == 0)
                    delta += ACCELERATION;
                radius *= delta;
            }
            if (radius >= maxRadius / 2) {
                delta = INITIAL_DELTA;
                radius = maxRadius / 2;
                isOpened = true;
            }
        } else {
            if (radius > INITIAL_RADIUS) {
                if (radius % DECELERATION_INTERVAL == 0)
                    delta += DECELERATION;
                radius /= delta;
            } else if (radius <= INITIAL_RADIUS) {
                delta = INITIAL_DELTA;
                radius = INITIAL_RADIUS;
                isOpened = false;
            }
        }
    }

    @Override
    public void setDimensions(float width, float height) {
        this.width = width;
        this.height = height;
        for (ButterflyOption option : options) {
            switch (option.getPosition()) {
                case NORTH:
                    option.setX(width / 2);
                    option.setY(height / 5);
                    break;
                case NORTH_EAST:
                    option.setX(width / 1.4f);
                    option.setY(height / 3.5f);
                    break;
                case EAST:
                    option.setX(width / 1.25f);
                    option.setY(height / 2);
                    break;
                case SOUTH_EAST:
                    option.setX(width / 1.4f);
                    option.setY(height / 1.4f);
                    break;
                case SOUTH:
                    option.setX(width / 2);
                    option.setY(height / 1.25f);
                    break;
                case SOUTH_WEST:
                    option.setX(width / 3.5f);
                    option.setY(height / 1.4f);
                    break;
                case WEST:
                    option.setX(width / 5);
                    option.setY(height / 2);
                    break;
                case NORTH_WEST:
                    option.setX(width / 3.5f);
                    option.setY(height / 3.5f);
                    break;
            }
        }
    }

    @Override
    public float getWidth() {
        return this.width;
    }

    @Override
    public float getHeight() {
        return this.height;
    }

    @Override
    public void setOpening(boolean isOpening) {
        this.isOpening = isOpening;
    }

    @Override
    public boolean isOpened() {
        return this.isOpened;
    }

    @Override
    public boolean isOpening() {
        return this.isOpening;
    }

    @Override
    public float getRadius() {
        return this.radius;
    }

    @Override
    public void setMaxRadius(float size) {
        this.maxRadius = size;
    }

    @Override
    public List<ButterflyOption> getOptions() {
        return options;
    }

    @Override
    public void addButterflyOption(int resId, int position) {
        try {
            options.add(new ButterflyOption(position, BitmapFactory.decodeResource(context.getResources(), resId)));
        } catch (IllegalArgumentException e) {
            throw new InvalidOptionException("The resource id cannot be used as an icon to represent the option.");
        }
    }

    @Override
    public ButterflyOption getSelectedOption() {
        return this.selectedOption;
    }

    @Override
    public void setSelectedOption(ButterflyOption butterflyOption) {
        this.selectedOption = butterflyOption;
    }

    @Override
    public float getMaxRadius() {
        return maxRadius;
    }

    @Override
    public boolean isCloseOnSelect() {
        return this.isCloseOnSelect;
    }

    @Override
    public void setCloseOnSelect(boolean enabled) {
        this.isCloseOnSelect = enabled;
    }

    @Override
    public void setBubbleColor(int color) {
        this.background.setColor(color);
    }

    @Override
    public int getBubbleColor() {
        return this.background.getColor();
    }

    @Override
    public void setSelectedColor(int color) {
        this.selected.setColor(color);
    }

    @Override
    public int getSelectedColor() {
        return this.selected.getColor();
    }

    @Override
    public Paint getBackgroundPaint() {
        return this.background;
    }

    @Override
    public Paint getSelectedPaint() {
        return this.selected;
    }
}

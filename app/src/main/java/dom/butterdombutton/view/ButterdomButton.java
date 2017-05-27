package dom.butterdombutton.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import dom.butterdombutton.R;
import dom.butterdombutton.model.ButterdomModel;

/**
 * Created by kevindom on 27/05/17.
 */

public class ButterdomButton extends FrameLayout {
    private static final String TAG = "Butterrrr";

    /**
     * Breathing space to drag after pressing on an option.
     */
    private static final int CLICK_ACTION_THRESHHOLD = 60;

    public static final int NORTH = 0;
    public static final int NORTH_EAST = 1;
    public static final int EAST = 2;
    public static final int SOUTH_EAST = 3;
    public static final int SOUTH = 4;
    public static final int SOUTH_WEST = 5;
    public static final int WEST = 6;
    public static final int NORTH_WEST = 7;

    private DisplayMetrics screenMetrics;
    private Drawable openIcon;
    private ButterdomModel model;
    private FloatingActionButton floatingActionButton;
    private OnOptionSelectedListener listener;
    private boolean isOnClick;

    public ButterdomButton(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public ButterdomButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ButterdomButton(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {
        this.model = new ButterdomModel(context);
        View root = inflate(context, R.layout.domlayout, this);
        this.screenMetrics = context.getResources().getDisplayMetrics();
        this.floatingActionButton = (FloatingActionButton) root.findViewById(R.id.floatingActionButton);
        this.floatingActionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.isOpened()) {
                    Log.d(TAG, "onClick: JAAA");
                    closeBubble();
                } else {
                    Log.d(TAG, "onClick: NEE");
                    openBubble();
                }
            }
        });
        if (attributeSet != null)
            applyStyling(context.obtainStyledAttributes(attributeSet, R.styleable.ButterdomButton));
        setMinimumWidth((int) this.model.getMaxRadius());
        setMinimumHeight((int) this.model.getMaxRadius());
    }

    private void applyStyling(TypedArray arr){
        int size = arr.getInteger(R.styleable.ButterdomButton_size, 0);
        Drawable icon = arr.getDrawable(R.styleable.ButterdomButton_buttonIcon);
        int buttonColor = arr.getColor(R.styleable.ButterdomButton_buttonColor, 0);
        int bubbleColor = arr.getColor(R.styleable.ButterdomButton_bubbleColor, 0);
        int selectColor = arr.getColor(R.styleable.ButterdomButton_selectColor, 0);
        if (size != 0) model.setMaxRadius(size);
        if (icon != null) {
            floatingActionButton.setImageDrawable(icon);
            openIcon = icon;
        }
        if (buttonColor != 0) floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(buttonColor));
        if (bubbleColor != 0) model.setBubbleColor(bubbleColor);
        if (selectColor != 0) model.setSelectedColor(selectColor);

        setRelativeY(arr.getFloat(R.styleable.ButterdomButton_relativeY, 0.0f));
        setRelativeX(arr.getFloat(R.styleable.ButterdomButton_relativeX, 0.0f));
        arr.recycle();
    }

    public void setRelativeX(float percentage) {
        if (percentage > 1)
            percentage = percentage / 100;
        else if (percentage < 0)
            percentage = 0;
        setX(screenMetrics.widthPixels * percentage - model.getMaxRadius()/2);
    }

    public void setRelativeY(float percentage) {
        if (percentage > 1)
            percentage = percentage / 100;
        else if (percentage < 0)
            percentage = 0;
        setY(screenMetrics.heightPixels * percentage - model.getMaxRadius()/2);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        model.setDimensions(w, h);
    }

    /**
     * Opens the filter.
     */
    public void openBubble() {
        this.model.setOpening(true);
        invalidate();
    }

    /**
     * Closes the filter.
     */
    public void closeBubble() {
        this.model.setOpening(false);
        invalidate();
    }

    public void setOpenIcon(Drawable drawable) {
        this.openIcon = drawable;
    }

    public void addOption(int resId, int position) {
        model.addButterflyOption(resId, position);
    }

    /**
     * Draws all the options to the screen which are not null.
     *
     * @param canvas
     */
    private void drawOptions(Canvas canvas) {
        ButterflyOption selectedOption = model.getSelectedOption();
        if (selectedOption != null)
            canvas.drawCircle(selectedOption.getX(), selectedOption.getY(), selectedOption.getIcon().getWidth(), model.getSelectedPaint());
        for (ButterflyOption option : model.getOptions()) {
            canvas.drawBitmap(option.getIcon(), option.getOffsetX(), option.getOffsetY(), null);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        model.update();
        canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, model.getRadius(), model.getBackgroundPaint());
        if (model.isOpening()) {
            if (model.isOpened()) {
                floatingActionButton.setImageResource(R.drawable.ic_clear);
                drawOptions(canvas);
            } else {
                invalidate();
            }
        } else {
            if (!model.isOpened()) {
                floatingActionButton.setImageDrawable(openIcon);
            } else {
                invalidate();
            }
        }
        super.dispatchDraw(canvas);
    }

    /**
     * To click an option.
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        ButterflyOption selectedOption = model.getSelectedOption();

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                for (ButterflyOption option : model.getOptions()) {
                    if (option != null) {
                        if (event.getX() >= option.getOffsetX() && event.getX() <= option.getPostsetX()
                                && event.getY() >= option.getOffsetY() && event.getY() <= option.getPostsetY()) {
                            isOnClick = true;
                            model.setSelectedOption(option);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (isOnClick && selectedOption != null) {
                    isOnClick = false;
                    if (listener != null) listener.onOptionSelected(selectedOption.getPosition(), selectedOption.getIcon());
                    if (model.isCloseOnSelect()) closeBubble();
                    else invalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isOnClick && (Math.abs(selectedOption.getX() - event.getX()) > CLICK_ACTION_THRESHHOLD)
                        || Math.abs(selectedOption.getY() - event.getY()) > CLICK_ACTION_THRESHHOLD) {
                    isOnClick = false;
                    model.setSelectedOption(null);
                }
                break;
        }
        return true;
    }

    public interface OnOptionSelectedListener {
        void onOptionSelected(int position, Bitmap icon);
    }
}

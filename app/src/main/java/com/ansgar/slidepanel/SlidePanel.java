package com.ansgar.slidepanel;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;

/**
 * Created by kirill on 12.7.17.
 */

public class SlidePanel extends FrameLayout implements View.OnTouchListener {

    public enum Position {
        LEFT,
        RIGHT,
        TOP,
        BOTTOM
    }

    public static final String RIGHT_POSITION = "right";

    private int mWidth;
    private int mLeftPanelWidth;
    private int mRightPanelWidth;

    private int mMenu;

    private int mXDelta;
    private int mMarginLeft;
    private int mMargin;

    private static final String TAG = SlidePanel.class.getSimpleName();

    public SlidePanel(Context context) {
        super(context);
        setOnTouchListener(this);
    }

    public SlidePanel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SlidePanel);

        mLeftPanelWidth = typedArray.getDimensionPixelSize(R.styleable.SlidePanel_left_panel, 50);
        mRightPanelWidth = typedArray.getDimensionPixelSize(R.styleable.SlidePanel_right_panel, 100);
        mWidth = mLeftPanelWidth + mRightPanelWidth;

        mMenu = typedArray.getResourceId(R.styleable.SlidePanel_menu, -1);
        typedArray.recycle();

        //TODO Parse xml menu resource to display menu items in panel
        PopupMenu popupMenu = new PopupMenu(getContext(), null);
        Menu menu = popupMenu.getMenu();
        ((Activity) getContext()).getMenuInflater().inflate(mMenu, menu);
        Log.i(TAG, "Menu: " + menu.getItem(0).getItemId());
        Log.i(TAG, "Menu: " + menu.getItem(0).getTitle());
        Log.i(TAG, "Menu: " + menu.getItem(0).getIcon());

    }

    public SlidePanel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int x = (int) event.getRawX();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
                mXDelta = x - params.leftMargin;
                break;
            case MotionEvent.ACTION_MOVE:

                if (mMargin >= mMarginLeft) {
                    RelativeLayout.LayoutParams moveParams = (RelativeLayout.LayoutParams) getLayoutParams();
                    mMargin = x - mXDelta;
                    moveParams.leftMargin = mMargin;
                    setLayoutParams(moveParams);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mMargin <= (getScreenWidth() - mWidth / 2)) {
                    mMargin = mMarginLeft;
                } else {
                    mMargin = getScreenWidth() - mLeftPanelWidth;
                }

                RelativeLayout.LayoutParams upParams = (RelativeLayout.LayoutParams) getLayoutParams();
                upParams.leftMargin = mMargin;

                setLayoutParams(upParams);
                break;
        }
        return true;
    }

    public void setRightPos(Position position) {

        int width = 0;
        int height = 0;
        switch (position) {
            case LEFT:
                width = 1;
                height = ViewGroup.LayoutParams.MATCH_PARENT;
                break;
            case RIGHT:
                width = mWidth + 100;
                height = ViewGroup.LayoutParams.MATCH_PARENT;
                break;
            case TOP:
                width = 1;
                height = ViewGroup.LayoutParams.MATCH_PARENT;
                break;
            case BOTTOM:
                width = 1;
                height = ViewGroup.LayoutParams.MATCH_PARENT;
                break;
        }
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        params.leftMargin = getScreenWidth() - mWidth;
        mMarginLeft = params.leftMargin;
        mMargin = mMarginLeft;
        setLayoutParams(params);
    }

    private int getScreenWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    private int getScreenHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public void setPanelsWidth(int leftWidth, int rightWidth) {
        mLeftPanelWidth = leftWidth;
        mRightPanelWidth = rightWidth;
        mWidth = leftWidth + rightWidth;
    }

}

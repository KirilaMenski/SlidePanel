package com.ansgar.navigationpanel;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

/**
 * Created by kirill on 12.7.17.
 */

public class SlidePanel extends FrameLayout {

    private MenuInflater mMenuInflater;

    private int mIndex;
    private TypedArray mTypedArray;

    public SlidePanel(Context context) {
        super(context);
    }

    public SlidePanel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.SlidePanel);

//        int textColor = typedArray.getColor(R.styleable.SlidePanel_text_color, Color.BLACK);
//        int textTint = typedArray.getColor(R.styleable.SlidePanel_text_tint, Color.BLACK);
//        int tintBackground = typedArray.getColor(R.styleable.SlidePanel_tint_item_background, Color.TRANSPARENT);
//        int divider = typedArray.getResourceId(R.styleable.SlidePanel_divider, -1);
//        int menu = typedArray.getResourceId(R.styleable.SlidePanel_menu, -1);
//        int drawablePadding = typedArray.getDimensionPixelSize(R.styleable.SlidePanel_drawable_padding, 0);
//
//        int textSize = typedArray.getDimensionPixelSize(R.styleable.SlidePanel_text_size, 16);
//        String textStyle = typedArray.getString(R.styleable.SlidePanel_text_style);
//
//        mIndex = getIndex();
//        mMenuInflater = new MenuInflater(context, this, menu, textColor, textTint, tintBackground, divider,
//                textSize, textStyle, drawablePadding, mIndex);
//        typedArray.recycle();

    }

    public SlidePanel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void updateView() {
        int textColor = mTypedArray.getColor(R.styleable.SlidePanel_text_color, Color.BLACK);
        int textTint = mTypedArray.getColor(R.styleable.SlidePanel_text_tint, Color.BLACK);
        int tintBackground = mTypedArray.getColor(R.styleable.SlidePanel_tint_item_background, Color.TRANSPARENT);
        int divider = mTypedArray.getResourceId(R.styleable.SlidePanel_divider, -1);
        int menu = mTypedArray.getResourceId(R.styleable.SlidePanel_menu, -1);
        int drawablePadding = mTypedArray.getDimensionPixelSize(R.styleable.SlidePanel_drawable_padding, 0);

        int textSize = mTypedArray.getDimensionPixelSize(R.styleable.SlidePanel_text_size, 16);
        String textStyle = mTypedArray.getString(R.styleable.SlidePanel_text_style);

        mMenuInflater = new MenuInflater(getContext(), this, menu, textColor, textTint, tintBackground, divider,
                textSize, textStyle, drawablePadding, mIndex);
        mTypedArray.recycle();
    }

    public void setIndex(int index) {
        mIndex = index;
    }

    public int getIndex() {
        return mIndex;
    }

    public MenuInflater getMenuInflater() {
        return mMenuInflater;
    }

}

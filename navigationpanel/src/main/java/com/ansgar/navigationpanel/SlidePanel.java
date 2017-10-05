package com.ansgar.navigationpanel;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by kirill on 12.7.17.
 */

public class SlidePanel extends FrameLayout {

    private MenuInflater mMenuInflater;

    public SlidePanel(Context context) {
        super(context);
    }

    public SlidePanel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SlidePanel);

        int textColor = typedArray.getColor(R.styleable.SlidePanel_text_color, Color.BLACK);
        int textTint = typedArray.getColor(R.styleable.SlidePanel_text_tint, Color.BLACK);
        int tintBackground = typedArray.getColor(R.styleable.SlidePanel_tint_item_background, Color.TRANSPARENT);

        int divider = typedArray.getResourceId(R.styleable.SlidePanel_divider, -1);
        int menu = typedArray.getResourceId(R.styleable.SlidePanel_menu, -1);
        mMenuInflater = new MenuInflater(context, this, menu, textColor, textTint, tintBackground, divider);
        typedArray.recycle();

    }

    public SlidePanel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MenuInflater getMenuInflater() {
        return mMenuInflater;
    }

}

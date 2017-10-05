package com.ansgar.navigationpanel;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill on 31.7.17.
 */

public class MenuInflater {

    private OnClickMenuItemListener mListener;

    private FrameLayout mParentFrame;
    private Menu mMenu;
    private Context mContext;

    private LinearLayout mPreviousLinear;
    private TextView mPreviousText;
    private Drawable mPreviousDrawable;

    public MenuInflater(Context context, FrameLayout parentView, int menuResource, int textColor,
                        int textTint, int tintBackground, int dividerId, int textSize, String textStyle,
                        int drawablePadding, int index) {
        mContext = context;
        PopupMenu popupMenu = new PopupMenu(context, null);
        mMenu = popupMenu.getMenu();
        android.view.MenuInflater inflater = ((Activity) context).getMenuInflater();
        inflater.inflate(menuResource, mMenu);

        mParentFrame = parentView;
        ViewGroup dividerLayout = (ViewGroup) LayoutInflater.from(context).inflate(dividerId, null);

        Typeface style = getStyle(context, textStyle);
        generateMenuItems(textTint, textColor, textSize, tintBackground, style, drawablePadding, dividerLayout, index);
    }

    private void generateMenuItems(final int textTint, final int textColor, int textSize,
                                   int tintBackground, Typeface textStyle, int drawablePadding,
                                   ViewGroup divider, int index) {

        LinearLayout parentLinear = new LinearLayout(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        parentLinear.setLayoutParams(params);
        parentLinear.setOrientation(LinearLayout.VERTICAL);

        Log.i("MenuInflanter", "Size: " + mMenu.size());
        for (int i = 0; i < mMenu.size(); i++) {
            MenuItem menuItem = new MenuItem(mMenu.getItem(i).getItemId(),
                    mMenu.getItem(i).getTitle().toString(),
                    mMenu.getItem(i).getIcon(),
                    textTint,
                    tintBackground);

            boolean changeItem = false;

            final LinearLayout linearLayout = new LinearLayout(mContext);
            LinearLayout.LayoutParams childParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 1f);

            final TextView text = new TextView(mContext);

            Log.i("MenuInflanter", "Index: " + index);
            if (index == mMenu.getItem(i).getItemId()) {
                changeItem = mMenu.getItem(i).getItemId() == index;
                linearLayout.setBackgroundColor(menuItem.getTintBackground());
                mPreviousLinear = linearLayout;
                mPreviousText = text;
                mPreviousDrawable = mMenu.getItem(i).getIcon();
            } else if (index == i) {
                changeItem = true;
                linearLayout.setBackgroundColor(menuItem.getTintBackground());
                mPreviousLinear = linearLayout;
                mPreviousText = text;
                mPreviousDrawable = mMenu.getItem(i).getIcon();
            }

            text.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            text.setText(menuItem.getTitle());
            text.setGravity(Gravity.CENTER);
            text.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            if (textStyle != null) text.setTypeface(textStyle);
            text.setTextColor(changeItem ? textTint : textColor);
            Drawable icon = menuItem.getIcon();
            icon.setColorFilter(changeItem ? textTint : Color.TRANSPARENT, PorterDuff.Mode.SRC_ATOP);
            text.setCompoundDrawablesWithIntrinsicBounds(null, icon, null, null);
            text.setCompoundDrawablePadding(drawablePadding);

            linearLayout.setLayoutParams(childParams);
            linearLayout.addView(text);
            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.setOnClickListener(new ClickListener(menuItem) {
                @Override
                protected void onMenuItemListener(MenuItem menuItem) {
                    if (mPreviousLinear != null) {
                        if (mPreviousLinear == linearLayout) {
                            return;
                        } else {
                            mPreviousLinear.setBackgroundColor(Color.TRANSPARENT);
                            mPreviousDrawable.setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.SRC_ATOP);
                            mPreviousText.setCompoundDrawablesWithIntrinsicBounds(null, mPreviousDrawable, null, null);
                            mPreviousText.setTextColor(textColor);
                        }
                    }
                    linearLayout.setBackgroundColor(menuItem.getTintBackground());
                    text.setTextColor(textTint);
                    menuItem.getIcon().setColorFilter(textTint, PorterDuff.Mode.SRC_ATOP);
                    text.setCompoundDrawablesWithIntrinsicBounds(null, menuItem.getIcon(), null, null);

                    mPreviousLinear = linearLayout;
                    mPreviousText = text;
                    mPreviousDrawable = menuItem.getIcon();
                    if (mListener != null) mListener.onItemClicked(menuItem);
                }
            });

            parentLinear.addView(linearLayout);
            if (i != mMenu.size() - 1) {
                LinearLayout dividerGroup = new LinearLayout(mContext);
                LinearLayout.LayoutParams dividerParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dividerGroup.setLayoutParams(dividerParams);
                dividerGroup.setOrientation(LinearLayout.VERTICAL);
                for (int j = 0; j < divider.getChildCount(); j++) {
                    if (divider.getChildAt(j) != null) {
                        View child = divider.getChildAt(j);

                        View dividerChild = new View(mContext);
                        dividerChild.setLayoutParams(child.getLayoutParams());
                        dividerChild.setBackground(child.getBackground());

                        dividerGroup.addView(dividerChild);
                    }
                }

                parentLinear.addView(dividerGroup);
            }
        }

        mParentFrame.addView(parentLinear);

    }

    private static Typeface getStyle(Context context, String font) {
        if (font == null) return null;
        Typeface type = Typeface.createFromAsset(context.getAssets(), font);
        return type;
    }

    public void setListener(OnClickMenuItemListener listener) {
        mListener = listener;
    }

    public interface OnClickMenuItemListener {
        void onItemClicked(MenuItem menuItem);
    }

}

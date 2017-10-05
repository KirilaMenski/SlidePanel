package com.ansgar.navigationpanel;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;
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
                        int textTint, int tintBackground, int dividerId, int textSize, String textStyle) {
        mContext = context;
        PopupMenu popupMenu = new PopupMenu(context, null);
        mMenu = popupMenu.getMenu();
        android.view.MenuInflater inflater = ((Activity) context).getMenuInflater();
        inflater.inflate(menuResource, mMenu);

        mParentFrame = parentView;
        ViewGroup dividerLayout = (ViewGroup) LayoutInflater.from(context).inflate(dividerId, null);

        Typeface style = getStyle(context, textStyle);
        generateMenuItems(textTint, textColor, textSize, tintBackground, style, dividerLayout);
    }

//    private List<View> getDividerViews(ViewGroup layout) {
//        List<View> dividers = new ArrayList<>();
//        if (layout == null) return dividers;
//        for (int i = 0; i < layout.getChildCount(); i++) {
//            View divider = layout.getChildAt(i);
//            Log.i("!!!!!!", "Divider: " + divider.getId() + ", " + divider.getHeight());
//            if (divider instanceof View) dividers.add(divider);
//        }
//        return dividers;
//    }

    private void generateMenuItems(final int textTint, final int textColor, int textSize,
                                   int tintBackground, Typeface textStyle, ViewGroup divider) {

        LinearLayout parentLinear = new LinearLayout(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        parentLinear.setLayoutParams(params);
        parentLinear.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < mMenu.size(); i++) {
            MenuItem menuItem = new MenuItem(mMenu.getItem(i).getItemId(),
                    mMenu.getItem(i).getTitle().toString(),
                    mMenu.getItem(i).getIcon(),
                    textTint,
                    tintBackground);

            final LinearLayout linearLayout = new LinearLayout(mContext);
            LinearLayout.LayoutParams childParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 1f);

            final TextView text = new TextView(mContext);
            text.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            text.setText(menuItem.getTitle());
            text.setGravity(Gravity.CENTER);
            text.setTextSize(textSize);
            if (textStyle != null) text.setTypeface(textStyle);
            text.setTextColor(textColor);
            text.setCompoundDrawablesWithIntrinsicBounds(null, menuItem.getIcon(), null, null);

            linearLayout.setLayoutParams(childParams);
            linearLayout.addView(text);
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

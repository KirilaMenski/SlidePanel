package com.ansgar.navigationpanel;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

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
                        int textTint, int tintBackground) {
        mContext = context;
        PopupMenu popupMenu = new PopupMenu(context, null);
        mMenu = popupMenu.getMenu();
        ((Activity) context).getMenuInflater().inflate(menuResource, mMenu);

        mParentFrame = parentView;
        generateMenuItems(textTint, textColor, tintBackground);
    }

    private void generateMenuItems(final int textTint, final int textColor, int tintBackground) {

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
        }

        mParentFrame.addView(parentLinear);

    }

    public void setListener(OnClickMenuItemListener listener) {
        mListener = listener;
    }

    public interface OnClickMenuItemListener {
        void onItemClicked(MenuItem menuItem);
    }

}

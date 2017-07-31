package com.ansgar.slidepanel;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill on 31.7.17.
 */

public class MenuInflater {

    private List<MenuItem> mMenuItems = new ArrayList<>();

    private FrameLayout mParentFrame;
    private LinearLayout mParentLinear;
    private PopupMenu mPopupMenu;
    private Menu mMenu;
    private Context mContext;

    private int mTintItem;
    private int mTintBackground;
    private int mWidth;

    public MenuInflater(Context context, FrameLayout parentView, int menuResource, int tintItem, int tintBackground, int width) {
        mContext = context;
        mWidth = width;
        mPopupMenu = new PopupMenu(context, null);
        mMenu = mPopupMenu.getMenu();
        ((Activity) context).getMenuInflater().inflate(menuResource, mMenu);

        mTintItem = tintItem;
        mTintBackground = tintBackground;

        mParentFrame = parentView;
        generateMenuItems();
    }

    private void generateMenuItems() {

        mParentLinear = new LinearLayout(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        mParentLinear.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < mMenu.size(); i++) {
            MenuItem menuItem = new MenuItem(mMenu.getItem(i).getItemId(),
                    mMenu.getItem(i).getTitle().toString(),
                    mMenu.getItem(i).getIcon(),
                    mTintItem,
                    mTintBackground);
            mMenuItems.add(menuItem);

            LinearLayout linearLayout = new LinearLayout(mContext);
            LinearLayout.LayoutParams childParams = new LinearLayout.LayoutParams(mWidth, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);

            TextView text = new TextView(mContext);
            text.setLayoutParams(new LinearLayout.LayoutParams(mWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
            text.setText(mMenuItems.get(i).getTitle());

            linearLayout.setLayoutParams(childParams);
            linearLayout.addView(text);

            mParentLinear.addView(linearLayout);
            Log.i("SlidePanel", "Child " + mMenu.getItem(i).getItemId() + ", " + mMenu.getItem(i).getTitle());
        }

        mParentFrame.addView(mParentLinear);

    }

    public List<MenuItem> getMenuItems() {
        return mMenuItems;
    }

}

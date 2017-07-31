package com.ansgar.slidepanel;

import android.graphics.drawable.Drawable;

/**
 * Created by kirill on 31.7.17.
 */

public class MenuItem {

    private int mId;
    private String mTitle;
    private Drawable mIcon;
    private int mTintItem;
    private int mTintBackground;

    public MenuItem() {

    }

    public MenuItem(int id, String title, Drawable icon, int tintItem, int tintBackground) {
        mId = id;
        mTitle = title;
        mIcon = icon;
        mTintItem = tintItem;
        mTintBackground = tintBackground;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Drawable getIcon() {
        return mIcon;
    }

    public void setIcon(Drawable icon) {
        mIcon = icon;
    }

    public int getTintItem() {
        return mTintItem;
    }

    public void setTintItem(int tintItem) {
        mTintItem = tintItem;
    }

    public int getTintBackground() {
        return mTintBackground;
    }

    public void setTintBackground(int tintBackground) {
        mTintBackground = tintBackground;
    }
}

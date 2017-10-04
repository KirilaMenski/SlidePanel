package com.ansgar.navigationpanel;

import android.view.View;

/**
 * Created by kirill on 4.10.17.
 */

public abstract class ClickListener implements View.OnClickListener {

    private MenuItem mMenuItem;

    protected ClickListener(MenuItem menuItem) {
        mMenuItem = menuItem;
    }

    @Override
    public void onClick(View view) {
        onMenuItemListener(mMenuItem);
    }

    protected abstract void onMenuItemListener(MenuItem menuItem);

}

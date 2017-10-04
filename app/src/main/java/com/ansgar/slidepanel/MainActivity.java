package com.ansgar.slidepanel;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private boolean mIsVisRightNavigation = true;
    LinearLayout linear;
    LinearLayout mRightNavigationMenu;
    ImageView mRightNavigationArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SlidePanel panel = (SlidePanel) findViewById(R.id.slide);
//        panel.setPanelsWidth(50, 150);
//        panel.setRightPos(SlidePanel.Position.RIGHT);
        panel.getMenuInflater().setListener(new MenuInflater.OnClickMenuItemListener() {
            @Override
            public void onItemClicked(MenuItem menuItem) {
                Log.i("!!!!", "Title: " + menuItem.getTitle());
            }
        });

        mRightNavigationMenu = (LinearLayout) findViewById(R.id.right_navigation_menu);
        mRightNavigationArrow = (ImageView) findViewById(R.id.arrow_ic);

        linear = (LinearLayout) findViewById(R.id.arrow_ll);
        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performAnimation(mRightNavigationMenu, "x", 100,
                        mRightNavigationMenu.getLeft(),
                        getViewMeasuredWidth(linear));
                performAnimation(mRightNavigationArrow, "rotation", 100, 0f, 180f);

                mIsVisRightNavigation = !mIsVisRightNavigation;
            }
        });

    }

    private void performAnimation(View view, String propertyName, int duration, float start, float end) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(
                view,
                propertyName,
                mIsVisRightNavigation ? start : end,
                mIsVisRightNavigation ? end : start
        );
        animator.setDuration(duration);
        animator.start();
    }

    public int getViewMeasuredWidth(View view) {
        view.measure(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        return getScreenWidth() - view.getMeasuredWidth();
    }

    public int getScreenWidth() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.widthPixels;
    }

}

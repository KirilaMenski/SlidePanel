package com.ansgar.slidepanel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SlidePanel panel = (SlidePanel) findViewById(R.id.slide);
        panel.setPanelsWidth(50, 150);
        panel.setRightPos(SlidePanel.Position.RIGHT);
    }
}

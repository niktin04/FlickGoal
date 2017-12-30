package com.niktin.flickgoal;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

    protected GamePanel gamePanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Make the activity fullscreen.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Get display dimensions in pixels.
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        //Saving constants for further use.
        Constants.CURRENT_CONTEXT = this;
        Constants.SCREEN_WIDTH = dm.widthPixels;
        Constants.SCREEN_HEIGHT = dm.heightPixels;
        Constants.MAX_FLING_VELOCITY = ViewConfiguration.get(this).getScaledMaximumFlingVelocity();

        //Allowing music control from volume buttons.
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        //Setting GamePanel class as my view.
        gamePanel = new GamePanel(this);
        setContentView(gamePanel);
    }
}

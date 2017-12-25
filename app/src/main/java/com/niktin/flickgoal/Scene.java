package com.niktin.flickgoal;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by NikTin on 26/12/17 at 00:44.
 */

public interface Scene {
    public void receiveTouch(MotionEvent event);
    public void update();
    public void draw(Canvas canvas);
    public void terminate();
}

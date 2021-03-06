package com.niktin.flickgoal;

import android.graphics.Canvas;
import android.media.SoundPool;
import android.view.MotionEvent;

/**
 * Created by NikTin on 26/12/17 at 00:44.
 */

public interface Scene {
    public void receiveSoundPool(SoundPool soundPool);
    public void receiveTouch(MotionEvent event);
    public void receiveFling(MotionEvent event1, MotionEvent event2,
                             float velocityX, float velocityY);
    public void update();
    public void draw(Canvas canvas);
    public void terminate();
}

package com.niktin.flickgoal;

import android.media.SoundPool;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by NikTin on 24/12/17 at 01:51.
 */

class GestureListener extends GestureDetector.SimpleOnGestureListener {

    private BallObject ballObject;
    private float maxFlingVelocity = ViewConfiguration.get(Constants.CURRENT_CONTEXT).getScaledMaximumFlingVelocity();

    GestureListener(BallObject ballObject) {
        this.ballObject = ballObject;
    }

    @Override
    public boolean onDown(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onFling(MotionEvent eventDown, MotionEvent eventUp,
                           float velocityX, float velocityY) {
        float normalisedVelocityX = velocityX / maxFlingVelocity * Constants.BALL_NORMALISED_SPEED;
        float normalisedVelocityY = velocityY / maxFlingVelocity * Constants.BALL_NORMALISED_SPEED;


        ballObject.setBallSpeed(normalisedVelocityX, normalisedVelocityY);
        return true;
    }
}

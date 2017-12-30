package com.niktin.flickgoal;

import android.graphics.RectF;
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
    private SwipeTrailObject swipeTrailObject;

    GestureListener(BallObject ballObject, SwipeTrailObject swipeTrailObject) {
        this.ballObject = ballObject;
        this.swipeTrailObject = swipeTrailObject;
    }

    @Override
    public boolean onDown(MotionEvent event) {
        return true;
    }
    @Override
    public boolean onFling(MotionEvent eventDown, MotionEvent eventUp,
                           float velocityX, float velocityY) {

        float normalisedVelocityX = velocityX / maxFlingVelocity * Constants.NORMALISED_VELOCITY;
        float normalisedVelocityY = velocityY / maxFlingVelocity * Constants.NORMALISED_VELOCITY;

        RectF ballRectangle = new RectF(
                ballObject.getCenterX() - ballObject.getBallRadius(),
                ballObject.getCenterY() - ballObject.getBallRadius(),
                ballObject.getCenterX() + ballObject.getBallRadius(),
                ballObject.getCenterY() + ballObject.getBallRadius());

        ballObject.setBallSpeed(normalisedVelocityX, normalisedVelocityY);
        swipeTrailObject.clearTrailPoints();
        return true;
    }
}

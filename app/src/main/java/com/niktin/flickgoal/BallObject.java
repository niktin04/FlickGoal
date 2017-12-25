package com.niktin.flickgoal;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by NikTin on 23/12/17 at 14:12.
 */

public class BallObject implements GameObject {

    private int colour;
    private float centerX, centerY;
    private float speedX, speedY;
    private float ballRadius = Constants.SCREEN_WIDTH / 20;

    BallObject(int colour, Point startPoint) {
        this.colour = colour;
        this.centerX = startPoint.x;
        this.centerY = startPoint.y;
    }

    @Override
    public void update() {
        // Updating speed based on friction.
        if ((centerX + speedX - ballRadius) < 0 || (centerX + speedX + ballRadius) > Constants.SCREEN_WIDTH) {
            speedX *= -1 * Constants.SURFACE_FRICTION_COEFFICIENT;
        } else {
            speedX *= Constants.SURFACE_FRICTION_COEFFICIENT;
        }

        if ((centerY + speedY - ballRadius) < 0 || (centerY + speedY + ballRadius) > Constants.SCREEN_HEIGHT) {
            speedY *= -1 * Constants.SURFACE_FRICTION_COEFFICIENT;
        } else {
            speedY *= Constants.SURFACE_FRICTION_COEFFICIENT;
        }

        // To make sure that further computation is stopped if speed falls below 1 pixel.
        if (speedX < 1 && speedX > -1) {
            speedX = 0;
        }
        if (speedY < 1 && speedY > -1) {
            speedY = 0;
        }

        // Updating ball position.
        centerX += speedX;
        centerY += speedY;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(colour);
        canvas.drawCircle(centerX, centerY, ballRadius, paint);
    }

    public void setBallSpeed(float speedX, float speedY) {
        this.speedX = speedX;
        this.speedY = speedY;
    }
}

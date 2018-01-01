package com.niktin.flickgoal;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;

/**
 * Created by NikTin on 31/12/17 at 00:02.
 */

public class BallObject implements GameObject {

    private int radius = Constants.SCREEN_WIDTH / 20;
    private Point centerPoint;
    private Paint ballPaint, highlightPaint, shadowPaint, boundaryPaint;
    private float speedX = 0, speedY = 0;

    BallObject(int centerPointX, int centerPointY) {
        centerPoint = new Point(centerPointX, centerPointY);

        ballPaint = new Paint();
        ballPaint.setAntiAlias(true);
//        ballPaint.setColor(Color.rgb(237, 94, 94));
        ballPaint.setColor(NikTinHelperFunctions.getRandomColor());
        ballPaint.setShadowLayer(2, 0, 0, Color.BLACK);

        highlightPaint = new Paint();
        highlightPaint.setAntiAlias(true);
        highlightPaint.setColor(Color.WHITE);
        highlightPaint.setAlpha(28);

        shadowPaint = new Paint();
        shadowPaint.setAntiAlias(true);
        shadowPaint.setColor(Color.BLACK);
        shadowPaint.setAlpha(16);
        shadowPaint.setStrokeCap(Paint.Cap.ROUND);
        shadowPaint.setStyle(Paint.Style.STROKE);
        shadowPaint.setStrokeWidth(7);

        boundaryPaint = new Paint();
        boundaryPaint.setAntiAlias(true);
        boundaryPaint.setColor(Color.BLACK);
        boundaryPaint.setStyle(Paint.Style.STROKE);
        boundaryPaint.setStrokeWidth(6);
    }

    @Override
    public void update() {
        if (centerPoint.x + radius + speedX > Constants.SCREEN_WIDTH || centerPoint.x - radius + speedX < 0) {
            speedX *= -1;
        }
        if (centerPoint.y + radius + speedY > Constants.SCREEN_HEIGHT || centerPoint.y - radius + speedY < 0) {
            speedY *= -1;
        }

        speedX *= Constants.SURFACE_FRICTION_COEFFICIENT;
        speedY *= Constants.SURFACE_FRICTION_COEFFICIENT;

        if (speedX < 1 && speedX > -1) {
            speedX = 0;
        }
        if (speedY < 1 && speedY > -1) {
            speedY = 0;
        }

        centerPoint.x += (int) speedX;
        centerPoint.y += (int) speedY;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(centerPoint.x, centerPoint.y, radius, ballPaint);
        canvas.drawCircle(centerPoint.x - radius / 3, centerPoint.y - radius / 3, radius / 3, highlightPaint);
        canvas.drawCircle(centerPoint.x, centerPoint.y, radius, boundaryPaint);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawArc(centerPoint.x - radius + 16, centerPoint.y - radius + 16, centerPoint.x + radius - 16, centerPoint.y + radius - 16, 0, 90, false, shadowPaint);
        }
    }

    public float getSpeedX() {
        return speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    void setSpeed(float speedX, float speedY) {
        this.speedX = speedX;
        this.speedY = speedY;
    }

    Point getPositionPoint() {
        return centerPoint;
    }
}

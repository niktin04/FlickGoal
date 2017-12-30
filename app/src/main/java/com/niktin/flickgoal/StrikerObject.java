package com.niktin.flickgoal;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;

/**
 * Created by NikTin on 31/12/17 at 00:02.
 */

public class StrikerObject implements GameObject {

    private int radius = Constants.SCREEN_WIDTH / 20;
    private Point centerPoint;
    private Paint ballPaint, highlightPaint, shadowPaint, boundaryPaint;
    private int speedX = 0, speedY = 0;

    StrikerObject(int centerPointX, int centerPointY) {
        centerPoint = new Point(centerPointX, centerPointY);

        ballPaint = new Paint();
        ballPaint.setAntiAlias(true);
        ballPaint.setColor(Color.rgb(237, 94, 94));
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

        System.out.println("Ball Speed: " + speedX + " " + speedY);
        centerPoint.x += Math.floor(speedX);
        centerPoint.y += Math.floor(speedY);
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

    void setSpeed(int speedX, int speedY) {
        this.speedX = speedX;
        this.speedY = speedY;
    }

    Point getPositionPoint() {
        return centerPoint;
    }
}
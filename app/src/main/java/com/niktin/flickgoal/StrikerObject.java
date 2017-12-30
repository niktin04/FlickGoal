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

    private int radius = Constants.SCREEN_WIDTH / 18;
    private Point centerPoint;
    private Paint ballPaint, highlightPaint, shadowPaint, boundaryPaint;

    StrikerObject(int centerPointX, int centerPointY) {
        centerPoint = new Point(centerPointX, centerPointY);

        ballPaint = new Paint();
        ballPaint.setColor(Color.rgb(237, 94, 94));
        ballPaint.setShadowLayer(2, 0, 0, Color.BLACK);

        highlightPaint = new Paint();
        highlightPaint.setColor(Color.WHITE);
        highlightPaint.setAlpha(40);

        shadowPaint = new Paint();
        shadowPaint.setColor(Color.BLACK);
        shadowPaint.setAlpha(16);
        shadowPaint.setStrokeCap(Paint.Cap.ROUND);
        shadowPaint.setStyle(Paint.Style.STROKE);
        shadowPaint.setStrokeWidth(7);

        boundaryPaint = new Paint();
        boundaryPaint.setColor(Color.BLACK);
        boundaryPaint.setAlpha(28);
        boundaryPaint.setStyle(Paint.Style.STROKE);
        boundaryPaint.setStrokeWidth(4);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(centerPoint.x, centerPoint.y, radius, ballPaint);
        canvas.drawCircle(centerPoint.x - radius / 3, centerPoint.y - radius / 3, radius / 3, highlightPaint);
        canvas.drawCircle(centerPoint.x, centerPoint.y, radius - 3, boundaryPaint);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawArc(centerPoint.x - radius + 16, centerPoint.y - radius + 16, centerPoint.x + radius - 16, centerPoint.y + radius - 16, 0, 90, false, shadowPaint);
        }
    }
}

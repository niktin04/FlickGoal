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
    private Point point;

    BallObject(int colour, Point point) {
        this.colour = colour;
        this.point = point;
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(colour);

        float circleRadius = 40;

        canvas.drawCircle(point.x, point.y, circleRadius, paint);
    }

    void update(Point pointGiven) {
        point = pointGiven;
    }
}

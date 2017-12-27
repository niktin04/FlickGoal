package com.niktin.flickgoal;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by NikTin on 26/12/17 at 17:59.
 */

public class SwipeTrailObject implements GameObject {

    ArrayList<Point> trailPoints = new ArrayList<>();
    private static final int MAX_TRAIL_WIDTH = Constants.SCREEN_WIDTH / 10;

    SwipeTrailObject() {
    }

    void touchEvents(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_UP) {
            Point point = new Point();
            point.x = event.getX();
            point.y = event.getY();
            trailPoints.add(point);
        } else {
            clearTrailPoints();
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(5f);
        paint.setColor(Color.RED);

//        if (trailPoints != null) {
//            System.out.println(trailPoints.get(0) + " " + trailPoints.size());
//            int trailLength = trailPoints.size();
//            for (int i = 1; i < trailLength; i++) {
//                canvas.drawLine(trailPoints.get(i - 1).x, trailPoints.get(i - 1).y, trailPoints.get(i).x, trailPoints.get(i).y, paint);
//            }
//        }
        Path path = new Path();
        if (trailPoints.size() > 1) {
            int trailLength = trailPoints.size();
            for (int i = trailLength - 2; i < trailLength; i++) {
                if (i >= 0) {
                    Point point = trailPoints.get(i);

                    if (i == 0) {
                        Point next = trailPoints.get(i + 1);
                        point.dx = ((next.x - point.x) / 3);
                        point.dy = ((next.y - point.y) / 3);
                    } else if (i == trailPoints.size() - 1) {
                        Point prev = trailPoints.get(i - 1);
                        point.dx = ((point.x - prev.x) / 3);
                        point.dy = ((point.y - prev.y) / 3);
                    } else {
                        Point next = trailPoints.get(i + 1);
                        Point prev = trailPoints.get(i - 1);
                        point.dx = ((next.x - prev.x) / 3);
                        point.dy = ((next.y - prev.y) / 3);
                    }
                }
            }
        }

        boolean first = true;
        for (int i = 0; i < trailPoints.size(); i++) {
            Point point = trailPoints.get(i);
            if (first) {
                first = false;
                path.moveTo(point.x, point.y);
            } else {
                Point prev = trailPoints.get(i - 1);
                path.cubicTo(prev.x + prev.dx, prev.y + prev.dy, point.x - point.dx, point.y - point.dy, point.x, point.y);
            }
        }

        canvas.drawPath(path, paint);
    }

    void clearTrailPoints() {
        this.trailPoints.clear();
    }

    class Point {
        float x, y;
        float dx, dy;

        @Override
        public String toString() {
            return x + ", " + y;
        }
    }
}

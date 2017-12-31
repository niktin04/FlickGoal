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

    private ArrayList<Point> trailPoints = new ArrayList<>();
    private static final int MAX_TRAIL_WIDTH = Constants.SCREEN_WIDTH / 10;
    private Paint paint;

    SwipeTrailObject() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(NikTinHelperFunctions.getRandomColor());
    }

    void touchEvents(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_UP) {
            // When the hardware tracks events faster than they are delivered,
            // the event will contain a history of those skipped points.
            int historySize = event.getHistorySize();
            for (int i = 0; i < historySize; i++) {
                float historicalX = event.getHistoricalX(i);
                float historicalY = event.getHistoricalY(i);
                trailPoints.add(new Point(historicalX, historicalY));
            }
            // After replaying history, add point.
            trailPoints.add(new Point(event.getX(), event.getY()));
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        int pointsLength = trailPoints.size();
        if (pointsLength > 1) {
            for (int i = pointsLength - 2; i < pointsLength; i++) {
                if (i >= 0) {
                    Point point = trailPoints.get(i);

                    if (i == 0) {
                        Point next = trailPoints.get(i + 1);
                        point.dx = ((next.x - point.x) / 3);
                        point.dy = ((next.y - point.y) / 3);
                    } else if (i == pointsLength - 1) {
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

            for (int i = 1; i < pointsLength; i++) {
                Point point = trailPoints.get(i);
                Point prev = trailPoints.get(i - 1);
                float strokeWidth = MAX_TRAIL_WIDTH / pointsLength * i;
//                int strokeAlpha = 255 / pointsLength * i; // Alpha varies from 0 to 255

                Path path = new Path();
                paint.setStrokeWidth(strokeWidth);
//                paint.setAlpha(strokeAlpha);
                path.moveTo(prev.x, prev.y);
                path.cubicTo(prev.x + prev.dx, prev.y + prev.dy, point.x - point.dx, point.y - point.dy, point.x, point.y);
                canvas.drawPath(path, paint);
            }
        }
    }

    void clearTrailPoints() {
        trailPoints.clear();
    }

    class Point {
        float x, y;
        float dx, dy;

        Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return x + ", " + y;
        }
    }
}

package com.niktin.flickgoal;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Build;

/**
 * Created by NikTin on 30/12/17 at 16:09.
 */

public class HorizontalSliderObject implements GameObject {

    private int width = Constants.SCREEN_WIDTH / 4;
    private int height = Constants.SCREEN_WIDTH / 20;
    private int sliderY, sliderSpanStartX, sliderSpanFinishX;
    private int arcIntermediateDistance = 12;
    private Point boundaryRectanglePoint, solidRectanglePoint;
    private RectF boundaryRectangle, solidRectangle, boundaryArcLeft, boundaryArcRight;
    private int speed = 4;
    private Paint solidRectanglePaint, boundaryRectanglePaint, backgroundSlashPaint;
    private float offsetDistanceX, offsetDistanceY, maxSolidOffset = 11;
    private boolean showSolidRectangle = true;

    HorizontalSliderObject(int sliderY, int sliderSpanStartX, int sliderSpanFinishX, int rectangleCenterX) {
        this.sliderY = sliderY;
        this.sliderSpanStartX = sliderSpanStartX;
        this.sliderSpanFinishX = sliderSpanFinishX;

        boundaryRectanglePoint = new Point(rectangleCenterX, sliderY);
        solidRectanglePoint = new Point(rectangleCenterX, sliderY);

        solidRectanglePaint = new Paint();
        solidRectanglePaint.setAntiAlias(true);
        solidRectanglePaint.setColor(NikTinHelperFunctions.getRandomColor());

        boundaryRectanglePaint = new Paint();
        boundaryRectanglePaint.setAntiAlias(true);
        boundaryRectanglePaint.setStyle(Paint.Style.STROKE);
        boundaryRectanglePaint.setStrokeWidth(6);

        backgroundSlashPaint = new Paint();
        backgroundSlashPaint.setAlpha(40);
    }

    @Override
    public void update() {
        if (boundaryRectanglePoint.x + width / 2 > sliderSpanFinishX || boundaryRectanglePoint.x - width / 2 < sliderSpanStartX) {
            speed *= -1;
        }

        boundaryRectanglePoint.x += speed;
        solidRectanglePoint.x += speed;
    }

    @Override
    public void draw(Canvas canvas) {
        generateBackgroundSlashes(canvas);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (showSolidRectangle) {
                canvas.drawRoundRect(solidRectanglePoint.x - width / 2 + offsetDistanceX,
                        solidRectanglePoint.y - height / 2 + offsetDistanceY,
                        solidRectanglePoint.x + width / 2 + offsetDistanceX,
                        solidRectanglePoint.y + height / 2 + offsetDistanceY, height / 2, height / 2, solidRectanglePaint);
                canvas.drawArc(boundaryRectanglePoint.x - width / 2 + arcIntermediateDistance,
                        boundaryRectanglePoint.y - height / 2 + arcIntermediateDistance,
                        boundaryRectanglePoint.x - width / 2 + height - arcIntermediateDistance,
                        boundaryRectanglePoint.y + height / 2 - arcIntermediateDistance, 180, 90, false, boundaryRectanglePaint);
                canvas.drawArc(boundaryRectanglePoint.x + width / 2 - height + arcIntermediateDistance,
                        boundaryRectanglePoint.y - height / 2 + arcIntermediateDistance,
                        boundaryRectanglePoint.x + width / 2 - arcIntermediateDistance,
                        boundaryRectanglePoint.y + height / 2 - arcIntermediateDistance, 0, 90, false, boundaryRectanglePaint);
            }
            canvas.drawRoundRect(boundaryRectanglePoint.x - width / 2,
                    boundaryRectanglePoint.y - height / 2,
                    boundaryRectanglePoint.x + width / 2,
                    boundaryRectanglePoint.y + height / 2, height / 2, height / 2, boundaryRectanglePaint);
        }
    }

    private void generateBackgroundSlashes(Canvas canvas) {
        int currentPosition = sliderSpanStartX;
        int objectSquareDimension = 24;
        Bitmap bitmap = NikTinHelperFunctions.getBitmapFromVectorDrawable(R.drawable.ic_slash, Color.BLACK, objectSquareDimension, objectSquareDimension);
        while (currentPosition < sliderSpanFinishX - 7) {
            canvas.drawBitmap(bitmap, currentPosition, sliderY - objectSquareDimension / 2, backgroundSlashPaint);
            currentPosition += objectSquareDimension - 7;
        }
    }

    void offsetSolidRectangle(Point ballCenter) {
        float maxDistanceX = Constants.SCREEN_WIDTH - sliderSpanStartX - width / 2;
        float currentDistanceX = ballCenter.x - solidRectanglePoint.x;

        float maxDistanceY = Constants.SCREEN_HEIGHT - sliderY - height / 2;
        float currentDistanceY = ballCenter.y - sliderY;

        offsetDistanceX = maxSolidOffset * currentDistanceX / maxDistanceX;
        offsetDistanceY = maxSolidOffset * currentDistanceY / maxDistanceY;

        showSolidRectangle = ballCenter.y - Constants.SCREEN_WIDTH / 20 >= sliderY - height;
    }
}

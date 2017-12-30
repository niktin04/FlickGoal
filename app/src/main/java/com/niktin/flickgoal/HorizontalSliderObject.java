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
    private int direction = 1;
    private Paint solidRectanglePaint, boundaryRectanglePaint, backgroundSlashPaint;

    public HorizontalSliderObject(int sliderY, int sliderSpanStartX, int sliderSpanFinishX, int rectangleCenterX) {
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

        boundaryRectangle = new RectF(
                boundaryRectanglePoint.x - width / 2,
                boundaryRectanglePoint.y - height / 2,
                boundaryRectanglePoint.x + width / 2,
                boundaryRectanglePoint.y + height / 2);
        boundaryArcLeft = new RectF(
                boundaryRectanglePoint.x - width / 2 + arcIntermediateDistance,
                boundaryRectanglePoint.y - height / 2 + arcIntermediateDistance,
                boundaryRectanglePoint.x - width / 2 + height - arcIntermediateDistance,
                boundaryRectanglePoint.y + height / 2 - arcIntermediateDistance);
        boundaryArcRight = new RectF(
                boundaryRectanglePoint.x + width / 2 - height + arcIntermediateDistance,
                boundaryRectanglePoint.y - height / 2 + arcIntermediateDistance,
                boundaryRectanglePoint.x + width / 2 - arcIntermediateDistance,
                boundaryRectanglePoint.y + height / 2 - arcIntermediateDistance);
        solidRectangle = new RectF(
                solidRectanglePoint.x - width / 2 + 4,
                solidRectanglePoint.y - height / 2 + 4,
                solidRectanglePoint.x + width / 2 - 4,
                solidRectanglePoint.y + height / 2 - 4);
    }

    @Override
    public void update() {
        if (boundaryRectangle.right > sliderSpanFinishX || boundaryRectangle.left < sliderSpanStartX) {
            direction *= -1;
        }
        boundaryRectangle.offset(speed * direction, 0);
        boundaryArcLeft.offset(speed * direction, 0);
        boundaryArcRight.offset(speed * direction, 0);
        solidRectangle.offset(speed * direction, 0);
    }

    @Override
    public void draw(Canvas canvas) {
        generateBackgroundSlashes(canvas);
        canvas.drawRoundRect(solidRectangle, height / 2, height / 2, solidRectanglePaint);
        canvas.drawRoundRect(boundaryRectangle, height / 2, height / 2, boundaryRectanglePaint);
        canvas.drawArc(boundaryArcLeft, 180, 90, false, boundaryRectanglePaint);
        canvas.drawArc(boundaryArcRight, 0, 90, false, boundaryRectanglePaint);
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
}

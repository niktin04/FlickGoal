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
    private int speed = 4;
    private RectF solidRectangle, boundaryRectangle, leftArc, rightArc, ballInteraction;
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

        solidRectangle = new RectF();
        boundaryRectangle = new RectF();
        leftArc = new RectF();
        rightArc = new RectF();
        ballInteraction = new RectF();
    }

    @Override
    public void update() {
        solidRectangle.set(solidRectanglePoint.x - width / 2 + offsetDistanceX,
                solidRectanglePoint.y - height / 2 + offsetDistanceY,
                solidRectanglePoint.x + width / 2 + offsetDistanceX,
                solidRectanglePoint.y + height / 2 + offsetDistanceY);
        boundaryRectangle.set(boundaryRectanglePoint.x - width / 2,
                boundaryRectanglePoint.y - height / 2,
                boundaryRectanglePoint.x + width / 2,
                boundaryRectanglePoint.y + height / 2);
        leftArc.set(boundaryRectanglePoint.x - width / 2 + arcIntermediateDistance,
                boundaryRectanglePoint.y - height / 2 + arcIntermediateDistance,
                boundaryRectanglePoint.x - width / 2 + height - arcIntermediateDistance,
                boundaryRectanglePoint.y + height / 2 - arcIntermediateDistance);
        rightArc.set(boundaryRectanglePoint.x + width / 2 - height + arcIntermediateDistance,
                boundaryRectanglePoint.y - height / 2 + arcIntermediateDistance,
                boundaryRectanglePoint.x + width / 2 - arcIntermediateDistance,
                boundaryRectanglePoint.y + height / 2 - arcIntermediateDistance);

        if (boundaryRectanglePoint.x + width / 2 > sliderSpanFinishX || boundaryRectanglePoint.x - width / 2 < sliderSpanStartX) {
            speed *= -1;
        }

        boundaryRectanglePoint.x += speed;
        solidRectanglePoint.x += speed;
    }

    @Override
    public void draw(Canvas canvas) {
        generateBackgroundSlashes(canvas);

        if (showSolidRectangle) {
            canvas.drawRoundRect(solidRectangle, height / 2, height / 2, solidRectanglePaint);
            canvas.drawArc(leftArc, 180, 90, false, boundaryRectanglePaint);
            canvas.drawArc(rightArc, 0, 90, false, boundaryRectanglePaint);
        }
        canvas.drawRoundRect(boundaryRectangle, height / 2, height / 2, boundaryRectanglePaint);
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

    void ballInteraction(StrikerObject ball) {
        if (showSolidRectangle) {
            showSolidRectangle = ball.getPositionPoint().y - Constants.SCREEN_WIDTH / 20 >= sliderY - height;
        } else {
            showSolidRectangle = ball.getPositionPoint().y - Constants.SCREEN_WIDTH / 20 >= sliderY + height / 2;
        }

        if (showSolidRectangle) {
            float maxDistanceX = Constants.SCREEN_WIDTH - sliderSpanStartX - width / 2;
            float currentDistanceX = ball.getPositionPoint().x - solidRectanglePoint.x;

            float maxDistanceY = Constants.SCREEN_HEIGHT - sliderY - height / 2;
            float currentDistanceY = ball.getPositionPoint().y - sliderY;

            offsetDistanceX = maxSolidOffset * currentDistanceX / maxDistanceX;
            offsetDistanceY = maxSolidOffset * currentDistanceY / maxDistanceY;

            ballInteraction.set(boundaryRectangle);
            ballInteraction.inset(-1 * Constants.SCREEN_WIDTH / 20, -1 * Constants.SCREEN_WIDTH / 20);
            if (ballInteraction.contains(ball.getPositionPoint().x, ball.getPositionPoint().y)) {
                ball.setSpeed(ball.getSpeedX() + speed, ball.getSpeedY() * -1 + 9);
            }
        }
    }
}

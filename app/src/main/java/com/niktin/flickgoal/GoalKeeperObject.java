package com.niktin.flickgoal;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

/**
 * Created by NikTin on 31/12/17 at 03:02.
 */

public class GoalKeeperObject implements GameObject {
    private int width = Constants.SCREEN_WIDTH / 4;
    private int height = Constants.SCREEN_WIDTH / 20;
    private int sliderY, sliderSpanStartX = 0, sliderSpanFinishX = Constants.SCREEN_WIDTH;
    private Point bodyRectanglePoint;
    private float maxSpeed = 11, speed;
    private float maxDistance = width / 2;
    private RectF bodyRectangle, ballInteraction;
    private Paint bodyRectanglePaint, eyePaint;
    private boolean showSolidRectangle = true;

    GoalKeeperObject(int sliderY) {
        this.sliderY = sliderY;

        bodyRectanglePoint = new Point(Constants.SCREEN_WIDTH / 2, sliderY);

        bodyRectanglePaint = new Paint();
        bodyRectanglePaint.setAntiAlias(true);

        eyePaint = new Paint();
        eyePaint.setAntiAlias(true);
        eyePaint.setColor(Color.WHITE);

        bodyRectangle = new RectF();
        ballInteraction = new RectF();
    }

    @Override
    public void update() {
        bodyRectanglePoint.x += speed;

        bodyRectangle.set(bodyRectanglePoint.x - width / 2,
                bodyRectanglePoint.y - height / 2,
                bodyRectanglePoint.x + width / 2,
                bodyRectanglePoint.y + height / 2);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRoundRect(bodyRectangle, height / 2, height / 2, bodyRectanglePaint);
        canvas.drawCircle(bodyRectanglePoint.x - width / 2 + height / 2, bodyRectanglePoint.y, height / 2 - 9, eyePaint);
        canvas.drawCircle(bodyRectanglePoint.x + width / 2 - height / 2, bodyRectanglePoint.y, height / 2 - 9, eyePaint);
        canvas.drawCircle(bodyRectanglePoint.x - width / 2 + height / 2, bodyRectanglePoint.y, height / 2 - 16, bodyRectanglePaint);
        canvas.drawCircle(bodyRectanglePoint.x + width / 2 - height / 2, bodyRectanglePoint.y, height / 2 - 16, bodyRectanglePaint);
    }

    void ballInteraction(StrikerObject ball) {
        float ballX = ball.getPositionPoint().x;
        float ballY = ball.getPositionPoint().y;

        if (bodyRectanglePoint.x + width / 2 < sliderSpanFinishX && bodyRectanglePoint.x - width / 2 > sliderSpanStartX) {
            if (ballX > bodyRectanglePoint.x - width / 2 && ballX < bodyRectanglePoint.x + width / 2) {
                speed = (ballX - bodyRectanglePoint.x) / maxDistance * maxSpeed;
            } else if (ballX <= bodyRectanglePoint.x - width / 2) {
                speed = -1 * maxSpeed;
            } else {
                speed = maxSpeed;
            }
        } else if (bodyRectanglePoint.x + width / 2 >= sliderSpanFinishX) {
            if (ballX > bodyRectanglePoint.x - width / 2 && ballX < bodyRectanglePoint.x) {
                speed = (ballX - bodyRectanglePoint.x) / maxDistance * maxSpeed;
            } else if (ballX <= bodyRectanglePoint.x - width / 2) {
                speed = -1 * maxSpeed;
            } else {
                speed = 0;
            }
        } else {
            if (ballX > bodyRectanglePoint.x && ballX < bodyRectanglePoint.x + width / 2) {
                speed = (ballX - bodyRectanglePoint.x) / maxDistance * maxSpeed;
            } else if (ballX >= bodyRectanglePoint.x + width / 2) {
                speed = maxSpeed;
            } else {
                speed = 0;
            }
        }

        ballInteraction.set(bodyRectangle);
        ballInteraction.inset(-1 * Constants.SCREEN_WIDTH / 20, -1 * Constants.SCREEN_WIDTH / 20);
        if (ballInteraction.contains(ball.getPositionPoint().x, ball.getPositionPoint().y)) {
            ball.setSpeed(ball.getSpeedX() + speed, ball.getSpeedY() * -1 + 16);
        }
    }
}

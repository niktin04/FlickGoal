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
    private float maxSpeed = 16, speed;
    private float maxDistance = width / 2;
    private RectF bodyRectangle, ballInteraction;
    private Paint bodyRectanglePaint, eyePaint, eyeBallPaint;
    private float leftEyeOffsetX = 7, rightEyeOffsetX = 7, eyeOffsetY = 7;
    private float eyeMaxOffsetX = width + height, eyeMaxOffsetY;

    GoalKeeperObject(int sliderY) {
        this.sliderY = sliderY;
        this.eyeMaxOffsetY = Constants.SCREEN_HEIGHT - sliderY;

        bodyRectanglePoint = new Point(Constants.SCREEN_WIDTH / 2, sliderY);

        bodyRectanglePaint = new Paint();
        bodyRectanglePaint.setAntiAlias(true);

        eyePaint = new Paint();
        eyePaint.setAntiAlias(true);
        eyePaint.setColor(Color.WHITE);

        eyeBallPaint = new Paint();
        eyeBallPaint.setAntiAlias(true);
        eyeBallPaint.setColor(Color.WHITE);
        eyeBallPaint.setAlpha(40);

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
        canvas.drawCircle(bodyRectanglePoint.x - width / 2 + height / 2 + leftEyeOffsetX, bodyRectanglePoint.y + eyeOffsetY, height / 2 - 16, bodyRectanglePaint);
        canvas.drawCircle(bodyRectanglePoint.x + width / 2 - height / 2 + rightEyeOffsetX, bodyRectanglePoint.y + eyeOffsetY, height / 2 - 16, bodyRectanglePaint);
        canvas.drawCircle(bodyRectanglePoint.x - width / 2 + height / 2 + leftEyeOffsetX, bodyRectanglePoint.y + eyeOffsetY, 7, eyeBallPaint);
        canvas.drawCircle(bodyRectanglePoint.x + width / 2 - height / 2 + rightEyeOffsetX, bodyRectanglePoint.y + eyeOffsetY, 7, eyeBallPaint);
    }

    void ballInteraction(BallObject ball) {
        float ballX = ball.getPositionPoint().x;
        float ballY = ball.getPositionPoint().y;

        leftEyeOffsetX = 7 * (ballX - (bodyRectanglePoint.x - width / 2 + height / 2)) / eyeMaxOffsetX;
        rightEyeOffsetX = 7 * (ballX - (bodyRectanglePoint.x + width / 2 - height / 2)) / eyeMaxOffsetX;
        eyeOffsetY = 7 * (ballY - bodyRectanglePoint.y) / eyeMaxOffsetY + 2;

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
        if (ballInteraction.contains(ballX, ballY)) {
            ball.setSpeed(ball.getSpeedX() + speed, ball.getSpeedY() * -1 + 16);
            RulesAndScoring.hopGoalkeeper += 1;
        }
    }
}

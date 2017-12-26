package com.niktin.flickgoal;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Build;

/**
 * Created by NikTin on 23/12/17 at 14:57.
 */

public class ObstacleHorizontalSlider implements GameObject {

    private int width = Constants.SCREEN_WIDTH / 4;
    private int height = Constants.SCREEN_WIDTH / 16;
    private int direction = 1;
    private int speed = 7;
    private int colour;
    private Point centerPoint;
    private float obstacleVerticalBoostVelocity = 10;

    ObstacleHorizontalSlider(int colour, Point centerPoint) {
        this.colour = colour;
        this.centerPoint = centerPoint;
    }

    @Override
    public void update() {
        if (centerPoint.x < Constants.SCREEN_WIDTH / 8 || centerPoint.x > Constants.SCREEN_WIDTH * 7 / 8) {
            direction *= -1;
        }
        centerPoint.offset(speed * direction, 0);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(colour);

        RectF rectangle = new RectF(centerPoint.x - width / 2, centerPoint.y - height / 2, centerPoint.x + width / 2, centerPoint.y + height / 2);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(rectangle, height / 2, height / 2, paint);
        } else {
            canvas.drawRect(rectangle, paint);
        }
    }

    void ballCollide(BallObject ballObject) {
        float ballRadius = ballObject.getBallRadius();
        RectF boundaryRectangle = new RectF(centerPoint.x - (width / 2) - ballRadius, centerPoint.y - (height / 2) - ballRadius, centerPoint.x + (width / 2) + ballRadius, centerPoint.y + (height / 2) + ballRadius);
        if (boundaryRectangle.contains(ballObject.getCenterX(), ballObject.getCenterY())) {
            ballObject.setBallSpeed(speed * direction * Constants.WALL_DAMPING_COEFFICIENT, 0);
            if (ballObject.getCenterY() > centerPoint.y)
                ballObject.setBallSpeed(0, (-1 * ballObject.getSpeedY() - ballObject.getSpeedY() * Constants.WALL_DAMPING_COEFFICIENT + obstacleVerticalBoostVelocity));
            else {
                ballObject.setBallSpeed(0, (-1 * ballObject.getSpeedY() - ballObject.getSpeedY() * Constants.WALL_DAMPING_COEFFICIENT - obstacleVerticalBoostVelocity));
            }
        }
    }
}

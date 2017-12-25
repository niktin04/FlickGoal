package com.niktin.flickgoal;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;

/**
 * Created by NikTin on 23/12/17 at 14:57.
 */

public class ObstacleHorizontalSlider implements GameObject {

    private int width = Constants.SCREEN_WIDTH / 4;
    private int height = Constants.SCREEN_WIDTH / 16;
    private int direction = 1;
    private int speed = 10;
    private int colour;
    private Point startingPoint;

    ObstacleHorizontalSlider(int colour, Point startingPoint) {
        this.colour = colour;
        this.startingPoint = startingPoint;
    }

    @Override
    public void update() {
        if (startingPoint.x < Constants.SCREEN_WIDTH / 8 || startingPoint.x > Constants.SCREEN_WIDTH * 7 / 8) {
            direction *= -1;
        }
        startingPoint.offset(speed * direction, 0);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(colour);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(startingPoint.x - width/2, startingPoint.y - height/2, startingPoint.x + width/2, startingPoint.y + height/2, height / 2, height / 2, paint);
        } else {
            canvas.drawRect(startingPoint.x - width/2, startingPoint.y - height/2, startingPoint.x + width/2, startingPoint.y + height/2, paint);
        }
    }
}

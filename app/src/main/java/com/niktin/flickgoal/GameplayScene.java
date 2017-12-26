package com.niktin.flickgoal;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v4.view.GestureDetectorCompat;
import android.view.MotionEvent;

/**
 * Created by NikTin on 26/12/17 at 00:49.
 */

public class GameplayScene implements Scene {

    private GestureDetectorCompat mDetector;

    private BallObject ballObject;
    private Point ballPoint;

    private ObstacleHorizontalSlider obstacleHorizontalSliderOne, obstacleHorizontalSliderTwo;
    private Point obstaclePointOne, obstaclePointTwo;

    public GameplayScene() {
        ballPoint = new Point(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 7 * 6);
        ballObject = new BallObject(Color.BLACK, ballPoint);

        obstaclePointOne = new Point(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 7 * 2);
        obstacleHorizontalSliderOne = new ObstacleHorizontalSlider(Color.RED, obstaclePointOne);

        obstaclePointTwo = new Point(Constants.SCREEN_WIDTH / 4 * 3, Constants.SCREEN_HEIGHT / 7 * 3);
        obstacleHorizontalSliderTwo = new ObstacleHorizontalSlider(Color.BLUE, obstaclePointTwo);

        mDetector = new GestureDetectorCompat(Constants.CURRENT_CONTEXT, new GestureListener(ballObject));
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        Point kickHitPoint = new Point();
        double distanceBetweenPoints;

        this.mDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
//                kickHitPoint.set((int) event.getX(), (int) event.getY());
//                distanceBetweenPoints = Math.sqrt((kickHitPoint.x - ballPoint.x) ^ 2 - (kickHitPoint.y - ballPoint.y) ^ 2);
//                if (distanceBetweenPoints < Constants.SCREEN_WIDTH / 16) {
//                }
            default:
        }
    }

    @Override
    public void update() {
        ballObject.update();
        obstacleHorizontalSliderOne.update();
        obstacleHorizontalSliderTwo.update();
        obstacleHorizontalSliderOne.ballCollide(ballObject);
        obstacleHorizontalSliderTwo.ballCollide(ballObject);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setColor(Color.CYAN);
        canvas.drawLine(Constants.SCREEN_WIDTH / 4, 0, Constants.SCREEN_WIDTH / 4, Constants.SCREEN_HEIGHT, paint);
        canvas.drawLine(Constants.SCREEN_WIDTH / 4 * 2, 0, Constants.SCREEN_WIDTH / 4 * 2, Constants.SCREEN_HEIGHT, paint);
        canvas.drawLine(Constants.SCREEN_WIDTH / 4 * 3, 0, Constants.SCREEN_WIDTH / 4 * 3, Constants.SCREEN_HEIGHT, paint);
        canvas.drawLine(0, Constants.SCREEN_HEIGHT / 7, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / 7, paint);
        canvas.drawLine(0, Constants.SCREEN_HEIGHT / 7 * 2, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / 7 * 2, paint);
        canvas.drawLine(0, Constants.SCREEN_HEIGHT / 7 * 3, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / 7 * 3, paint);
        canvas.drawLine(0, Constants.SCREEN_HEIGHT / 7 * 4, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / 7 * 4, paint);
        canvas.drawLine(0, Constants.SCREEN_HEIGHT / 7 * 5, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / 7 * 5, paint);
        canvas.drawLine(0, Constants.SCREEN_HEIGHT / 7 * 6, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / 7 * 6, paint);
        ballObject.draw(canvas);
        obstacleHorizontalSliderOne.draw(canvas);
        obstacleHorizontalSliderTwo.draw(canvas);
    }

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 0;
    }
}

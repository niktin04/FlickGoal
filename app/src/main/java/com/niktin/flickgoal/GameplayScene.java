package com.niktin.flickgoal;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.media.SoundPool;
import android.support.v4.view.GestureDetectorCompat;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by NikTin on 26/12/17 at 00:49.
 */

public class GameplayScene implements Scene {

    private BallObject ballObject;
    private Point ballPoint;

    private ObstacleHorizontalSlider obstacleHorizontalSliderOne, obstacleHorizontalSliderTwo;
    private Point obstaclePointOne, obstaclePointTwo;

    private SwipeTrailObject swipeTrailObject;
    private float maxFlingVelocity = ViewConfiguration.get(Constants.CURRENT_CONTEXT).getScaledMaximumFlingVelocity();

    private SoundPool soundPool;
    private int soundIds[] = new int[10];

    public GameplayScene() {
        ballPoint = new Point(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 7 * 6);
        ballObject = new BallObject(Color.BLACK, ballPoint);

        obstaclePointOne = new Point(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 7 * 2);
        obstacleHorizontalSliderOne = new ObstacleHorizontalSlider(Color.RED, obstaclePointOne);

        obstaclePointTwo = new Point(Constants.SCREEN_WIDTH / 4 * 3, Constants.SCREEN_HEIGHT / 7 * 3);
        obstacleHorizontalSliderTwo = new ObstacleHorizontalSlider(Color.BLUE, obstaclePointTwo);

        swipeTrailObject = new SwipeTrailObject();
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        swipeTrailObject.touchEvents(event);
    }

    @Override
    public void receiveFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
        RectF ballRectangle = new RectF(
                ballObject.getCenterX() - ballObject.getBallRadius(),
                ballObject.getCenterY() - ballObject.getBallRadius(),
                ballObject.getCenterX() + ballObject.getBallRadius(),
                ballObject.getCenterY() + ballObject.getBallRadius());
        ballRectangle.inset(-90,-90);
        if (ballRectangle.contains(event2.getX(), event2.getY())) {
            playBallKickedSound();
            float normalisedVelocityX = velocityX / maxFlingVelocity * Constants.BALL_NORMALISED_SPEED;
            float normalisedVelocityY = velocityY / maxFlingVelocity * Constants.BALL_NORMALISED_SPEED;
            ballObject.setBallSpeed(normalisedVelocityX, normalisedVelocityY);
        }
        swipeTrailObject.clearTrailPoints();
    }

    @Override
    public void receiveSoundPool(SoundPool soundPool) {
        this.soundPool = soundPool;
        soundIds[0] = this.soundPool.load(Constants.CURRENT_CONTEXT, R.raw.kick, 1);
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
        swipeTrailObject.draw(canvas);
    }

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 0;
    }

    public void playBallKickedSound() {
        soundPool.play(soundIds[0], 1, 1, 1, 0, 1);
    }
}

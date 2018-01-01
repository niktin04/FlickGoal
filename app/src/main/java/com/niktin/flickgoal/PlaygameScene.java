package com.niktin.flickgoal;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.SoundPool;
import android.view.MotionEvent;

/**
 * Created by NikTin on 28/12/17 at 19:14.
 */

public class PlaygameScene implements Scene {

    private static int WIDTH_SEGMENTS = 4;
    private static int HEIGHT_SEGMENTS = 8;
    private double[] positionX, positionY, isVisibleSelector;
    private Bitmap[] bitmaps;
    private GoalKeeperObject goalKeeper;
    private HorizontalSliderObject sliderOne, sliderTwo, sliderThree;
    private BallObject ball;
    private SwipeTrailObject swipeTrailObject;
    private RectF ballTouchArea = new RectF();
    private Paint touchAreaPaint;

    PlaygameScene() {
        positionX = new double[WIDTH_SEGMENTS * HEIGHT_SEGMENTS];
        positionY = new double[WIDTH_SEGMENTS * HEIGHT_SEGMENTS];
        isVisibleSelector = new double[WIDTH_SEGMENTS * HEIGHT_SEGMENTS];
        bitmaps = new Bitmap[WIDTH_SEGMENTS * HEIGHT_SEGMENTS];

        for (int i = 0; i < WIDTH_SEGMENTS * HEIGHT_SEGMENTS; i++) {
            positionX[i] = Math.random();
            positionY[i] = Math.random();
            isVisibleSelector[i] = Math.random();
            bitmaps[i] = NikTinHelperFunctions.getBitmapFromVectorDrawable(NikTinHelperFunctions.getRandomBackgroundParticle(), NikTinHelperFunctions.getRandomColor(), 16, 16);
        }

        goalKeeper = new GoalKeeperObject(160);
        sliderOne = new HorizontalSliderObject(400, 400, 900, 700);
        sliderTwo = new HorizontalSliderObject(700, 40, 470, 280);
        sliderThree = new HorizontalSliderObject(900, 90, 990, 400);
        ball = new BallObject(Constants.SCREEN_WIDTH / 2, 1700);
        swipeTrailObject = new SwipeTrailObject();

        touchAreaPaint = new Paint();
        touchAreaPaint.setAntiAlias(true);
        touchAreaPaint.setStyle(Paint.Style.STROKE);
        touchAreaPaint.setStrokeWidth(4);
        touchAreaPaint.setColor(Color.GRAY);
        touchAreaPaint.setAlpha(40);
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        swipeTrailObject.touchEvents(event);
        if (event.getAction() == MotionEvent.ACTION_UP) {
            swipeTrailObject.clearTrailPoints();
        }
    }

    @Override
    public void receiveFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
        int x = ball.getPositionPoint().x;
        int y = ball.getPositionPoint().y;
        int touchLimit = 160;

        ballTouchArea.set(x - touchLimit, y - touchLimit, x + touchLimit, y + touchLimit);
        if (ballTouchArea.contains(event2.getX(), event2.getY())) {
            float normalisedVelocityX = velocityX / Constants.MAX_FLING_VELOCITY * Constants.NORMALISED_VELOCITY;
            float normalisedVelocityY = velocityY / Constants.MAX_FLING_VELOCITY * Constants.NORMALISED_VELOCITY;

            ball.setSpeed(ball.getSpeedX() + normalisedVelocityX, ball.getSpeedY() + normalisedVelocityY);
        }

        swipeTrailObject.clearTrailPoints();
    }

    @Override
    public void receiveSoundPool(SoundPool soundPool) {

    }

    @Override
    public void update() {
        sliderOne.update();
        sliderTwo.update();
        sliderThree.update();

        sliderOne.ballInteraction(ball);
        sliderTwo.ballInteraction(ball);
        sliderThree.ballInteraction(ball);
        goalKeeper.ballInteraction(ball);

        goalKeeper.update();
        ball.update();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        generateBackground(canvas);
        swipeTrailObject.draw(canvas);
        sliderOne.draw(canvas);
        sliderTwo.draw(canvas);
        sliderThree.draw(canvas);
        goalKeeper.draw(canvas);
        ball.draw(canvas);
        canvas.drawCircle(ball.getPositionPoint().x, ball.getPositionPoint().y, 160, touchAreaPaint);
    }

    @Override
    public void terminate() {

    }

    private void generateBackground(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAlpha(160);

        int blockWidth = Constants.SCREEN_WIDTH / WIDTH_SEGMENTS;
        int blockHeight = Constants.SCREEN_HEIGHT / HEIGHT_SEGMENTS;
        for (int i = 0; i < HEIGHT_SEGMENTS; i++) {
            for (int j = 0; j < WIDTH_SEGMENTS; j++) {
                if (isVisibleSelector[4 * i + j] > 0.7 || isVisibleSelector[4 * i + j] < 0.4) {
                    canvas.drawBitmap(bitmaps[4 * i + j], blockWidth * j + (int) (blockWidth * positionX[4 * i + j]),
                            blockHeight * i + (int) (blockHeight * positionY[4 * i + j]), paint);
                }
            }
        }
    }
}

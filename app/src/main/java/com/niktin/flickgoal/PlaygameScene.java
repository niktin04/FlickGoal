package com.niktin.flickgoal;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
    private HorizontalSliderObject horizontalSliderObject;

    PlaygameScene() {
        positionX = new double[WIDTH_SEGMENTS * HEIGHT_SEGMENTS];
        positionY = new double[WIDTH_SEGMENTS * HEIGHT_SEGMENTS];
        isVisibleSelector = new double[WIDTH_SEGMENTS * HEIGHT_SEGMENTS];
        bitmaps = new Bitmap[WIDTH_SEGMENTS * HEIGHT_SEGMENTS];

        for (int i = 0; i < WIDTH_SEGMENTS * HEIGHT_SEGMENTS; i++) {
            positionX[i] = Math.random();
            positionY[i] = Math.random();
            isVisibleSelector[i] = Math.random();
            bitmaps[i] = NikTinHelperFunctions.getBitmapFromVectorDrawable(setBackgroundObjectType(Math.random()), setBackgroundObjectColor(Math.random()), 16, 16);
        }

        horizontalSliderObject = new HorizontalSliderObject(400, 400, 900, 700);
    }

    @Override
    public void receiveTouch(MotionEvent event) {

    }

    @Override
    public void receiveFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {

    }

    @Override
    public void receiveSoundPool(SoundPool soundPool) {

    }

    @Override
    public void update() {
        horizontalSliderObject.update();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        generateBackground(canvas);
        horizontalSliderObject.draw(canvas);
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

    private int setBackgroundObjectColor(double randomNumber) {
        if (randomNumber < 0.25) {
            return Color.rgb(254, 167, 87);
        } else if (randomNumber < 0.50) {
            return Color.rgb(241, 91, 92);
        } else if (randomNumber < 0.75) {
            return Color.rgb(127, 216, 212);
        } else {
            return Color.rgb(170, 170, 170);
        }
    }

    private int setBackgroundObjectType(double randomNumber) {
        if (randomNumber < 0.25) {
            return R.drawable.ic_cross;
        } else if (randomNumber < 0.50) {
            return R.drawable.ic_circle;
        } else if (randomNumber < 0.75) {
            return R.drawable.ic_triangle;
        } else {
            return R.drawable.ic_square;
        }
    }
}

package com.niktin.flickgoal;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.SoundPool;
import android.view.MotionEvent;

/**
 * Created by NikTin on 01/01/18 at 19:16.
 */

public class WelcomeScene implements Scene {

    private static int WIDTH_SEGMENTS = 4;
    private static int HEIGHT_SEGMENTS = 8;
    private double[] positionX, positionY, isVisibleSelector;
    private Bitmap[] bitmaps;
    Paint logoSolidPaint;

    WelcomeScene() {
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

        logoSolidPaint = new Paint();
        logoSolidPaint.setTypeface(Constants.phosphateSolidFont);
        logoSolidPaint.setColor(Color.rgb(254, 167, 87));
        logoSolidPaint.setTextSize(70);

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

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        generateBackground(canvas);

        canvas.drawLine(Constants.SCREEN_WIDTH / 2, 0, Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT, new Paint());
        canvas.drawLine(0, Constants.SCREEN_HEIGHT/2, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT/2, new Paint());

        int xPos = canvas.getWidth() / 2;
        int yPos = (int) (canvas.getHeight() / 2 - ((logoSolidPaint.descent() + logoSolidPaint.ascent()) / 2));

        canvas.drawText("FLICKGOAL", xPos, yPos, logoSolidPaint);
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

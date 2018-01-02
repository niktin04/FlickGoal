package com.niktin.flickgoal;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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
    Paint logoSolidPaint, subtitlePaint, backgroundPaint;
    private Rect r = new Rect();
    private boolean fadeLogoIn = true;
    private int counter = 0;
    private Rect settingsIcon = new Rect(20, 20, 160, 160);

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

        logoSolidPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        logoSolidPaint.setTypeface(Constants.phosphateSolidFont);
        logoSolidPaint.setShadowLayer(4, 0, 0, Color.BLACK);
        logoSolidPaint.setTextSize(110);

        subtitlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        subtitlePaint.setColor(Color.rgb(241, 91, 92));
        subtitlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        subtitlePaint.setTextSize(28);

        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.WHITE);
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

        while (fadeLogoIn) {
            if (counter == 255) {
                fadeLogoIn = false;
            }
            logoSolidPaint.setAlpha(counter);
            counter++;
        }

        canvas.drawRect(0, Constants.SCREEN_HEIGHT / 2 - 160, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / 2 + 160, backgroundPaint);
        drawCenterText(canvas, logoSolidPaint, "FLICKGOAL", 0);
        drawCenterText(canvas, subtitlePaint, "TEST YOUR FOOTBALL FRIENDLY FINGERS", 70);

        if (!fadeLogoIn) {
            canvas.drawRect(settingsIcon, new Paint());
            canvas.drawBitmap(NikTinHelperFunctions.getBitmapFromVectorDrawable(R.drawable.ic_settings_black_24dp, Color.GRAY, settingsIcon.width(), settingsIcon.height()), settingsIcon.left + 20, settingsIcon.top + 20, new Paint());
        }
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

    private void drawCenterText(Canvas canvas, Paint paint, String text, int offsetY) {
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.setTextAlign(Paint.Align.LEFT);
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom + offsetY;
        canvas.drawText(text, x, y, paint);
    }
}

package com.niktin.flickgoal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.SoundPool;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.MotionEvent;

/**
 * Created by NikTin on 28/12/17 at 19:14.
 */

public class PlaygameScene implements Scene {

    private static int WIDTH_SEGMENTS = 4;
    private static int HEIGHT_SEGMENTS = 8;
    private double[] positionX, positionY, typeSelector, colorSelector;

    PlaygameScene() {
        positionX = new double[WIDTH_SEGMENTS * HEIGHT_SEGMENTS];
        positionY = new double[WIDTH_SEGMENTS * HEIGHT_SEGMENTS];
        typeSelector = new double[WIDTH_SEGMENTS * HEIGHT_SEGMENTS];
        colorSelector = new double[WIDTH_SEGMENTS * HEIGHT_SEGMENTS];

        for (int i = 0; i < WIDTH_SEGMENTS * HEIGHT_SEGMENTS; i++) {
            positionX[i] = Math.random();
            positionY[i] = Math.random();
            typeSelector[i] = Math.random();
            colorSelector[i] = Math.random();
        }
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
    }

    @Override
    public void terminate() {

    }

    private void generateBackground(Canvas canvas) {
        Paint smallThingsPaint = new Paint();
        smallThingsPaint.setAlpha(70);

        int blockWidth = Constants.SCREEN_WIDTH / WIDTH_SEGMENTS;
        int blockHeight = Constants.SCREEN_HEIGHT / HEIGHT_SEGMENTS;
        for (int i = 0; i < HEIGHT_SEGMENTS; i++) {
            for (int j = 0; j < WIDTH_SEGMENTS; j++) {
                if (typeSelector[4 * i + j] < 0.20) {
                    canvas.drawBitmap(getBitmapFromVectorDrawable(Constants.CURRENT_CONTEXT, R.drawable.ic_circle, setBackgroundObjectColor(colorSelector[4 * i + j])),
                            blockWidth * j + (int) (blockWidth * positionX[4 * i + j]),
                            blockHeight * i + (int) (blockHeight * positionY[4 * i + j]), smallThingsPaint);
                } else if (typeSelector[4 * i + j] < 0.40) {
                    canvas.drawBitmap(getBitmapFromVectorDrawable(Constants.CURRENT_CONTEXT, R.drawable.ic_triangle, setBackgroundObjectColor(colorSelector[4 * i + j])),
                            blockWidth * j + (int) (blockWidth * positionX[4 * i + j]),
                            blockHeight * i + (int) (blockHeight * positionY[4 * i + j]), smallThingsPaint);
                } else if (typeSelector[4 * i + j] < 0.60) {
                    canvas.drawBitmap(getBitmapFromVectorDrawable(Constants.CURRENT_CONTEXT, R.drawable.ic_cross, setBackgroundObjectColor(colorSelector[4 * i + j])),
                            blockWidth * j + (int) (blockWidth * positionX[4 * i + j]),
                            blockHeight * i + (int) (blockHeight * positionY[4 * i + j]), smallThingsPaint);
                } else if (typeSelector[4 * i + j] < 0.75) {
                    canvas.drawBitmap(getBitmapFromVectorDrawable(Constants.CURRENT_CONTEXT, R.drawable.ic_square, setBackgroundObjectColor(colorSelector[4 * i + j])),
                            blockWidth * j + (int) (blockWidth * positionX[4 * i + j]),
                            blockHeight * i + (int) (blockHeight * positionY[4 * i + j]), smallThingsPaint);
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
            return Color.rgb(0, 0, 0);
        }
    }

    private static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId, int color) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        DrawableCompat.setTint(drawable, color);
        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth() / 4, canvas.getHeight() / 4);
        drawable.draw(canvas);

        return bitmap;
    }
}

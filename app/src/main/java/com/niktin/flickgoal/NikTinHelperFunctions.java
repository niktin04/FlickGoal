package com.niktin.flickgoal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

/**
 * Created by NikTin on 30/12/17 at 16:17.
 */

class NikTinHelperFunctions {

    static Bitmap getBitmapFromVectorDrawable(int drawableId, int color, int width, int height) {
        Drawable drawable = ContextCompat.getDrawable(Constants.CURRENT_CONTEXT, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        DrawableCompat.setTint(drawable, color);
        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);

        return bitmap;
    }



    static int getRandomColor() {
        double randomNumber = Math.random();
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

    static int getRandomBackgroundParticle() {
        double randomNumber = Math.random();
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

package com.niktin.flickgoal;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 * Created by NikTin on 23/12/17 at 11:17.
 */

class Constants {
    static int SCREEN_WIDTH;
    static int SCREEN_HEIGHT;

    static final int MAX_FPS = 30;

    @SuppressLint("StaticFieldLeak")
    static Context CURRENT_CONTEXT;

    static boolean GAME_OVER = false;
}

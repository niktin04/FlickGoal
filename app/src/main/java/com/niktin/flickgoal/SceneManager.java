package com.niktin.flickgoal;

import android.graphics.Canvas;
import android.media.SoundPool;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by NikTin on 26/12/17 at 00:46.
 */

class SceneManager {
    private ArrayList<Scene> scenes = new ArrayList<>();
    static int ACTIVE_SCENE;

    SceneManager() {
        ACTIVE_SCENE = 0;
        scenes.add(new GameplayScene());
    }

    void receiveFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
        scenes.get(ACTIVE_SCENE).receiveFling(event1, event2, velocityX, velocityY);
    }

    void receiveSoundPool(SoundPool soundPool) {
        scenes.get(ACTIVE_SCENE).receiveSoundPool(soundPool);
    }

    void receiveTouch(MotionEvent event) {
        scenes.get(ACTIVE_SCENE).receiveTouch(event);
    }

    void update() {
        scenes.get(ACTIVE_SCENE).update();
    }

    void draw(Canvas canvas) {
        scenes.get(ACTIVE_SCENE).draw(canvas);
    }
}

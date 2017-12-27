package com.niktin.flickgoal;

import android.content.Context;
import android.graphics.Canvas;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by NikTin on 23/12/17 at 11:20.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread mainThread;
    private SceneManager sceneManager;
    private SoundPool soundPool;

    public GamePanel(Context context) {
        super(context);
        getHolder().addCallback(this);
        mainThread = new MainThread(getHolder(), this);
        sceneManager = new SceneManager();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes attrs = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(10)
                    .setAudioAttributes(attrs)
                    .build();
        } else {
            soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        }

        sceneManager.receiveSoundPool(soundPool);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mainThread = new MainThread(getHolder(), this);
        mainThread.setRunning(true);
        mainThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                mainThread.setRunning(false);
                mainThread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        sceneManager.receiveTouch(event);
        return true;
    }

    public SoundPool getSoundPool() {
        return soundPool;
    }

    public void update() {
        sceneManager.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        sceneManager.draw(canvas);
    }
}

package com.niktin.flickgoal;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

import java.util.zip.CheckedOutputStream;

/**
 * Created by NikTin on 23/12/17 at 11:19.
 */

public class MainThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;

    void setRunning(boolean running) {
        this.running = running;
    }

    MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    @Override
    public void run() {
        long startTime;
        long timeMillis;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000 / Constants.MAX_FPS;

        while (running) {
            startTime = System.nanoTime();

            Canvas canvas = null;
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    gamePanel.update();
                    gamePanel.draw(canvas);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;

            try {
                if (waitTime > 0) {
                    sleep(waitTime);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            totalTime += System.nanoTime() - startTime;
            frameCount++;

            if (frameCount == Constants.MAX_FPS) {
                double averageFPS = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println("FPS: " + averageFPS);
            }
        }
    }
}

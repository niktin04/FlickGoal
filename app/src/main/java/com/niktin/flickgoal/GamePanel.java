package com.niktin.flickgoal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by NikTin on 23/12/17 at 11:20.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread mainThread;

    private BallObject ballObject;
    private Point ballPoint;

    private ObstacleHorizontalSlider obstacleHorizontalSlider;
    private Point obstaclePoint;

    public GamePanel(Context context) {
        super(context);

        getHolder().addCallback(this);

        mainThread = new MainThread(getHolder(), this);

        ballPoint = new Point(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 4 * 3);
        ballObject = new BallObject(Color.BLACK, ballPoint);

        obstaclePoint = new Point(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 4);
        obstacleHorizontalSlider = new ObstacleHorizontalSlider(Color.RED, obstaclePoint);

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
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ballPoint.set((int) event.getX(), (int) event.getY());
        }
        return true;
        //return super.onTouchEvent(event);
    }

    public void update() {
        ballObject.update(ballPoint);
        obstacleHorizontalSlider.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.WHITE);
        ballObject.draw(canvas);
        obstacleHorizontalSlider.draw(canvas);
    }
}

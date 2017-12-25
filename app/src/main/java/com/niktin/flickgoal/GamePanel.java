package com.niktin.flickgoal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v4.view.GestureDetectorCompat;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by NikTin on 23/12/17 at 11:20.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread mainThread;
    private GestureDetectorCompat mDetector;

    private BallObject ballObject;
    private Point ballPoint;

    private ObstacleHorizontalSlider obstacleHorizontalSlider;
    private Point obstaclePoint;

    public GamePanel(Context context) {
        super(context);

        getHolder().addCallback(this);

        mainThread = new MainThread(getHolder(), this);

        ballPoint = new Point(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 7 * 6);
        ballObject = new BallObject(Color.BLACK, ballPoint);

        obstaclePoint = new Point(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 4);
        obstacleHorizontalSlider = new ObstacleHorizontalSlider(Color.RED, obstaclePoint);

        mDetector = new GestureDetectorCompat(context, new GestureListener(ballObject));

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
        Point kickHitPoint = new Point();
        double distanceBetweenPoints;

        this.mDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:
//                kickHitPoint.set((int) event.getX(), (int) event.getY());
//                distanceBetweenPoints = Math.sqrt((kickHitPoint.x - ballPoint.x) ^ 2 - (kickHitPoint.y - ballPoint.y) ^ 2);
//                if (distanceBetweenPoints < Constants.SCREEN_WIDTH / 16) {
//                }
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }


    public void update() {
        ballObject.update();
        obstacleHorizontalSlider.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.WHITE);

        Paint paint = new Paint();
        paint.setColor(Color.CYAN);
        canvas.drawLine(Constants.SCREEN_WIDTH / 4, 0, Constants.SCREEN_WIDTH / 4, Constants.SCREEN_HEIGHT, paint);
        canvas.drawLine(Constants.SCREEN_WIDTH / 4 * 2, 0, Constants.SCREEN_WIDTH / 4 * 2, Constants.SCREEN_HEIGHT, paint);
        canvas.drawLine(Constants.SCREEN_WIDTH / 4 * 3, 0, Constants.SCREEN_WIDTH / 4 * 3, Constants.SCREEN_HEIGHT, paint);
        canvas.drawLine(0, Constants.SCREEN_HEIGHT / 7, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / 7, paint);
        canvas.drawLine(0, Constants.SCREEN_HEIGHT / 7 * 2, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / 7 * 2, paint);
        canvas.drawLine(0, Constants.SCREEN_HEIGHT / 7 * 3, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / 7 * 3, paint);
        canvas.drawLine(0, Constants.SCREEN_HEIGHT / 7 * 4, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / 7 * 4, paint);
        canvas.drawLine(0, Constants.SCREEN_HEIGHT / 7 * 5, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / 7 * 5, paint);
        canvas.drawLine(0, Constants.SCREEN_HEIGHT / 7 * 6, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / 7 * 6, paint);

        ballObject.draw(canvas);
        obstacleHorizontalSlider.draw(canvas);
    }
}

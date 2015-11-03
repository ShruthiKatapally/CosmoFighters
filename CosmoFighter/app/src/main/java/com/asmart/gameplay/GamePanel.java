package com.asmart.gameplay;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.asmart.cosmofighter.R;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{
    public static final int WIDTH = 1600;
    public static final int HEIGHT = 960;
    public static final int MOVESPEED = -5;
    private MainThread thread;
    private GameBackground bg;
    private GamePlayer gamePlayer;
    public GamePanel(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format,int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(retry) {
            try{
                thread.setRunning(false);
                thread.join();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        bg = new GameBackground(BitmapFactory.decodeResource(getResources(), R.drawable.space));
        gamePlayer = new GamePlayer(BitmapFactory.decodeResource(getResources(),R.drawable.testplayer), 65,25,3);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN) {
            if (!gamePlayer.getPlaying())
            {
                gamePlayer.setPlaying(true);
            }
            else{
                gamePlayer.setUp(true);
            }
            return true;
        }
        if(event.getAction()==MotionEvent.ACTION_UP)
        {
            gamePlayer.setUp(false);
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void update() {

        if(gamePlayer.getPlaying()){
            bg.update();
            gamePlayer.update();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        final float scaleFactorX = getWidth()/(WIDTH*1.f);
        final float scaleFactorY = getHeight()/(HEIGHT*1.f);
        if(canvas != null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            bg.draw(canvas);
            gamePlayer.draw(canvas);
            canvas.restoreToCount(savedState);
        }
    }
}
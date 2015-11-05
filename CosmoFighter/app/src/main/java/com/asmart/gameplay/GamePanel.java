package com.asmart.gameplay;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.asmart.cosmofighter.R;

import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    public static final int WIDTH = 1600;
    public static final int HEIGHT = 960;
    public static final int MOVESPEED = -5;
    private MainThread thread;
    private GameBackground bg;
    private GamePlayer gamePlayer;

    // for Debris
    private long missileStartingTime;
    private Random rand = new Random();
    private ArrayList<Missile> missiles;

    // For Health
    private ArrayList<Health> powerUps;
    private long healthHelperTime;

    public GamePanel(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        int maxLoopTrial = 0;
        while (retry && maxLoopTrial < 1500) {
            maxLoopTrial++;
            try {
                thread.setRunning(false);
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        bg = new GameBackground(BitmapFactory.decodeResource(getResources(), R.drawable.space));
        gamePlayer = new GamePlayer(BitmapFactory.decodeResource(getResources(), R.drawable.testplayer), 95, 90, 3);

        //for missile
        missiles = new ArrayList<Missile>();
        missileStartingTime = System.nanoTime();
        // Health objects initiation
        powerUps = new ArrayList<Health>();
        healthHelperTime = System.nanoTime();
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (!gamePlayer.getPlaying()) {
                gamePlayer.setPlaying(true);
            } else {
                gamePlayer.setUp(true);
            }
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            gamePlayer.setUp(false);
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void update() {

        if (gamePlayer.getPlaying()) {
            bg.update();
            gamePlayer.update();
            // for Health helpers
            if ((System.nanoTime() - healthHelperTime)/1000000 > (50000 - gamePlayer.getScore() / 2)) {

                if (powerUps.size() == 0) {
                    System.out.println("Reaching making health helpers");
                    powerUps.add(new Health(BitmapFactory.decodeResource(getResources(), R.drawable.ic_health), WIDTH + 10,(int) (rand.nextDouble() * (HEIGHT)), 100, 100, 1));
                } else {
                    powerUps.add(new Health(BitmapFactory.decodeResource(getResources(), R.drawable.ic_health), WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 100, 100, 1));
                }

                healthHelperTime = System.nanoTime();
            }
            for (int i = 0; i < powerUps.size(); i++) {
                powerUps.get(i).update();
                // when collison occurs decrement the player life by 1
                if (collision(powerUps.get(i), gamePlayer)) {
                    powerUps.remove(i);
                    gamePlayer.setLives(1);
                    break;
                }

                if (powerUps.get(i).getX() < -100) {
                    powerUps.remove(i);
                    break;
                }

            }
            // for missiles
            long misslelap = (System.nanoTime() - missileStartingTime) / 1000000;

            if (misslelap > (2000 - gamePlayer.getScore() / 4)) {
                System.out.println("Reaching making missile");
                if (missiles.size() == 0) {
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.missile), WIDTH + 10, HEIGHT / 2, 45, 15, gamePlayer.getScore(), 13));
                } else {
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.missile), WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 45, 15, gamePlayer.getScore(), 13));
                }

                missileStartingTime = System.nanoTime();
            }
            for (int i = 0; i < missiles.size(); i++) {
                missiles.get(i).update();
                // when collison occurs decrement the player life by 1
                if (collision(missiles.get(i), gamePlayer)) {
                    missiles.remove(i);

                    if(gamePlayer.getLives()>0) {
                        gamePlayer.decLives();
                    }
                    if(gamePlayer.getLives()==0)
                    {
                        gamePlayer.setPlaying(false);
                        break;
                        // need to put the exit logic here in this block
                    }
                }

                if (missiles.get(i).getX() < -100) {
                    missiles.remove(i);
                    break;
                }

            }

        }
    }

    public boolean collision(GameObject a, GameObject b) {
        
        if (Rect.intersects(a.getRect(), b.getRect())) {
            return true;
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        final float scaleFactorX = getWidth() / (WIDTH * 1.f);
        final float scaleFactorY = getHeight() / (HEIGHT * 1.f);
        if (canvas != null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            bg.draw(canvas);
            // for missile
            for (Missile m : missiles) {
                m.draw(canvas);
            }
            gamePlayer.draw(canvas);

            for(Health h: powerUps){
                h.draw(canvas);
            }
            canvas.restoreToCount(savedState);
        }
    }
}
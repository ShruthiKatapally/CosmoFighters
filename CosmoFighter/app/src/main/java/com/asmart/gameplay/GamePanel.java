package com.asmart.gameplay;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.asmart.cosmofighter.HighScoresActivity;
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
    private int timer;
    // Debris related variables
    private long debrisStartingTime;
    private Random rand = new Random();
    private int flaggingTime;
    private ArrayList<Debris> debris;
    private ArrayList<Ammo> ammos;
    private ArrayList<Collision> collide;
    private Context context;
    private int levelNum;
    private Flag flag;
    private int trackPlayerX;
    private int healthFrequency;
    private int debrisFrequency;
    private int ammoFrequency;
    private int debrisCoordX;
    private int debrisCoordY;
    private int flagCoordX;
    private int flagCoordY;
    private int packNum;
    //Ammo related variables
    private long ammoStartingTime;
    private long gameStartTime;
    //Health related variables
    private ArrayList<Health> powerUps;
    private long healthHelperTime;
    public GamePanel(Context context) {
        super(context);
        this.context = context;
        getHolder().addCallback(this);
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
                thread = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        bg = new GameBackground(BitmapFactory.decodeResource(getResources(), R.drawable.space));
        gamePlayer = new GamePlayer(BitmapFactory.decodeResource(getResources(), R.drawable.testplayer), 95, 90, 3);
        SharedPreferences settings =context.getSharedPreferences(context.getString(R.string.APP_PREFERENCES), 0);

        gameStartTime = System.nanoTime();
        levelNum = settings.getInt(this.context.getString(R.string.LEVEL), 1);
        packNum = settings.getInt(this.context.getString(R.string.PACKAGE), 1);
        if(levelNum==1)
        {
            //easy
            debrisFrequency = 2000;
            healthFrequency = 20000;
            ammoFrequency = 7000;
            flaggingTime = 80;
        }
        if(levelNum==2)
        {
            //medium
            debrisFrequency = 5000;
            healthFrequency = 50000;
            ammoFrequency = 10000;
            flaggingTime = 100;
        }
        if(levelNum==3)
        {
            //hard
            debrisFrequency = 8000;
            healthFrequency = 30000;
            ammoFrequency = 15000;
            flaggingTime = 130;
        }




        //For Debris
        debris = new ArrayList<>();
        debrisStartingTime = System.nanoTime();
        collide = new ArrayList<>();
        thread = new MainThread(getHolder(), this);
        timer = 0;

        //for Ammo
        ammos = new ArrayList<>();
        ammoStartingTime = System.nanoTime();

        // Health objects initiation
        powerUps = new ArrayList<>();
        healthHelperTime = System.nanoTime();
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (!gamePlayer.getPlaying()) {
                gamePlayer.setPlaying(true);
            }
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            gamePlayer.setUserX(event.getX());
            gamePlayer.setUserY(event.getY());
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void update() {

        if(packNum==1)
        {
            debrisCoordX = WIDTH + 10;
            debrisCoordY =  (int) (rand.nextDouble() * (HEIGHT));
        }
        if(packNum==2 || packNum==3)
        {
            debrisCoordX = (int) (rand.nextDouble()*(WIDTH));
            debrisCoordY =  -10;
        }
        if (gamePlayer.getPlaying()) {
            bg.update();
            gamePlayer.update();

            // For Health helpers
            if ((System.nanoTime() - healthHelperTime)/1000000 > (healthFrequency - gamePlayer.getScore() / 2)) {
                if (powerUps.size() == 0) {
                   // System.out.println("Reaching making health helpers");
                    powerUps.add(new Health(this.context,BitmapFactory.decodeResource(getResources(), R.drawable.ic_health), WIDTH + 10,(int) (rand.nextDouble() * (HEIGHT)), 100, 100, 1));
                } else {
                    powerUps.add(new Health(this.context,BitmapFactory.decodeResource(getResources(), R.drawable.ic_health), WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 100, 100, 1));
                }
                healthHelperTime = System.nanoTime();
            }

            for (int i = 0; i < powerUps.size(); i++) {
                powerUps.get(i).update();
                // When collision occurs decrement the player life by 1 and display collision effect
                if (isCollision(powerUps.get(i), gamePlayer)) {
                    powerUps.remove(i);
                    gamePlayer.setScore(50);
                    gamePlayer.setLives(1);
                    break;
                }

                if (powerUps.get(i).getX() < -100) {
                    powerUps.remove(i);
                    break;
                }

            }
            // Flagging time
            if((( System.nanoTime() - gameStartTime)/1000000000>flaggingTime) &&flag==null){                               //

                // time to draw a flag on to the screen randomly.
               // System.out.println("Flag has been created!!!!");
                flagCoordX = getWidth()-250;
                flagCoordY = HEIGHT- 600;
                flag = new Flag(this.context,BitmapFactory.decodeResource(getResources(), R.drawable.flag),flagCoordX,flagCoordY, 220, 190, 1);
                flag.update();
            }

            if(flag!=null){                 // that means flag has been created
                    flag.update();                        //now its time to see if it has collided with our fighter
               // System.out.println("Flag is being updated!!!");
                if(isCollision(gamePlayer,flag))
                {
                    gamePlayer.setPlaying(false);
                    startHighScoreActivity();
                }
            }


            // For Debris
            long debrislap = (System.nanoTime() - debrisStartingTime) / 1000000;

            if (debrislap > (debrisFrequency - gamePlayer.getScore() / 4)) {
                if (debris.size() == 0) {
                    debris.add(new Debris(this.context,BitmapFactory.decodeResource(getResources(), R.drawable.debris), debrisCoordX, debrisCoordY, 68, 72, gamePlayer.getScore(), 1));
                }
                else {
                    debris.add(new Debris(this.context,BitmapFactory.decodeResource(getResources(), R.drawable.debris), debrisCoordX, debrisCoordY, 68, 70, gamePlayer.getScore(), 1));
                }

                debrisStartingTime = System.nanoTime();
            }
            for (int i = 0; i < debris.size(); i++) {
                debris.get(i).update();
                // when collision occurs decrement the player life by 1
                if (isCollision(debris.get(i), gamePlayer)) {
                    debris.remove(i);
                    collide.add(0, new Collision(BitmapFactory.decodeResource(getResources(), R.drawable.collision), gamePlayer.getX(), gamePlayer.getY() - 30, 100, 100, 25));
                    collide.get(0).update();
                    if(gamePlayer.getLives()>0) {
                        gamePlayer.decLives();
                        gamePlayer.setScore(-50);
                    }

                    if(gamePlayer.getLives() == 0)
                    {
                        gamePlayer.setPlaying(false);
                        startHighScoreActivity();
                        break;
                    }
                }

                if (debris.get(i).getX() < -100) {
                    debris.remove(i);
                    break;
                }

                if (debris.get(i).getY() > GamePanel.HEIGHT) {
                    debris.remove(i);
                    break;
                }
            }

            // For ammo
            long ammolap = (System.nanoTime() - ammoStartingTime) / 1000000;

            if (ammolap > (ammoFrequency - gamePlayer.getScore() / 4)) {
                if (ammos.size() == 0) {
                    ammos.add(new Ammo(BitmapFactory.decodeResource(getResources(), R.drawable.ammo), WIDTH + 10, HEIGHT / 2, 70, 67, gamePlayer.getScore(), 1));
                } else {
                    ammos.add(new Ammo(BitmapFactory.decodeResource(getResources(), R.drawable.ammo), WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 70, 67, gamePlayer.getScore(), 1));
                }

                ammoStartingTime = System.nanoTime();
            }
            for (int i = 0; i < ammos.size(); i++) {
                ammos.get(i).update();
                // when collision occurs fire bullets


                if (ammos.get(i).getX() < -100) {
                    ammos.remove(i);
                    break;
                }
            }






          }
        }

    public boolean isCollision(GameObject a, GameObject b) {
        return Rect.intersects(a.getRect(), b.getRect());
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        final float scaleFactorX = getWidth() / (WIDTH * 1.f);
        final float scaleFactorY = getHeight() / (HEIGHT * 1.f);
        if (canvas != null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            bg.draw(canvas);
          //  canvas.drawPaint(paintLives);
            gamePlayer.draw(canvas);

            // for Debris
            for (Debris m : debris) {
                m.draw(canvas);
            }

            //for Ammo
            for(Ammo a : ammos) {
                a.draw(canvas);
            }

             for(Health h: powerUps){
                h.draw(canvas);
             }

            if(flag!=null){
                flag.draw(canvas);
                System.out.println("drawing Flag");
            }
            if(collide.size()!=0)
            {
                timer++;
                collide.get(0).draw(canvas);

            }
            if(timer ==3){
                timer =0;
                if(collide.size()!=0)
                {
                    collide.remove(0);
                }
            }
            writeText(canvas);


           canvas.restoreToCount(savedState);
        }
    }

    public void writeText(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(70);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("SCORE: " + gamePlayer.getScore(), 10, HEIGHT-10, paint);
        canvas.drawText("LIVES: " + gamePlayer.getLives(), 1000, HEIGHT-10, paint);
    }

    public void startHighScoreActivity() {
        //Update the package and level in shared preferences
        SharedPreferences settings = context.getSharedPreferences(context.getString(R.string.APP_PREFERENCES), 0);
        SharedPreferences.Editor edit = settings.edit();
        edit.putInt(context.getString(R.string.PACKAGE), 0);
        edit.putInt(context.getString(R.string.LEVEL), 0);
        edit.commit();

        Intent intent = new Intent(this.context , HighScoresActivity.class);
        intent.putExtra(context.getString(R.string.GAME_SCORE), gamePlayer.getScore());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.context.startActivity(intent);
        Activity currentActivity = (Activity) context;
        currentActivity.finish();
    }
}
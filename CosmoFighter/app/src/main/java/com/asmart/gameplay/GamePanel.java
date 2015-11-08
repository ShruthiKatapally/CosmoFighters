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
import com.asmart.helpers.MusicHelper;

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
    private int bullettimer;

    // Debris related variables
    private long debrisStartingTime;
    private Random rand = new Random();
    private int flaggingTime;

    private ArrayList<Debris> debris;
    private ArrayList<Ammo> ammos;
    private ArrayList<Collision> collide;
    private ArrayList<Bullet> bullet;
    private Context context;
    private int levelNum;
    private Flag flag;
    private int trackPlayerX;
    private long bulletStartingtime;
    private int healthFrequency;
    private int debrisFrequency;
    private int bulletFrequency;
    private int ammoFrequency;
    private ArrayList<Coins> coins;
    private int debrisCoordX;
    private int debrisCoordY;
    private int flagCoordX;
    private int flagCoordY;
    private int packNum;
    private boolean bulletfiring;
    int bulletcount;

    //Ammo related variables
    private long ammoStartingTime;
    private long gameStartTime;

    //Health related variables
    private ArrayList<Health> powerUps;
    private long healthHelperTime;

    private boolean isFlagReached = false;
    private long coinHelperTime;

    SharedPreferences settings;
    private MusicHelper mh;

    public GamePanel(Context context) {
        super(context);
        this.context = context;
        getHolder().addCallback(this);
        setFocusable(true);
        settings = context.getSharedPreferences(context.getString(R.string.APP_PREFERENCES), 0);
        mh = MusicHelper.getInstance(context);
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
            debrisFrequency = 1000;
            healthFrequency = 20000;
            ammoFrequency = 7000;
            flaggingTime = 80;
        }
        if(levelNum==2)
        {
            //medium
            debrisFrequency = 700;
            healthFrequency = 50000;
            ammoFrequency = 10000;
            flaggingTime = 100;
        }
        if(levelNum==3)
        {
            //hard
            debrisFrequency = 800;
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

        //for bullets
        bullet = new ArrayList<>();
        bullettimer=0;
        bulletStartingtime = System.nanoTime();

        // Health objects initiation
        powerUps = new ArrayList<>();
        healthHelperTime = System.nanoTime();
        
        coins = new ArrayList<>();
        
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

    public void updateHealthHelper(){

        // For Health helpers
        if ((System.nanoTime() - healthHelperTime)/1000000 > (healthFrequency - gamePlayer.getScore() / 2)) {
            if (powerUps.size() == 0) {
                // System.out.println("Reaching making health helpers");
                powerUps.add(new Health(this.context,BitmapFactory.decodeResource(getResources(), R.drawable.ic_health), WIDTH + 10,(int) (rand.nextDouble() * (HEIGHT)), 100, 100, 1));
            } else {
                powerUps.add(new Health(this.context, BitmapFactory.decodeResource(getResources(), R.drawable.ic_health), WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 100, 100, 1));
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
    }

    void updateFlag(){
        // Flagging time
        if((( System.nanoTime() - gameStartTime)/1000000000>flaggingTime) &&flag==null){                               //

            // time to draw a flag on to the screen randomly.
            // System.out.println("Flag has been created!!!!");
            flagCoordX = getWidth()-500;
            flagCoordY = 0;
            flag = new Flag(this.context,BitmapFactory.decodeResource(getResources(), R.drawable.flag),flagCoordX,flagCoordY, 220, 190, 1);
            flag.update();
        }

        if(flag!=null){                 // that means flag has been created
            flag.update();                        //now its time to see if it has collided with our fighter
            // System.out.println("Flag is being updated!!!");
            if(isCollision(gamePlayer,flag))
            {
                isFlagReached = true;
                gamePlayer.setPlaying(false);
                startHighScoreActivity();
            }
        }



    }
    void addNewDebris(){
        // For Debris
        long debrislap = (System.nanoTime() - debrisStartingTime) / 1000000;

        if (debrislap > (debrisFrequency - gamePlayer.getScore() / 8)) {
            if (debris.size() == 0) {
                debris.add(new Debris(this.context,BitmapFactory.decodeResource(getResources(), R.drawable.debris), debrisCoordX, debrisCoordY, 68, 72, gamePlayer.getScore(), 1));
            }
            else {
                debris.add(new Debris(this.context, BitmapFactory.decodeResource(getResources(), R.drawable.debris), debrisCoordX, debrisCoordY, 68, 70, gamePlayer.getScore(), 1));
            }


            debrisStartingTime = System.nanoTime();
        }
    }


    void removeCollidedDebrisAndBullet(){


        //update bullet
        for(Bullet b: bullet){
            b.update();
            boolean collided = false;
            for(Debris d: debris) {

                if (isCollision(b, d)) {
                    collided = true;
                    bullet.remove(b);
                    debris.remove(d);
                }

            }

        }

    }

    void updateDebris(){

        System.out.println("Deris size  ..." + debris.size());
        for (int i = 0; i < debris.size(); i++) {

            debris.get(i).update();

            // when collision occurs decrement the player life by 1

            if (isCollision(debris.get(i), gamePlayer)) {
                if(settings.getBoolean(context.getString(R.string.SOUNDON), false)) {
                    mh.playExplosionMusic();
                }
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
    }

    void addAndUpdateAmmo(){
        long ammoLap = (System.nanoTime() - ammoStartingTime) / 1000000;

        if (ammoLap > (ammoFrequency - gamePlayer.getScore() / 5)) {
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
            if (isCollision(ammos.get(i), gamePlayer)) {
                if(settings.getBoolean(context.getString(R.string.SOUNDON), false)) {
                    mh.playAmmoMusic();
                }
                ammos.remove(i);
                gamePlayer.setScore(+50);
                bullet.clear();
                bulletcount =0;
                bulletfiring = true;
            }
            if (ammos.get(i).getX() < -100) {
                ammos.remove(i);
                break;
            }
        }
    }



    void fireBullets(){
        if(bulletcount < 50 && bulletfiring == true)
        {
             bullet.add(new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.bullet), gamePlayer.getX(), gamePlayer.getY()+gamePlayer.getHeight()/2 ,45, 15, 13));
            //bullet.add(new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.debris), gamePlayer.getX(), gamePlayer.getY(), 68, 72, 1));
            bulletcount++;

        }
    }
    void addAndupdateCoins(){
        if ((System.nanoTime() - coinHelperTime)/10000000 > (1700)) {
            if (coins.size() == 0) {
                // System.out.println("Reaching making health helpers");
                coins.add(new Coins(this.context,BitmapFactory.decodeResource(getResources(), R.drawable.bonus), WIDTH + 10,(int) (rand.nextDouble() * (HEIGHT)), 50, 49, 1));
            } else {
                coins.add(new Coins(this.context,BitmapFactory.decodeResource(getResources(), R.drawable.bonus), WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT)), 50, 49, 1));
            }
            coinHelperTime = System.nanoTime();
        }

        for (int i = 0; i < coins.size(); i++) {
            coins.get(i).update();
            // When collision occurs decrement the player life by 1 and display collision effect
            if (isCollision(coins.get(i), gamePlayer)) {
                if(settings.getBoolean(context.getString(R.string.SOUNDON), false)) {
                    mh.playCoinMusic();
                }
                coins.remove(i);
                gamePlayer.setScore(50);
                break;
            }

            if (coins.get(i).getX() < -100) {
                coins.remove(i);
                break;
            }

        }

    }
    public void update() {

        if (packNum == 1) {
            debrisCoordX = WIDTH + 10;
            debrisCoordY = (int) (rand.nextDouble() * (HEIGHT));
        }
        if (packNum == 2 || packNum == 3) {
            debrisCoordX = (int) (rand.nextDouble() * (WIDTH));
            debrisCoordY = -10;
        }


        if (gamePlayer.getPlaying()) {
            bg.update();
            gamePlayer.update();


            updateHealthHelper();

            updateFlag();

            addAndupdateCoins();
            addNewDebris();


           removeCollidedDebrisAndBullet();

            updateDebris();
            addAndUpdateAmmo();
            fireBullets();
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



            for(Bullet b:bullet){

                b.draw(canvas);

            }



            //for Ammo
            for(Ammo a : ammos) {
                a.draw(canvas);
            }

            for( Coins c : coins){
                c.draw(canvas);
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
        int currentPackage = settings.getInt(context.getString(R.string.PACKAGE), 1);
        int currentLevel = settings.getInt(context.getString(R.string.LEVEL), 1);
        int currPackStars = settings.getInt(context.getString(R.string.PACKAGE_STARS), 0);
        int currLvlStars = settings.getInt(context.getString(R.string.LEVEL_STARS), 0);
        SharedPreferences.Editor edit = settings.edit();
        edit.putInt(context.getString(R.string.PACKAGE), 0);
        edit.putInt(context.getString(R.string.LEVEL), 0);
        edit.commit();

        //Create a new intent for HighScoresActivity
        Intent intent = new Intent(this.context , HighScoresActivity.class);
        intent.putExtra(context.getString(R.string.GAME_SCORE), gamePlayer.getScore());
        intent.putExtra(context.getString(R.string.FLAG_REACHED), isFlagReached);
        intent.putExtra(context.getString(R.string.PACKAGE), currentPackage);
        intent.putExtra(context.getString(R.string.PACKAGE_STARS), currPackStars);
        intent.putExtra(context.getString(R.string.LEVEL), currentLevel);
        intent.putExtra(context.getString(R.string.LEVEL_STARS), currLvlStars);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.context.startActivity(intent);
        Activity currentActivity = (Activity) context;
        currentActivity.finish();
    }
}
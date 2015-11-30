package com.asmart.gameplay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

public class GamePlayer extends GameObject {
    private int score;
    private boolean playing;
    private Animation animation = new Animation();
    private long startTime;
    private int lives;
    private float userX;
    private float userY;
    private Bitmap spritesheet;

    private int screenWidth;
    private int screenHeight;
    public GamePlayer(Bitmap res, int w, int h, int numFrames,Context context){
        x = 100;

        y= GamePanel.HEIGHT / 2;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        // for older android versions...
        screenWidth = display.getWidth();
        screenHeight  = display.getHeight();
        score=0;
        height = h;
        width = w;
        lives =5;
        if(x>=screenWidth)
            userX = screenWidth - 10;
        else
            userX = x;

        if(y>=screenHeight)
            userY = screenHeight - 5;
        else
            userY = y;
        Bitmap [] image = new Bitmap[numFrames];
        spritesheet = res;
        for (int i=0; i<image.length; i++)
        {
            image[i] = Bitmap.createBitmap(spritesheet, i*width,0, width, height);

        }
        animation.setFrames(image);
        animation.setDelay(10);
        startTime = System.nanoTime();
    }

    public void update()
    {
        long elapsed = (System.nanoTime() - startTime) / 1000000;
        if(elapsed>100)
        {
            score++;
            startTime = System.nanoTime();
        }
        animation.update();
        if (x < userX)
        {
            x += (userX-x)/12;

        }
        if(x > userX)
        {
            x -=(x - userX)/12;
        }
        if (y < userY)
        {
            y +=(userY-y)/8;

        }
        if(y > userY)
        {
            y -=(y-userY)/8;
        }

        if(y> 3/4.0 *screenHeight + height) {

            y = (int)((3/4.0)*screenHeight)+height - 10;
        }
        if(x>3/4.0 *screenWidth - 20) {
            x = (int)(3/4.0 *screenWidth )+10;

        }
        System.out.println(""+screenWidth);


    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(animation.getImage(),x,y,null);
    }
    public int getScore(){return score;}
    public boolean getPlaying(){return playing;}
    public void setPlaying(boolean p) { playing = p;}
    public int getLives(){return this.lives;}
    public void setLives(int lives){
        this.lives+= lives;
    }
    public void decLives(){
        if(this.lives>0){
            this.lives-=1;
        }

    }
    public void setScore(int score){
        this.score+=score;
        if(this.score<0)
            this.score=0;
    }
    public void setUserX(float x){this.userX =x;}
    public void setUserY(float y){this.userY =y;}
    public void resetScore(){score =0;}
}
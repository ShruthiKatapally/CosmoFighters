package com.asmart.gameplay;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.Display;

/**
 * Created by Manish kumar on 11/3/2015.
 */
public class GamePlayer extends GameObject {
    private int score;
    private boolean playing;
    private Animation animation = new Animation();
    private long startTime;
    private int lives;
    private float userX;
    private float userY;
    private Bitmap spritesheet;
    private Display disp;

    public GamePlayer(Bitmap res, int w, int h, int numFrames){


        x = 100;
        y= GamePanel.HEIGHT / 2;

        score=0;
        height = h;
        width = w;
        lives =5;
        userX = x;
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
<<<<<<< HEAD
        if(up){
           dy = (int)(dya -= 1.1);
        }
        else {
            dy = (int) (dya += 1.1);
        }
        if(dy>14)
        {
            dy =14;
        }
        if(dy<14)
=======
        if (x<userX)
        {
            x+= (userX-x)/8;

        }
        if(x>userX)
>>>>>>> 56c76dad03abb5c099f4acf446c25d9028652fe5
        {
            x-=(x+3 - userX)/8;
        }
<<<<<<< HEAD
        y+= dy*2;


        if( y < 0 ) {
            y = 0;
        }

        dy =0;
=======
        if (y<userY)
        {
            y+=Math.abs(userY-y)/8;

        }
        if(y>userY)
        {
            y-=(y+6-userY)/8;
        }

>>>>>>> 56c76dad03abb5c099f4acf446c25d9028652fe5
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
        else{
            // call function to exit this activity and go to high scores activity.
        }
    }
    public void setUserX(float x){this.userX =x;}
    public void setUserY(float y){this.userY =y;}
    public void resetScore(){score =0;}
}

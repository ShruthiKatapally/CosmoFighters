package com.asmart.gameplay;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Manish kumar on 11/3/2015.
 */
public class GamePlayer extends GameObject {
    private int score;
    private double dya;
    private boolean up;
    private boolean playing;
    private Animation animation = new Animation();
    private long startTime;
    private Bitmap spritesheet;

    public GamePlayer(Bitmap res, int w, int h, int numFrames){
        x = 100;
        y= GamePanel.HEIGHT / 2;
        dy=0;
        score=0;
        height = h;
        width = w;

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

    public void setUp(boolean b){
        up = b;
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
        if(up){
            dy = (int)(dya -= 1.1);
        }
        else
            dy = (int)(dya+=1.1);
        if(dy>14)
        {dy =14;}
        if(dy<14)
        {
            dy-=14;
        }
        y+= dy*2;
        dy =0;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(animation.getImage(),x,y,null);
    }
    public int getScore(){return score;}
    public boolean getPlaying(){return playing;}
    public void setPlaying(boolean p) { playing = p;}
    public void resetDya(){dya=0;}
    public void resetScore(){score =0;}
}

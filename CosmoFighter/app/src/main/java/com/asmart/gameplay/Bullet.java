package com.asmart.gameplay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by Shruthi on 11/7/2015.
 */
public class Bullet extends GameObject {
    public Context context;
    private int width;
    private int height;

    private int bulletspeed;
    private Random rand = new Random();

    private Bitmap spritesheet;
    private Animation animation = new Animation();

    private Bullet(Context context)

    {
        this.context = context;
    }


    public Bullet(Bitmap res, int x, int y, int w, int h, int numFrames) {
        super.x = x;
        super.y = y;
        this.width = w;
        this.height = h;
        Bitmap[] image = new Bitmap[numFrames];
        spritesheet = res;
        bulletspeed = 50;

        for(int i = 0; i < image.length; i++)
        {
            image[i] = Bitmap.createBitmap(spritesheet,0,i*height,width,height);
        }

        animation.setFrames(image);
        animation.setDelay(1000 - bulletspeed);
    }

    public void draw(Canvas canvas) {
        try{canvas.drawBitmap(animation.getImage(),x,y,null);}
        catch(Exception e) {}
    }


    public void update() {
        x+=bulletspeed;
           animation.update();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth()
    {
        return width -10;

    }


}
package com.asmart.gameplay;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by Shruthi on 11/6/2015.
 */
public class Ammo extends GameObject {
    private int ammospeed;
    private int score;
    private Bitmap spritesheet;
    private Random rand = new Random();
    private Animation animation = new Animation();

    public Ammo(Bitmap res,int x,int y,int w, int h, int s, int numFrames)
    {
        super.x = x;
        super.y = y;
        width = w;
        height = h;
        score = s;
        // what speed a Ammo should come in.
        ammospeed = 15;

        Bitmap[] image = new Bitmap[numFrames];

        spritesheet = res;

        for(int i = 0; i < image.length; i++)
        {
            image[i] = Bitmap.createBitmap(spritesheet,0,i*height,width,height);
        }

        animation.setFrames(image);
        animation.setDelay(100-ammospeed);
    }

    public void update()
    {
        x-=ammospeed;
        animation.update();
    }

    public void draw(Canvas canvas)
    {
        try{canvas.drawBitmap(animation.getImage(),x,y,null);}
        catch(Exception e) {}
    }

    @Override
    public int getWidth()
    {
        return width -10;

    }

}

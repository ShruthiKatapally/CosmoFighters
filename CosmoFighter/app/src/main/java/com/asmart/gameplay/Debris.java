package com.asmart.gameplay;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

public class Debris extends GameObject
{
    private int speed;
    private int score;
    private Bitmap spritesheet;
    private Random rand = new Random();
    private Animation animation = new Animation();

    public Debris(Bitmap res,int x,int y,int w, int h, int s, int numFrames)
    {
        super.x = x;
        super.y = y;
        width = w;
        height = h;
        score = s;
        // what speed a Debris should come in.
        speed = 7 + (int)(rand.nextDouble()*score/30);

        if(speed > 40)
            speed = 40;
        Bitmap[] image = new Bitmap[numFrames];

        spritesheet = res;

        for(int i = 0; i < image.length; i++)
        {
            image[i] = Bitmap.createBitmap(spritesheet,0,i*height,width,height);
        }

        animation.setFrames(image);
        animation.setDelay(100-speed);
    }

    public void update()
    {
        x-=speed;
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
package com.asmart.gameplay;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.asmart.cosmofighter.R;

public class Coins extends GameObject{
    private Bitmap spritesheet;
    //private Random rand = new Random();
    private Animation animation = new Animation();
    private int speed;
    private Context context;
    public Coins(Context context,Bitmap res,int x,int y,int w, int h, int numFrames) {
        super.x = x;
        super.y = y;
        width = w;
        height = h;
        this.context = context;
        SharedPreferences settings =context.getSharedPreferences(context.getString(R.string.APP_PREFERENCES), 0);
        //int packageNum = settings.getInt(this.context.getString(R.string.PACKAGE), 1);
        int levelNum = settings.getInt(this.context.getString(R.string.LEVEL), 1);
        if(levelNum==1)
        {
            speed = 10;
        }
        if(levelNum==2)
        {
            speed = 13;
        }
        if(levelNum==3)
        {
            speed = 15;
        }

        Bitmap[] image = new Bitmap[numFrames];

        spritesheet = res;

        for (int i = 0; i < image.length; i++) {
            image[i] = Bitmap.createBitmap(spritesheet, 0, i * height, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(100 - speed);
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


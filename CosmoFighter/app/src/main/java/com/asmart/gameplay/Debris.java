package com.asmart.gameplay;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.asmart.cosmofighter.R;

import java.util.Random;

public class Debris extends GameObject
{
    private int speed;
    private int score;
    private Bitmap spritesheet;
    private Random rand = new Random();
    private Animation animation = new Animation();
    private Context context;
    private int baseSpeed;
    private int packNum;
    public Debris(Context context,Bitmap res,int x,int y,int w, int h, int s, int numFrames)
    {
        super.x = x;
        super.y = y;
        width = w;
        height = h;
        score = s;
        baseSpeed = 5;
        this.context = context;
        // what speed a Debris should come in.
        SharedPreferences settings =context.getSharedPreferences(context.getString(R.string.APP_PREFERENCES), 0);
        packNum = settings.getInt(this.context.getString(R.string.PACKAGE), 1);
        int levelNum = settings.getInt(this.context.getString(R.string.LEVEL), 1);
        if(levelNum==1)
        {  //base speed = 6
            baseSpeed = 6;
        }
        if(levelNum==2)
        {
            baseSpeed = 10;
        }
        if(levelNum==3)
        {
            baseSpeed =15 ;
        }
        /*speed = baseSpeed + (int)(rand.nextDouble()*score/30);

        if(speed > 40+baseSpeed)
            speed = 40 + baseSpeed; */
        speed =baseSpeed;
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
        if(packNum==1) {
            x -= speed;
        }
        if(packNum==2)
        {
            x-=speed;
            y+= speed;
        }
        if(packNum==3)
        {

            y+=speed;
        }

        animation.update();
    }

    public void draw(Canvas canvas)
    {
        try{canvas.drawBitmap(animation.getImage(),x,y,null);}
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getWidth()
    {
        return width -10;

    }
}
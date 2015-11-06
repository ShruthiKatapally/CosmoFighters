package com.asmart.gameplay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Shruthi on 11/5/2015.
 */
public class Collision extends GameObject {
    public Context context;
    private int x;
    private static Collision col;
    private int y;
    private int row;
    private int width;
    private int height;

    private Bitmap spritesheet;
    private Animation animation = new Animation();

    private Collision(Context context)

    {
        this.context = context;
    }

    //Returns the instance of Collision class when needed
    public static Collision getInstance(Context context) {
        if(col == null) {
            col = new Collision(context);
        }
        return col;
    }


    public Collision(Bitmap res,int x,int y,int w,int h,int numFrames)
    {
        this.x=x;
        this.y=y;
        this.width=w;
        this.height=h;
        Bitmap[] image = new Bitmap[numFrames];
        spritesheet = res;

        for(int i=0;i<image.length;i++)
        {
            if(i%5==0&&i>0)
            {
                row++;
            }
            image[i] = Bitmap.createBitmap(spritesheet,(i-(5*row))*width,row*height,width,height);

        }

        animation.setFrames(image);
        animation.setDelay(10);
    }

    public void draw(Canvas canvas)
    {
        if(!animation.isPlayedOnce())
        {
            canvas.drawBitmap(animation.getImage(),x,y,null);
        }
    }

    public void update()
    {
        if(!animation.isPlayedOnce())
        {
            animation.update();
        }
    }

    public int getHeight()
    {
        return height;
    }

}

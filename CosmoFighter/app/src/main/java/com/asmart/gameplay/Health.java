package com.asmart.gameplay;

import android.graphics.Bitmap;
import android.graphics.Canvas;
/**
 * Created by Manish kumar on 11/4/2015.
 */
public class Health extends GameObject {
    private Bitmap spritesheet;
    //private Random rand = new Random();
    private Animation animation = new Animation();
    private int speed;
    public Health(Bitmap res,int x,int y,int w, int h, int numFrames) {
        super.x = x;
        super.y = y;
        width = w;
        height = h;
        speed = 30;
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

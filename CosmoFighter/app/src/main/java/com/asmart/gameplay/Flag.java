package com.asmart.gameplay;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Flag extends GameObject {

    private Bitmap spritesheet;
    private int speed;
    private Animation animation = new Animation();
    private Context context;
    public Flag(Context context,Bitmap res,int x,int y,int w, int h,int numFrames){
        speed = 0;              // flag is located at fixed position
        this.x = x;
        this.y = y;
        width  = w;
        height = h;
        spritesheet = res;
        this.context = context;
        Bitmap[] image = new Bitmap[numFrames];

        for(int i = 0; i < image.length; i++)
        {
            image[i] = Bitmap.createBitmap(spritesheet,0,i*height,width,height);
        }

        animation.setFrames(image);
        animation.setDelay(5-speed);
    }

    public void update(){
       animation.update();
    }

    public void draw(Canvas canvas)
    {
        try{

                canvas.drawBitmap(animation.getImage(),x,y,null);

        }
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

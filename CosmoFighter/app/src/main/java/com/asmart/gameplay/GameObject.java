package com.asmart.gameplay;
import android.graphics.Rect;

/**
 * Abstract class for game objects...
 * All the objects present on the screen would extend the GameObject class
 */
public abstract class GameObject {
   // object coordinates
    protected int x;
    protected int y;

    protected int dx;
    protected int dy;
    // object dimensions

    protected int height;
    protected int width;

    // getters and setter methods for the instance variables
    public void setX(int x){this.x = x;}
    public void setY(int y){this.y = y;}
    public void setHeight(int height){this.height = height;}
    public void setWidth(int width){this.width = width;}

    public int getX(){return this.x;}
    public int getY(){return this.y;}
    public int getHeight(){return this.height;}
    public int getWidth(){return this.width;}
    public Rect getRect(){return new Rect(x,y,x+width, y+height);}

}

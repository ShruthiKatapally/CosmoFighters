package com.asmart.gameplay;
import android.graphics.Bitmap;
/*
* Animation class that contains the frames information
* */

public class Animation {
    private Bitmap [] frames;
    private int curFrame;
    private long startTime;
    private long delay;
    private boolean playedOnce;

    public void setFrames(Bitmap[] frames){

        this.frames = frames;
        curFrame = 0;
        startTime = System.nanoTime();
    }
    public void setDelay(long delay){this.delay = delay;}
    public void setCurFrame(int frame){this.curFrame = frame;}
    public void update(){
        long timeElapsed = (System.nanoTime() - startTime)/1000000;
        if(timeElapsed > delay){
            curFrame++;
            startTime = System.nanoTime();
        }
        if(curFrame == frames.length)
        {
            curFrame=0;
            playedOnce=true;
        }
    }
    public Bitmap getImage(){return frames[curFrame];}
    public int getCurFrame(){return this.curFrame;}
    public boolean isPlayedOnce(){return playedOnce;}
}

package com.asmart.gameplay;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class GameBackground {
    private Bitmap img;
    private int x, y, dx;

    public GameBackground(Bitmap res) {
        img = res;
        dx = GamePanel.MOVESPEED;
    }

    public void update() {
        x+= dx;
        if(x <- GamePanel.WIDTH) {
            x = 0;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(img, x, y, null);
        if(x < 0){
            canvas.drawBitmap(img, x + GamePanel.WIDTH, y, null);
        }
    }


}

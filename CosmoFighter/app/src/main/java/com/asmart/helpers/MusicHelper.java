package com.asmart.helpers;

import android.content.Context;
import android.media.MediaPlayer;
import com.asmart.cosmofighter.R;


public class MusicHelper {

    private static MusicHelper mh;
    public MediaPlayer bgm;
    public MediaPlayer coinMusic;
    public MediaPlayer explosionMusic;
    public MediaPlayer ammoMusic;
    public MediaPlayer healthMusic;
    public Context context;

    private MusicHelper(Context context)
    {
        this.context = context;
    }

    //Returns the instance of MusicHelper class when needed
    public static MusicHelper getInstance(Context context) {
        if(mh == null) {
            mh = new MusicHelper(context);
        }
        return mh;
    }

    //Starts playing the background music
    public void startMusic() {
        if(bgm==null) {
            bgm = MediaPlayer.create(context, R.raw.backmusic);
            bgm.setLooping(true);
            bgm.start();
        }
    }

    //Stops playing the background music
    public void stopMusic() {
        if(bgm!=null) {
            bgm.stop();
            bgm.release();
            bgm = null;
        }
    }

    //Pauses the background music
    public void pauseMusic() {
        if(bgm!=null) {
            bgm.pause();
        }
    }

    //Play the music when coin is collected
    public void playCoinMusic() {
        if(coinMusic == null) {
            coinMusic = MediaPlayer.create(context, R.raw.coin);
        }
        coinMusic.start();
    }

    //Play the explosion music
    public void playExplosionMusic() {
        if(explosionMusic == null) {
            explosionMusic = MediaPlayer.create(context, R.raw.explosion);
        }
        explosionMusic.start();
    }

    //Play the music for ammo
    public void playAmmoMusic() {
        if(ammoMusic == null) {
            ammoMusic = MediaPlayer.create(context, R.raw.bullet);
        }
        ammoMusic.start();
    }

    //Play the music for health
    public void playHealthMusic() {
        if(healthMusic == null) {
            healthMusic = MediaPlayer.create(context, R.raw.health);
        }
        healthMusic.start();
    }
}
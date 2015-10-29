package com.asmart.helpers;

import android.content.Context;
import android.media.MediaPlayer;
import com.asmart.cosmofighter.R;


public class MusicHelper {

    public MediaPlayer bgm;
    public Context context;

    public MusicHelper(Context context) {
        this.context = context;
    }

    public void addbackgroundmusic() {
        if(bgm==null) {
            bgm = MediaPlayer.create(context, R.raw.backmusic);
            bgm.setLooping(true);
            bgm.start();
        }
    }

    public void removebackgroundmusic() {
        if(bgm!=null) {
            bgm.stop();
            bgm.release();
            bgm = null;
        }
    }

}
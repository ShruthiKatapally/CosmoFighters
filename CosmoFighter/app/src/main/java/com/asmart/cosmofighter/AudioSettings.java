package com.asmart.cosmofighter;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.asmart.helpers.InitialSetupHelper;

public class AudioSettings extends AppCompatActivity {

    public static final String MUSICON = "isMusicOn";
    public static final String SOUNDON = "isSoundOn";
    public static final String FIRSTRUN = "isFirstRun";
    public MediaPlayer bgm;
    public Switch musicswitch = null;
    public Switch audioswitch = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_settings);

        musicswitch = (Switch) findViewById(R.id.music_switch);
        audioswitch = (Switch) findViewById(R.id.audio_switch);

        //listeners for switches
        musicswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // adding background music
                    addbackgroundmusic();
                } else {
                    //remove background music
                    removebackgroundmusic();
                }
            }
        });
    }
    public void addbackgroundmusic() {
        bgm = MediaPlayer.create(AudioSettings.this,R.raw.backmusic);
        bgm.start();
    }

    public void removebackgroundmusic() {
         if(bgm!=null) {
             bgm.stop();
             bgm.release();
             bgm = null;
         }
    }
}
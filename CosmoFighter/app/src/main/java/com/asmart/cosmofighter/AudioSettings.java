package com.asmart.cosmofighter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import com.asmart.helpers.MusicHelper;

public class AudioSettings extends AppCompatActivity {

    public Switch musicswitch = null;
    public Switch audioswitch = null;
    public MusicHelper mh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_settings);
        mh = new MusicHelper(this);

        musicswitch = (Switch) findViewById(R.id.music_switch);
        audioswitch = (Switch) findViewById(R.id.audio_switch);

        //listeners for switches
        musicswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    // remove background music
                    mh.removebackgroundmusic();

                } else {
                    //adding background music
                    mh.addbackgroundmusic();
                }
            }
        });
    }
}
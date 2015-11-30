package com.asmart.cosmofighter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import com.asmart.helpers.MusicHelper;

public class AudioSettings extends AppCompatActivity {

    private MusicHelper mh;
    private SharedPreferences.Editor edit;

    public Switch musicswitch = null;
    public Switch audioswitch = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_settings);
        mh = MusicHelper.getInstance(this);

        musicswitch = (Switch) findViewById(R.id.music_switch);
        audioswitch = (Switch) findViewById(R.id.audio_switch);

        //Open the shared preferences for editing
        SharedPreferences settings = getSharedPreferences(getString(R.string.APP_PREFERENCES), 0);
        edit = settings.edit();

        //Turn the music switch on/off based on the App preferences
       /*if(settings.getBoolean(getString(R.string.MUSICON), true)) {
            musicswitch.setChecked(true);
       }
       else {
            musicswitch.setChecked(false);
       }*/

        //Turn the game sounds switch on/off based on the App preferences
        /*if(settings.getBoolean(getString(R.string.SOUNDON), true)) {
            audioswitch.setEnabled(true);
        }
        else {
            audioswitch.setEnabled(false);
        }*/

        //Stop or start music based on selection
        musicswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Stop the music if the switch is turned off and save the preferences
                if (!isChecked) {
                    mh.stopMusic();
                    edit.putBoolean(getString(R.string.MUSICON), false);
                }
                //Start the music if the switch is turned on and save the preferences
                else {
                    mh.startMusic();
                    edit.putBoolean(getString(R.string.MUSICON), true);
                }
                //Save the changes
                edit.commit();
            }
        });

        //Stop or start music based on selection
        audioswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Stop the music if the switch is turned off and save the preferences
                if (!isChecked) {
                    edit.putBoolean(getString(R.string.SOUNDON), false);
                }
                //Start the music if the switch is turned on and save the preferences
                else {
                    edit.putBoolean(getString(R.string.SOUNDON), true);
                }
                //Save the changes
                edit.commit();
            }
        });
    }

    public void selectFighter(View view) {
        Intent intent = new Intent(this, SelectFighter.class);
        intent.putExtra(getString(R.string.PREVIOUS_SCREEN), "settings");
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
        finish();
    }
}
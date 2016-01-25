package com.asmart.cosmofighter;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import com.asmart.helpers.InitialSetupHelper;
import com.asmart.helpers.MusicHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MusicHelper mh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Checks if the app is run first time and performs initial setup
        SharedPreferences settings = getSharedPreferences(getString(R.string.APP_PREFERENCES), 0);
        if (settings.getBoolean(getString(R.string.FIRSTRUN), true)) {
            InitialSetupHelper init = new InitialSetupHelper(this);
            init.setupApplication(settings);
        }

        //If the preference is to turn on music, plays the background music
        mh = MusicHelper.getInstance(this);
        if(settings.getBoolean(getString(R.string.MUSICON),true)){
            mh.startMusic();
        }

       /* long startTime = System.nanoTime();
        while ((startTime - System.nanoTime())/1000000 > -12)
        {
          //  setContentView(R.layout.activity_main);
        }
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);   */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //Opens the home screen to start playing the game
    public void startPlaying(View view) {
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
    }

    public void instructions(View view){
        Intent intent = new Intent(this,Instructions.class);
        startActivity(intent);
    }


}
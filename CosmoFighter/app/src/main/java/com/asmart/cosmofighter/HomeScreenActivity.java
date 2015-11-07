package com.asmart.cosmofighter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.asmart.helpers.MusicHelper;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }

    //Starts the select package activity
    public void playGame(View view) {
        Intent intent = new Intent(this, SelectPackageActivity.class);
        startActivity(intent);
        finish();
    }

    //Starts the view settings activity
    public void viewSettings(View view){
        Intent intent = new Intent(this, AudioSettings.class);
        startActivity(intent);
        finish();
    }

    //Starts the facebook share activity
    public void fbShare(View view){
        Intent intent = new Intent(this, FbShare.class);
        startActivity(intent);
        finish();
    }

    //Exits the game
    public void exitGame(View view){
        MusicHelper mh = MusicHelper.getInstance(this);
        mh.pauseMusic();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}

package com.asmart.cosmofighter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }

    public void playGame(View view) {
        Intent intent = new Intent(this, SelectPackageActivity.class);
        startActivity(intent);
    }

    public void viewSettings(View view){

        Intent intent = new Intent(this, AudioSettings.class);
        startActivity(intent);
    }

    public void fbShare(View view){
        Intent intent = new Intent(this, FbShare.class);
        startActivity(intent);
    }

    public void exitGame(View view){
        // code to exit the game goes in here
    }
}

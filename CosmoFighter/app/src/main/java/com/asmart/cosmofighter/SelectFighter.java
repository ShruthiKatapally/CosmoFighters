package com.asmart.cosmofighter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.asmart.helpers.DatabaseHelper;
import com.asmart.model.Packages;

import java.util.List;

public class SelectFighter extends AppCompatActivity {

    private String previousScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ImageButton img;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_fighter);

        //Get the previous screen name
        Intent intent = getIntent();
        previousScreen = intent.getStringExtra(getString(R.string.PREVIOUS_SCREEN));

        //Gets the list of all the packages
        DatabaseHelper db = DatabaseHelper.getInstance(this);
        List<Packages> packList = db.getAllPackages();

        for(Packages pack: packList) {
            if (pack.getPackageName().equals(getString(R.string.title_package1))) {
                img = (ImageButton)findViewById(R.id.imageButton);
            }
            else if (pack.getPackageName().equals(getString(R.string.title_package2))) {
                img = (ImageButton)findViewById(R.id.imageButton2);
            }
            else {
                img = (ImageButton)findViewById(R.id.imageButton3);
            }
            //Enable or disable the fighters based on isPackageUnlocked
            img.setClickable(pack.isPackageUnlocked());
        }
    }

    public void setFighter(View view) {
        String fighter = "ic_fighter0";
        SharedPreferences settings = getSharedPreferences(getString(R.string.APP_PREFERENCES), 0);
        SharedPreferences.Editor edit = settings.edit();

        switch(view.getId()) {
            case R.id.imageButton:
                fighter = "ic_fighter0";
                break;
            case R.id.imageButton2:
                fighter = "ic_fighter1";
                break;
            case R.id.imageButton3:
                fighter = "ic_fighter2";
                break;
        }

        edit.putString(getString(R.string.FIGHTER_NAME), fighter);
        edit.commit();

        //If the previous screen is settings, then start home screen activity
        if(previousScreen.equals("settings")) {
            Intent intent = new Intent(this, HomeScreenActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, GameScreenActivity.class);
            startActivity(intent);
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        if(previousScreen.equals("settings")) {
            Intent intent = new Intent(this, HomeScreenActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, LevelsActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
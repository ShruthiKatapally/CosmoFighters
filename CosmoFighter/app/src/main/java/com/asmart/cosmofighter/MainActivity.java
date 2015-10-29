package com.asmart.cosmofighter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;

import com.asmart.helpers.InitialSetupHelper;
import com.asmart.helpers.MusicHelper;

public class MainActivity extends AppCompatActivity {

    public static final String PREFERENCE = "AppPreferences";
    public static MusicHelper mh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences(PREFERENCE, 0);
        if (settings.getBoolean("isFirstRun", true)) {
            InitialSetupHelper init = new InitialSetupHelper(this);
            init.setupApplication(settings);
        }

        if(settings.getBoolean("InitialSetupHelper.MUSICON",true)){
            mh = new MusicHelper(this);
            mh.addbackgroundmusic();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }    */

    public void startPlaying(View view) {
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
    }
}
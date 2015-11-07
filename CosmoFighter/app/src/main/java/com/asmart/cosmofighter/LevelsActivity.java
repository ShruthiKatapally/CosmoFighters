package com.asmart.cosmofighter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class LevelsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        Intent intent = getIntent();
        String message = intent.getStringExtra(getString(R.string.PACKAGE_NAME));
        //Gets the name of the package selected and displays it on the screen
        TextView txt = (TextView)findViewById(R.id.textLevel);
        txt.setText(message);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, SelectPackageActivity.class);
        startActivity(intent);
        finish();
    }

    public void StartGame(View view) {
        Intent intent = new Intent(this, GameScreenActivity.class);
        startActivity(intent);
        SharedPreferences settings = getSharedPreferences(getString(R.string.APP_PREFERENCES), 0);
        SharedPreferences.Editor edit = settings.edit();
        switch (view.getId()) {
            case R.id.easy_button:
                edit.putInt(getString(R.string.LEVEL), 1);
                break;
            case R.id.medium_button:
                edit.putInt(getString(R.string.LEVEL), 2);
                break;
            case R.id.hard_button:
                edit.putInt(getString(R.string.LEVEL), 3);
                break;
        }
        edit.commit();
        finish();
    }
}
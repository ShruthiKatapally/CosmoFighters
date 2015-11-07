package com.asmart.cosmofighter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.asmart.helpers.DatabaseHelper;
import com.asmart.model.PkgLvl;
import java.util.List;

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

        //Gets the list of all the levels
        DatabaseHelper db = DatabaseHelper.getInstance(this);
        SharedPreferences settings = getSharedPreferences(getString(R.string.APP_PREFERENCES), 0);
        int currentPackage = settings.getInt(getString(R.string.PACKAGE), 1);
        List<PkgLvl> packList = db.getAllLevels(currentPackage);

        //Checks if the levels are locked or unlocked and disables the buttons if the levels are locked
        Button btn;
        for(PkgLvl pack: packList) {
            System.out.println(pack.getLevelId() + "   " + pack.isUnlocked());
            if(pack.getLevelId() == 1) {
                btn = (Button)findViewById(R.id.easy_button);
            }
            else if (pack.getLevelId() == 2) {
                btn = (Button)findViewById(R.id.medium_button);
            }
            else {
                btn = (Button)findViewById(R.id.hard_button);
            }
            btn.setClickable(pack.isUnlocked());

            //Set the count of stars based on the stars count
            /*switch(pack.getStarsCount()) {
                case 1:
                    btn.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.stars1, 0);
                    break;
                case 2:
                    btn.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.stars2, 0);
                    break;
                case 3:
                    btn.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.stars3, 0);
                    break;
            }*/
        }
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
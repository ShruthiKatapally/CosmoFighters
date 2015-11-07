package com.asmart.cosmofighter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.asmart.helpers.DatabaseHelper;
import com.asmart.model.Packages;
import java.util.List;

public class SelectPackageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_package);

        //Gets the list of all the packages
        DatabaseHelper db = DatabaseHelper.getInstance(this);
        List<Packages> packList = db.getAllPackages();

        //Checks if the packages are locked or unlocked and disables the buttons if the packages are locked
        ImageButton img;
        ImageView starsImg;

        for(Packages pack: packList) {
            //Get access to the images button and the stars image
            if(pack.getPackageName().equals(getString(R.string.title_package1))) {
                starsImg = (ImageView)findViewById(R.id.imgPack1Stars);
                img = (ImageButton)findViewById(R.id.pack1_btn);
            }
            else if (pack.getPackageName().equals(getString(R.string.title_package2))) {
                starsImg = (ImageView)findViewById(R.id.imgPack2Stars);
                img = (ImageButton)findViewById(R.id.pack2_btn);
            }
            else {
                starsImg = (ImageView)findViewById(R.id.imgPack3Stars);
                img = (ImageButton)findViewById(R.id.pack3_btn);
            }
            //Enable or disable the button based isPackageUnlocked
            img.setClickable(pack.isPackageUnlocked());

            //Set the count of stars based on the stars count
            switch(pack.getStarsCount()) {
                case 1:
                    starsImg.setImageResource(R.drawable.stars1);
                    break;
                case 2:
                    starsImg.setImageResource(R.drawable.stars2);
                    break;
                case 3:
                    starsImg.setImageResource(R.drawable.stars3);
                    break;
            }
        }
    }

    //Opens the select levels screen when a package is selected
    public void selectPackage(View view){
        String message = getString(R.string.title_package1);
        Intent intent = new Intent(this, LevelsActivity.class);
        SharedPreferences settings = getSharedPreferences(getString(R.string.APP_PREFERENCES), 0);
        SharedPreferences.Editor edit = settings.edit();
        //Package name is passed to the levels activity
        switch (view.getId()) {
            case R.id.pack1_btn:
                edit.putInt(getString(R.string.PACKAGE), 1);
                message = getString(R.string.title_package1);
                break;
            case R.id.pack2_btn:
                edit.putInt(getString(R.string.PACKAGE), 2);
                message = getString(R.string.title_package2);
                break;
            case R.id.pack3_btn:
                edit.putInt(getString(R.string.PACKAGE), 3);
                message = getString(R.string.title_package3);
        }
        edit.commit();
        intent.putExtra(getString(R.string.PACKAGE_NAME), message);
        startActivity(intent);
    }
}
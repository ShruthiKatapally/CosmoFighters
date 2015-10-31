package com.asmart.cosmofighter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

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
        for(Packages pack: packList) {
            if(!pack.isPackageUnlocked()) {
                if(pack.getPackageName().equals(getString(R.string.title_package2))) {
                    img = (ImageButton)findViewById(R.id.pack2_btn);
                }
                else {
                    img = (ImageButton)findViewById(R.id.pack3_btn);
                }
                img.setClickable(false);
            }
        }
    }

    //Opens the select levels screen when a package is selected
    public void selectPackage(View view){
        String message = getString(R.string.title_package1);
        Intent intent = new Intent(this, LevelsActivity.class);
        //package name is passed to the levels activity
        switch (view.getId()) {
            case R.id.pack1_btn:
                message = getString(R.string.title_package1);
                break;
            case R.id.pack2_btn:
                message = getString(R.string.title_package2);;
                break;
            case R.id.pack3_btn:
                message = getString(R.string.title_package3);;
        }
        intent.putExtra(getString(R.string.PACKAGE_NAME), message);
        startActivity(intent);
    }
}
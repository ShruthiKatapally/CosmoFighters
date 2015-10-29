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

        Log.i("DEBUG", "" + packList.size());
        ImageButton img;
        for(Packages pack: packList) {
            if(!pack.isPackageUnlocked()) {
                if(pack.getPackageName().equals("pack2")) {
                    img = (ImageButton)findViewById(R.id.pack2_btn);
                }
                else {
                    img = (ImageButton)findViewById(R.id.pack3_btn);
                }
                img.setClickable(false);
            }
        }
    }

    public void selectPackageActivity(View view){
        Intent intent = new Intent(this, LevelsActivity.class);
        switch (view.getId()) {
            case R.id.pack1_btn:
                // opening package 1
                break;
            case R.id.pack2_btn:
                // opening package 2
                break;
            case R.id.pack3_btn:
                //open game relevant to package 3
        }

        startActivity(intent);
    }
}

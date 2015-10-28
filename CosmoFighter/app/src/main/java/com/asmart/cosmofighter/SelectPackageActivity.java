package com.asmart.cosmofighter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;

public class SelectPackageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_package);
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

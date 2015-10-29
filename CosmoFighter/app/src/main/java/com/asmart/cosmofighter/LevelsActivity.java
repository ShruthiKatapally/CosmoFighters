package com.asmart.cosmofighter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class LevelsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        Intent intent = getIntent();
        String message = intent.getStringExtra(SelectPackageActivity.PACKAGE_NAME);

        TextView txt = (TextView)findViewById(R.id.textLevel);
        txt.setText(message);
    }
}



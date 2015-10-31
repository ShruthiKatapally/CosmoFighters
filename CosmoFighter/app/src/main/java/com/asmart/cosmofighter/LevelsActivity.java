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
        String message = intent.getStringExtra(getString(R.string.PACKAGE_NAME));

        //Gets the name of the package selected and displays it on the screen
        TextView txt = (TextView)findViewById(R.id.textLevel);
        txt.setText(message);
    }
}



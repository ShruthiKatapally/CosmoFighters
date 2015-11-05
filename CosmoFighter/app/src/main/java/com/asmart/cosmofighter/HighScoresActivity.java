package com.asmart.cosmofighter;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.asmart.helpers.DatabaseHelper;
import com.asmart.model.Player;

import java.util.List;
import java.util.Map;

public class HighScoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHelper db = DatabaseHelper.getInstance(this);
       // Map<String, Integer> players = db.getTopPlayers(5);

        setContentView(R.layout.activity_high_scores);
    }

}

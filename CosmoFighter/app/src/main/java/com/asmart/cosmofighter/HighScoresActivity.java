package com.asmart.cosmofighter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.asmart.helpers.DatabaseHelper;
import com.asmart.model.Player;

import java.util.List;
import java.util.Map;

public class HighScoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);
        //DatabaseHelper db = DatabaseHelper.getInstance(this);

        Intent intent = getIntent();
        int score = intent.getIntExtra(getString(R.string.GAME_SCORE), 0);
        TextView txt = (TextView)findViewById(R.id.scoreView);
        System.out.println("Score " + score);
        txt.setText(String.valueOf(score));
    }

    public void submit(View view){
        EditText text = (EditText)findViewById(R.id.player_name_field);
        String playerName = text.getText().toString();
        DatabaseHelper db = DatabaseHelper.getInstance(this);
        Player p = new Player();
        p.setPlayerName(playerName);
        db.addPlayer(p);
        // Also need to add score corresponding to the Player Id.
    }

}

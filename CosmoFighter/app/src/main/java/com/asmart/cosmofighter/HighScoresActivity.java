package com.asmart.cosmofighter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

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

    public void submit(View view){
        EditText text = (EditText)findViewById(R.id.player_name_field);
        String playerName = text.getText().toString();
        DatabaseHelper db = DatabaseHelper.getInstance(this);
        Player p = new Player();
        p.setPlayerName(playerName);
        db.addPlayer(p);
        // Also need to add score corresponding to the player Id.

    }
    public void startPlaying(View view) {
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
    }

}

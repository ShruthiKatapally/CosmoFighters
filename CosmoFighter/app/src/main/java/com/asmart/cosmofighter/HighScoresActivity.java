package com.asmart.cosmofighter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.asmart.helpers.DatabaseHelper;
import com.asmart.model.Player;

public class HighScoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);
        //DatabaseHelper db = DatabaseHelper.getInstance(this);

        Intent intent = getIntent();
        int score = intent.getIntExtra(getString(R.string.GAME_SCORE), 0);
        TextView txt = (TextView)findViewById(R.id.scoreView);
        txt.setText(String.valueOf(score));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
        finish();
    }

    public void addPlayer(View view){
        EditText playerView = (EditText)findViewById(R.id.player_name_field);
        TextView scoreView = (TextView)findViewById(R.id.scoreView);
        String playerName = playerView.getText().toString();
        int score = Integer.parseInt(scoreView.getText().toString());
        DatabaseHelper db = DatabaseHelper.getInstance(this);
        Player player = new Player();
        player.setPlayerName(playerName);
        player.setScore(score);
        db.addPlayer(player);

        Intent intent = new Intent(this, SelectPackageActivity.class);
        startActivity(intent);
        finish();
    }

    public void exitToHome(View view) {
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
        finish();
    }
}

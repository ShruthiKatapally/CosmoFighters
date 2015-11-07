package com.asmart.cosmofighter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.asmart.helpers.DatabaseHelper;
import com.asmart.model.Packages;
import com.asmart.model.PkgLvl;
import com.asmart.model.Player;

import java.util.List;

public class HighScoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        Intent intent = getIntent();
        int score = intent.getIntExtra(getString(R.string.GAME_SCORE), 0);
        int currentPackage = intent.getIntExtra(getString(R.string.PACKAGE), 1);
        int currentLevel = intent.getIntExtra(getString(R.string.LEVEL), 1);
        boolean isFlag = intent.getBooleanExtra(getString(R.string.FLAG_REACHED), false);
        TextView txt = (TextView)findViewById(R.id.scoreView);
        txt.setText(String.valueOf(score));

        DatabaseHelper db = DatabaseHelper.getInstance(this);

        //Update the stars count based on the score
        int stars = 0;
        if(score > 950) {
            stars = 3;
        }
        else if (score > 850) {
            stars = 2;
        }
        else if (score > 500) {
            stars = 1;
        }

        //Update the number of stars in current level
        PkgLvl level = new PkgLvl();
        level.setPackageId(currentPackage);
        level.setLevelId(currentLevel);
        level.setStarsCount(stars);
        level.setIsUnlocked(isFlag);
        db.updateLevel(level);

        //Unlock the next level/package if the current level is completed
        if(isFlag) {
            //Update the number of stars for current package
            Packages currpack = new Packages();
            currpack.setPackageId(currentPackage);
            currpack.setPackageUnlocked(true);
            currpack.setStarsCount(currentLevel);
            db.updatePackage(currpack);

            if (currentLevel == 3) {
                if(currentPackage != 3) {
                    //Unlock the next package
                    Packages pack = new Packages();
                    pack.setPackageId(currentPackage + 1);
                    pack.setPackageUnlocked(isFlag);
                    pack.setStarsCount(0);
                    db.updatePackage(pack);
                }
            }
            else {
                //Unlock the next level in current package
                PkgLvl pack = new PkgLvl();
                pack.setPackageId(currentPackage);
                pack.setLevelId(currentLevel + 1);
                pack.setIsUnlocked(isFlag);
                pack.setStarsCount(0);
                db.updateLevel(pack);
            }
        }

        //Display the high scores of the game
        /*List<String> players = db.getTopPlayers(5);
        System.out.println("Players: " + players.size());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, players);
        ListView playersList = (ListView)findViewById(R.id.player_list_view);
        playersList.setAdapter(adapter);*/
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
        finish();
    }

    public void addNewPlayer(View view) {
        EditText playerView = (EditText)findViewById(R.id.player_name_field);
        TextView scoreView = (TextView)findViewById(R.id.scoreView);
        String playerName = playerView.getText().toString();
        int score = Integer.parseInt(scoreView.getText().toString());
        DatabaseHelper db = DatabaseHelper.getInstance(this);
        Player player = new Player();
        player.setPlayerName(playerName);
        player.setScore(score);
        db.addPlayer(player);
    }

    public void exitToPackages(View view){
        addNewPlayer(view);
        Intent intent = new Intent(this, SelectPackageActivity.class);
        startActivity(intent);
        finish();
    }

    public void exitToHome(View view) {
        addNewPlayer(view);
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
        finish();
    }
}

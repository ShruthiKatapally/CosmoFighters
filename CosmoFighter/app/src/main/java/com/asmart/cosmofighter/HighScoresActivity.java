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
        int currPackStars = intent.getIntExtra(getString(R.string.PACKAGE_STARS), 1);
        int currentLevel = intent.getIntExtra(getString(R.string.LEVEL), 1);
        int currtLvlStars = intent.getIntExtra(getString(R.string.LEVEL_STARS), 1);
        boolean isFlag = intent.getBooleanExtra(getString(R.string.FLAG_REACHED), false);
        TextView txt = (TextView)findViewById(R.id.scoreView);
        txt.setText(String.valueOf(score));

        DatabaseHelper db = DatabaseHelper.getInstance(this);

        //Update the stars count based on the score
        int stars = 0;
        if(score > 1050) {
            stars = isFlag? 3 : 2;
        }
        else if (score > 950) {
            stars = 2;
        }
        else if (score > 700) {
            stars = 1;
        }

        if(currtLvlStars > stars) {
            stars = currtLvlStars;
        }

        //Update the number of stars in current level
        PkgLvl level = new PkgLvl();
        level.setPackageId(currentPackage);
        level.setLevelId(currentLevel);
        level.setStarsCount(stars);
        level.setIsUnlocked(true);
        db.updateLevel(level);

        //Unlock the next level/package if the current level is completed
        if(isFlag) {
            //Update the number of stars for current package
            Packages currpack = new Packages();
            currpack.setPackageId(currentPackage);
            currpack.setPackageUnlocked(true);
            if(currPackStars > currentLevel) {
                currpack.setStarsCount(currPackStars);
            }
            else {
                currpack.setStarsCount(currentLevel);
            }
            db.updatePackage(currpack);

            if (currentLevel == 3) {
                if(currentPackage != 3) {
                    //Unlock the next package
                    Packages pack = new Packages();
                    pack.setPackageId(currentPackage + 1);
                    pack.setPackageUnlocked(true);
                    pack.setStarsCount(0);
                    db.updatePackage(pack);
                }
            }
            else {
                //Unlock the next level in current package
                PkgLvl pack = new PkgLvl();
                pack.setPackageId(currentPackage);
                pack.setLevelId(currentLevel + 1);
                pack.setIsUnlocked(true);
                pack.setStarsCount(0);
                db.updateLevel(pack);
            }
        }

        //Display the high scores of the game
        List<String> players = db.getTopPlayers(5);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.high_scores_list_view, players);
        ListView playersList = (ListView)findViewById(R.id.player_list_view);
        playersList.setAdapter(adapter);
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
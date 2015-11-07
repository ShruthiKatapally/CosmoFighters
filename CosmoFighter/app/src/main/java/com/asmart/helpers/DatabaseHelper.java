package com.asmart.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.asmart.cosmofighter.R;
import com.asmart.model.Levels;
import com.asmart.model.Packages;
import com.asmart.model.PkgLvl;
import com.asmart.model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper dbHelper = null;

    public static final int DB_VERSION = 1;
    private Context context;

    //Player Table
    public static final String TABLE_PLAYER = "player";
    public static final String COLUMN_PLAYERID = "player_id";
    public static final String COLUMN_PLAYERNAME = "player_name";

    //Scores Table
    public static final String TABLE_SCORES = "scores";
    public static final String COLUMN_SCORE = "score";

    //Packages Table
    public static final String TABLE_PACKAGES = "packages";
    public static final String COLUMN_PACKAGEID = "package_id";
    public static final String COLUMN_PACKAGENAME = "package_name";
    public static final String COLUMN_PACKAGEUNLOCKED = "package_unlocked";
    public static final String COLUMN_STARSCOUNT = "stars_count";
    public static final String COLUMN_LEVELSUNLOCKED = "levels_unlocked";

    //Levels Table
    public static final String TABLE_LEVELS = "levels";
    public static final String COLUMN_LEVELID = "level_id";
    public static final String COLUMN_LEVELNAME = "level_name";

    //PkgLvl Table
    public static final String TABLE_PKGLVL = "pkglvl";
    public static final String COLUMN_ISUNLOCKED = "isUnlocked";

    private DatabaseHelper(Context context) {
        super(context, context.getString(R.string.DATABASE_NAME), null, DB_VERSION);
        this.context = context;
    }

    //Returns an instance of Database Helper class
    public static DatabaseHelper getInstance(Context context) {
        if(dbHelper == null) {
            dbHelper = new DatabaseHelper(context);
        }
        return dbHelper;
    }

    //Creates all the tables when the app is initialised
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PLAYER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PLAYER + "(" +
                COLUMN_PLAYERID + " INTEGER PRIMARY KEY," +
                COLUMN_PLAYERNAME + " TEXT UNIQUE NOT NULL" + ")";

        String CREATE_SCORES_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_SCORES + "(" +
                COLUMN_PLAYERID + " INTEGER NOT NULL," +
                COLUMN_SCORE + " INTEGER NOT NULL" + ")";

        String CREATE_PACKAGES_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PACKAGES + "(" +
                COLUMN_PACKAGEID + " INTEGER PRIMARY KEY," +
                COLUMN_PACKAGENAME + " TEXT NOT NULL," +
                COLUMN_PACKAGEUNLOCKED + " INTEGER NOT NULL," +
                COLUMN_STARSCOUNT + " INTEGER NOT NULL," +
                COLUMN_LEVELSUNLOCKED + " INTEGER NOT NULL" + ")";

        String CREATE_LEVEL_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_LEVELS + "(" +
                COLUMN_LEVELID + " INTEGER PRIMARY KEY," +
                COLUMN_LEVELNAME + " TEXT UNIQUE NOT NULL" + ")";

        String CREATE_PKGLVL_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PKGLVL + "(" +
                COLUMN_PACKAGEID + " INTEGER NOT NULL," +
                COLUMN_LEVELID + " INTEGER NOT NULL," +
                COLUMN_STARSCOUNT + " INTEGER NOT NULL," +
                COLUMN_ISUNLOCKED + " INTEGER NOT NULL" + ")";

        db.execSQL(CREATE_PLAYER_TABLE);
        db.execSQL(CREATE_SCORES_TABLE);
        db.execSQL(CREATE_PACKAGES_TABLE);
        db.execSQL(CREATE_LEVEL_TABLE);
        db.execSQL(CREATE_PKGLVL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**** Methods that handle package table   */

    //Adds a package to the database
    public void addPackage(Packages pack) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PACKAGEID, pack.getPackageId());
        values.put(COLUMN_PACKAGENAME, pack.getPackageName());
        values.put(COLUMN_PACKAGEUNLOCKED, pack.isPackageUnlocked() ? 1 : 0);
        values.put(COLUMN_STARSCOUNT, pack.getStarsCount());
        values.put(COLUMN_LEVELSUNLOCKED, pack.getLevelsUnlocked());

        db.insert(TABLE_PACKAGES, null, values);
        db.close();
    }

    //Gets the list of all packages from the database
    public List<Packages> getAllPackages() {
        List<Packages> packList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_PACKAGES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor csr = db.rawQuery(query, null);

        if(csr.moveToNext()) {
            do {
                Packages pack = new Packages();
                pack.setPackageId(Integer.parseInt(csr.getString(0)));
                pack.setPackageName(csr.getString(1));
                if (Integer.parseInt(csr.getString(2)) == 1) {
                    pack.setPackageUnlocked(true);
                }
                else {
                    pack.setPackageUnlocked(false);
                }
                pack.setStarsCount(Integer.parseInt(csr.getString(3)));
                pack.setLevelsUnlocked(Integer.parseInt(csr.getString(4)));

                packList.add(pack);
            }while(csr.moveToNext());
        }
        csr.close();
        db.close();

        return packList;
    }

    /**** Methods that handle level table   */

    //Adds a level to the database
    public void addLevel(Levels level) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LEVELID, level.getLevelId());
        values.put(COLUMN_LEVELNAME, level.getLevelName());

        db.insert(TABLE_LEVELS, null, values);
        db.close();
    }

    /**** Methods that handle PkgLvl table   */

    //Adds a level in package to the database
    public void addPkgLvl(PkgLvl lvl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PACKAGEID, lvl.getPackageId());
        values.put(COLUMN_LEVELID, lvl.getLevelId());
        values.put(COLUMN_STARSCOUNT, lvl.getStarsCount());
        values.put(COLUMN_ISUNLOCKED, lvl.isUnlocked()?1:0);

        db.insert(TABLE_PKGLVL, null, values);
        db.close();
    }

    //Gets the list of all levels from the database
    public List<PkgLvl> getAllLevels(int packageNum) {
        List<PkgLvl> packList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_PKGLVL + " WHERE " + COLUMN_PACKAGEID + " = " + packageNum;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor csr = db.rawQuery(query, null);

        if(csr.moveToNext()) {
            do {
                PkgLvl pack = new PkgLvl();
                pack.setPackageId(Integer.parseInt(csr.getString(0)));
                pack.setLevelId(Integer.parseInt(csr.getString(1)));
                pack.setStarsCount(Integer.parseInt(csr.getString(2)));
                if (Integer.parseInt(csr.getString(3)) == 1) {
                    pack.setIsUnlocked(true);
                }
                else {
                    pack.setIsUnlocked(false);
                }

                packList.add(pack);
            }while(csr.moveToNext());
        }
        csr.close();
        db.close();

        return packList;
    }

    /**** Methods that handle players and score table   */

    // Adds a new player if the player does not exist in player table
    public void addPlayer(Player player){
        String query = "SELECT " + COLUMN_PLAYERID + " FROM " + TABLE_PLAYER + " WHERE " + COLUMN_PLAYERNAME + " =  \"" + player.getPlayerName().trim() + "\"";
        SharedPreferences settings = context.getSharedPreferences(context.getString(R.string.APP_PREFERENCES), 0);
        int player_id = settings.getInt(context.getString(R.string.PLAYER_ID), 1) ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor csr = db.rawQuery(query, null);
        if(csr.getColumnCount() == 0) {
            csr.close();
            //Add a new player record
            ContentValues values = new ContentValues();
            values.put(COLUMN_PLAYERID, player_id);
            values.put(COLUMN_PLAYERNAME, player.getPlayerName());
            db.insert(TABLE_PLAYER, null, values);

            SharedPreferences.Editor edit = settings.edit();
            edit.putInt(context.getString(R.string.PLAYER_ID), player_id + 1);
            edit.commit();
        }
        else {
            //Get the player id
            csr = db.rawQuery(query, null);
            if(csr.moveToNext()) {
                player_id = Integer.parseInt(csr.getString(0));
            }
            csr.close();
        }

        //Insert the new high score for a player
        ContentValues values = new ContentValues();
        values.put(COLUMN_PLAYERID, player_id);
        values.put(COLUMN_SCORE, player.getScore());
        db.insert(TABLE_SCORES, null, values);

        db.close();
    }

    //Gets the list of players with high scores
    public List<String> getTopPlayers(int num)
    {
        List<String> players = new ArrayList<>();
        String query = "SELECT P."+ COLUMN_PLAYERNAME +", S."+COLUMN_SCORE+" FROM " + TABLE_PLAYER + " P, " + TABLE_SCORES +" S WHERE P." + COLUMN_PLAYERID + " = S." + COLUMN_PLAYERID + " ORDER BY S." + COLUMN_SCORE + " DESC LIMIT "+Integer.toString(num);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor csr = db.rawQuery(query, null);
        if(csr.moveToNext()) {
            do {
                String player = String.format("%13s \t %8s", csr.getString(0), csr.getString(1));
                System.out.println(player);
                /*Player player = new Player();
                player.setPlayerName(csr.getString(0));
                player.setScore(Integer.parseInt(csr.getString(1)));*/
                players.add(player);
            }while(csr.moveToNext());
        }
        csr.close();
        db.close();
        return players;
    }
}
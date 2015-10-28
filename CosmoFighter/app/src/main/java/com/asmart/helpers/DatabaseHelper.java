package com.asmart.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.asmart.model.Packages;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "cosmofighter";

    public static final int DB_VERSION = 1;

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

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PLAYER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PLAYER + "(" +
                COLUMN_PLAYERID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
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

        db.execSQL(CREATE_PLAYER_TABLE);
        db.execSQL(CREATE_SCORES_TABLE);
        db.execSQL(CREATE_PACKAGES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addPackage(Packages pack) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PACKAGEID, pack.getPackageId());
        values.put(COLUMN_PACKAGENAME, pack.getPackageName());
        values.put(COLUMN_PACKAGEUNLOCKED, pack.isPackageUnlocked()?1:0);
        values.put(COLUMN_STARSCOUNT, pack.getStarsCount());
        values.put(COLUMN_LEVELSUNLOCKED, pack.getLevelsUnlocked());

        db.insert(TABLE_PACKAGES, null, values);
        db.close();
    }

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

        return packList;
    }
}
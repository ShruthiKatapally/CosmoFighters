package com.asmart.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.asmart.model.Levels;
import com.asmart.model.Packages;
import com.asmart.model.PkgLvl;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper dbHelper = null;
    private Context context;

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

    //Levels Table
    public static final String TABLE_LEVELS = "levels";
    public static final String COLUMN_LEVELID = "level_id";
    public static final String COLUMN_LEVELNAME = "level_name";

    //PkgLvl Table
    public static final String TABLE_PKGLVL = "pkglvl";
    public static final String COLUMN_ISUNLOCKED = "isUnlocked";

    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    public static DatabaseHelper getInstance(Context context) {
        if(dbHelper == null) {
            dbHelper = new DatabaseHelper(context);
        }
        return dbHelper;
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

    /**** Methods that handle level table   */
    public void addLevel(Levels level) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LEVELID, level.getLevelId());
        values.put(COLUMN_LEVELNAME, level.getLevelName());

        db.insert(TABLE_LEVELS, null, values);
        db.close();
    }

    /**** Methods that handle PkgLvl table   */
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
}
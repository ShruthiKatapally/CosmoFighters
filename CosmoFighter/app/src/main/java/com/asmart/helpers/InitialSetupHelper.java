package com.asmart.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.asmart.model.Levels;
import com.asmart.model.Packages;
import com.asmart.model.PkgLvl;

public class InitialSetupHelper {

    public static final String MUSICON = "isMusicOn";
    public static final String SOUNDON = "isSoundOn";
    public static final String FIRSTRUN = "isFirstRun";

    private Context context;

    public InitialSetupHelper(Context context) {
        this.context = context;
    }

    public void setupApplication(SharedPreferences settings) {
        SharedPreferences.Editor edit = settings.edit();
        edit.putBoolean(MUSICON, true);
        edit.putBoolean(SOUNDON, true);
        edit.putBoolean(FIRSTRUN, false);
        edit.commit();

        addDefaultPackages();
        addDefaultLevels();
        addDefaultsLevelsinpackages();
    }

    //Inserts the packages and their corresponding values to the database in initial run
    public void addDefaultPackages() {
        Packages pack1 = new Packages(1, "pack1", true, 0, 0);
        Packages pack2 = new Packages(2, "pack2", false, 0, 0);
        Packages pack3 = new Packages(3, "pack3", false, 0, 0);

        DatabaseHelper db = DatabaseHelper.getInstance(context);
        db.addPackage(pack1);
        db.addPackage(pack2);
        db.addPackage(pack3);
    }

    //Inserts the levels and their corresponding values to the database in initial run
    public void addDefaultLevels() {
        Levels lvl1 = new Levels(1, "Easy");
        Levels lvl2 = new Levels(2, "Medium");
        Levels lvl3 = new Levels(3, "Hard");

        DatabaseHelper db = DatabaseHelper.getInstance(context);
        db.addLevel(lvl1);
        db.addLevel(lvl2);
        db.addLevel(lvl3);
    }

    //Inserts the level corresponding to a package to database in initial run
    public void addDefaultsLevelsinpackages() {
        PkgLvl pkg = null;
        DatabaseHelper db = DatabaseHelper.getInstance(context);
        for(int i = 1; i <= 3; i++ ) {
            for(int j = 1; j <=3; j++) {
                if (j == 1) {
                    pkg = new PkgLvl(i, j, 0, true);
                }
                else {
                    pkg = new PkgLvl(i, j, 0, false);
                }
                db.addPkgLvl(pkg);
            }
        }
    }
}

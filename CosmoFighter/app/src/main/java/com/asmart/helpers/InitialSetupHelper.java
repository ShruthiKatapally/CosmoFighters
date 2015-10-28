package com.asmart.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.asmart.model.Packages;

public class InitialSetupHelper {

    public static final String MUSICON = "isMusicOn";
    public static final String SOUNDON = "isSoundOn";
    public static final String FIRSTRUN = "isFirstRun";

    private Context context;

    private SharedPreferences.Editor edit;

    public InitialSetupHelper(Context context) {
        this.context = context;
    }

    public void setupApplication(SharedPreferences settings) {
        edit = settings.edit();
        edit.putBoolean(MUSICON, true);
        edit.putBoolean(SOUNDON, true);
        edit.putBoolean(FIRSTRUN, false);
        edit.commit();

        addDefaultPackages();
    }

    //Inserts the packages and their corresponding values to the database in initial run
    public void addDefaultPackages() {
        Packages pack1 = new Packages("Package1", true, 0, 0);
        Packages pack2 = new Packages("Package2", false, 0, 0);
        Packages pack3 = new Packages("Package3", false, 0, 0);

        DatabaseHelper db = new DatabaseHelper(context);
        db.addPackage(pack1);
        db.addPackage(pack2);
        db.addPackage(pack3);
    }
}

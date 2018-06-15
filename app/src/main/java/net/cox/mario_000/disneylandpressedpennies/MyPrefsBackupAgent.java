package net.cox.mario_000.disneylandpressedpennies;

import android.app.backup.BackupAgentHelper;

/**
 * Created by mario_000 on 4/26/2017.
 */

public class MyPrefsBackupAgent extends BackupAgentHelper {
    //static final String PREFS = "Coin_App";
    static final String PREFS_BACKUP = "prefs";

    @Override
    public void onCreate() {
        //SharedPreferencesBackupHelper helper = new SharedPreferencesBackupHelper(this, PREFS_NAME);
        //addHelper(PREFS_BACKUP, helper);
    }
}

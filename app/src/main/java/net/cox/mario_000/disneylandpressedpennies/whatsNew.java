package net.cox.mario_000.disneylandpressedpennies;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by mario_000 on 3/26/2017.
 */

public class whatsNew
{
    // Strings
    private static final String LOG_TAG = "WhatsNewScreen";
    private static final String LAST_VERSION_CODE_KEY = "last_version_code";

    // References
    private Activity mActivity;

    // Constructor memorize the calling Activity ("context")
    public whatsNew( Activity context )
    {
        mActivity = context;
    }

    // Show the dialog only if not already shown for this version of the application
    public void show()
    {
        try
        {
            // Get the versionCode of the Package, which must be different (incremented) in each release on the market in the AndroidManifest.xml
            final PackageInfo packageInfo = mActivity.getPackageManager().getPackageInfo( mActivity.getPackageName(), PackageManager.GET_ACTIVITIES );

            final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences( mActivity );
            final long lastVersionCode = prefs.getLong( LAST_VERSION_CODE_KEY, 0 );

            if ( packageInfo.versionCode != lastVersionCode )
            {
                Log.i( LOG_TAG, "versionCode " + packageInfo.versionCode + "is different from the last known version " + lastVersionCode );

                final String title = mActivity.getString( R.string.app_name ) + " v" + packageInfo.versionName;

                final String message = mActivity.getString( R.string.whatsnew );

                // Show the News since last version
                AlertDialog.Builder builder = new AlertDialog.Builder( mActivity )
                        .setTitle( title )
                        .setMessage( message )
                        .setPositiveButton( android.R.string.ok, new Dialog.OnClickListener()
                        {

                            public void onClick( DialogInterface dialogInterface, int i )
                            {
                                // Mark this version as read
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putLong( LAST_VERSION_CODE_KEY, packageInfo.versionCode );
                                editor.apply();
                                dialogInterface.dismiss();
                            }
                        } );
                builder.create().show();
            } else
            {
                Log.i( LOG_TAG, "versionCode " + packageInfo.versionCode + "is already known" );
            }
        } catch ( PackageManager.NameNotFoundException e )
        {
            e.printStackTrace();
        }
    }
}
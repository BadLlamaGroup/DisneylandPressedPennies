package net.cox.mario_000.disneylandpressedpennies;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.jinatonic.confetti.ConfettiManager;
import com.github.jinatonic.confetti.ConfettiSource;
import com.github.jinatonic.confetti.ConfettoGenerator;
import com.github.jinatonic.confetti.confetto.BitmapConfetto;
import com.github.jinatonic.confetti.confetto.Confetto;

import java.util.Random;

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

    public void show( boolean forceShow )
    {
        try {
            final PackageInfo packageInfo = mActivity.getPackageManager().getPackageInfo( mActivity.getPackageName(), PackageManager.GET_ACTIVITIES );
            final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences( mActivity );
            final long lastVersionCode = prefs.getLong( LAST_VERSION_CODE_KEY, 0 );

            if ( packageInfo.versionCode != lastVersionCode || forceShow ) {
                LayoutInflater inflater = mActivity.getLayoutInflater();
                View view = inflater.inflate( R.layout.whats_new_title, null );

                TextView versionName = view.findViewById( R.id.version_txt );
                versionName.setText( "Version: " + packageInfo.versionName );

                // Show the News since last version
                AlertDialog.Builder builder = new AlertDialog.Builder( mActivity, R.style.WhatsNewDialog )
                        .setView( view )
                        .setPositiveButton( android.R.string.ok, new Dialog.OnClickListener()
                        {
                            public void onClick( DialogInterface dialogInterface, int i )
                            {
                                // Mark this version as read
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putLong( LAST_VERSION_CODE_KEY, packageInfo.versionCode );
                                editor.apply();
                                dialogInterface.dismiss();

                                DisplayMetrics displayMetrics = new DisplayMetrics();
                                mActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                                int width = displayMetrics.widthPixels;

                                final ConfettoGenerator characterGenerator = new ConfettoGenerator()
                                {
                                    @Override
                                    public Confetto generateConfetto( Random random )
                                    {
                                        final Bitmap bitmap = BitmapFactory.decodeResource( mActivity.getResources(), R.drawable.frontline_construction );
                                        return new BitmapConfetto( Bitmap.createScaledBitmap(bitmap, 200, 300, false) );
                                    }
                                };
                                final ConfettiSource confettiSource = new ConfettiSource( 0, -200, width, -200 );
                                ViewGroup viewGroup = mActivity.findViewById(android.R.id.content);
                                new ConfettiManager( mActivity, characterGenerator, confettiSource, viewGroup )
                                        .setEmissionDuration( 5000 )
                                        .setEmissionRate( 1 )
                                        .setVelocityX( 0, 50 )
                                        .setVelocityY( 500 )
                                        .setRotationalVelocity( 180, 180 )
                                        .setTouchEnabled( true )
                                        .animate();

                                final ConfettoGenerator extraCharacter = new ConfettoGenerator()
                                {
                                    @Override
                                    public Confetto generateConfetto( Random random )
                                    {
                                        final Bitmap bitmap = BitmapFactory.decodeResource( mActivity.getResources(), R.drawable.frontline_farmer );
                                        return new BitmapConfetto( Bitmap.createScaledBitmap(bitmap, 200, 300, false) );
                                    }
                                };

                                new ConfettiManager( mActivity, extraCharacter, confettiSource, viewGroup )
                                        .setEmissionDuration( 5000 )
                                        .setEmissionRate( 1 )
                                        .setVelocityX( 0, 50 )
                                        .setVelocityY( 500 )
                                        .setRotationalVelocity( 180, 180 )
                                        .setTouchEnabled( true )
                                        .animate();

                                final ConfettoGenerator extraCharacter2 = new ConfettoGenerator()
                                {
                                    @Override
                                    public Confetto generateConfetto( Random random )
                                    {
                                        final Bitmap bitmap = BitmapFactory.decodeResource( mActivity.getResources(), R.drawable.frontline_fireman );
                                        return new BitmapConfetto( Bitmap.createScaledBitmap(bitmap, 200, 300, false) );
                                    }
                                };

                                new ConfettiManager( mActivity, extraCharacter2, confettiSource, viewGroup )
                                        .setEmissionDuration( 5000 )
                                        .setEmissionRate( 1 )
                                        .setVelocityX( 0, 50 )
                                        .setVelocityY( 500 )
                                        .setRotationalVelocity( 180, 180 )
                                        .setTouchEnabled( true )
                                        .animate();

                                final ConfettoGenerator extraCharacter3 = new ConfettoGenerator()
                                {
                                    @Override
                                    public Confetto generateConfetto( Random random )
                                    {
                                        final Bitmap bitmap = BitmapFactory.decodeResource( mActivity.getResources(), R.drawable.frontline_police );
                                        return new BitmapConfetto( Bitmap.createScaledBitmap(bitmap, 200, 300, false) );
                                    }
                                };

                                new ConfettiManager( mActivity, extraCharacter3, confettiSource, viewGroup )
                                        .setEmissionDuration( 5000 )
                                        .setEmissionRate( 1 )
                                        .setVelocityX( 0, 50 )
                                        .setVelocityY( 500 )
                                        .setRotationalVelocity( 180, 180 )
                                        .setTouchEnabled( true )
                                        .animate();
                            }
                        } );

                AlertDialog displayInfo = builder.create();
                displayInfo.getWindow().setBackgroundDrawableResource( R.drawable.border );
                displayInfo.show();

                // Must be called after show()
                TextView message = displayInfo.findViewById( R.id.whats_new_message );
                message.setLinkTextColor( mActivity.getResources().getColor( R.color.colorRed ) );
                message.setMovementMethod( LinkMovementMethod.getInstance() );

            }
        } catch ( PackageManager.NameNotFoundException e ) {
            e.printStackTrace();
        }
    }
}
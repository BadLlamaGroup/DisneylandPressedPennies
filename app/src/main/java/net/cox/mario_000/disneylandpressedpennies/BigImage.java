package net.cox.mario_000.disneylandpressedpennies;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.io.File;

import static net.cox.mario_000.disneylandpressedpennies.MainActivity.COIN_PATH;

/**
 * Created by mario_000 on 6/29/2016.
 * Description: Display full screen image
 */
public class BigImage extends Activity
{
    // Coin images
    private ImageView frontCoin;
    private ImageView backCoin;

    // Animation
    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;
    private boolean mIsBackVisible;

    // Data
    boolean custom;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.big_image );
        Bundle extras = getIntent().getExtras();
        if ( extras != null )
        {
            // Names for coin images
            String frontImg = extras.getString( "frontImg" );
            String backImg = extras.getString( "backImg" );

            // Link views
            frontCoin = findViewById( R.id.editCoinFront );
            backCoin = findViewById( R.id.editCoinBack );
            LinearLayout layout = findViewById( R.id.layout );

            // Get image resId number
            final int frontResId = getApplicationContext().getResources().getIdentifier( frontImg, "drawable", getApplicationContext().getPackageName() );
            String test = backImg == null ? frontImg + "_backstamp" : backImg;
            final int backResId = getApplicationContext().getResources().getIdentifier( test, "drawable", getApplicationContext().getPackageName() );
            if ( frontResId != 0 && backResId != 0 )
            {
                // Display front image
                Picasso.get().load( frontResId ).error( R.drawable.new_penny ).into( frontCoin );

                // Create dimensions
                BitmapFactory.Options dimensions = new BitmapFactory.Options();
                dimensions.inJustDecodeBounds = true;

                // Check dimensions of front image
                Bitmap frontBitmap = BitmapFactory.decodeResource( getApplicationContext().getResources(), frontResId, dimensions );
                int frontHeight = dimensions.outHeight;
                int frontWidth = dimensions.outWidth;

                // Check dimensions of back image
                Bitmap backBitmap = BitmapFactory.decodeResource( getApplicationContext().getResources(), backResId, dimensions );
                int backHeight = dimensions.outHeight;
                int backWidth = dimensions.outWidth;

                // Set orientation based on coin direction
                if ( frontWidth > frontHeight )
                {
                    if ( backWidth < backHeight )
                    {
                        Picasso.get().load( backResId ).error( R.drawable.new_penny_back ).rotate( 90 ).into( backCoin );
                    } else
                    {
                        Picasso.get().load( backResId ).error( R.drawable.new_penny_back ).into( backCoin );
                    }

                } else
                {
                    if ( backWidth > backHeight )
                    {
                        Picasso.get().load( backResId ).error( R.drawable.new_penny_back ).rotate( 90 ).into( backCoin );
                    } else
                    {
                        Picasso.get().load( backResId ).error( R.drawable.new_penny_back ).into( backCoin );
                    }
                }
            } else
            {
                custom = true;

                // Set images
                Uri frontImage = Uri.fromFile( new File( COIN_PATH + "/" + frontImg ) );
                Picasso.get().load( frontImage ).error( R.drawable.new_penny ).fit().into( frontCoin );

                Uri backImage = Uri.fromFile( new File( COIN_PATH + "/" + backImg ) );
                Picasso.get().load( backImage ).error( R.drawable.new_penny_back ).fit().into( backCoin );
            }

            // Set animation
            Animation hyperspaceJump = AnimationUtils.loadAnimation( getApplicationContext(), R.anim.fade_in );
            layout.startAnimation( hyperspaceJump );

            // Set animations
            mSetRightOut = ( AnimatorSet ) AnimatorInflater.loadAnimator( this, R.animator.flip1 );
            mSetLeftIn = ( AnimatorSet ) AnimatorInflater.loadAnimator( this, R.animator.flip2 );

            // Set camera distance
            int distance = 8000;
            float scale = getResources().getDisplayMetrics().density * distance;
            frontCoin.setCameraDistance( scale );
            backCoin.setCameraDistance( scale );

            // Flip coin
            layout.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick( View v )
                {
                    // Check if coin is currently flipping
                    if ( !( mSetRightOut.isRunning() || mSetLeftIn.isRunning() ) && ( frontResId != backResId || custom ) )
                    {
                        flipCoin();
                    }
                }
            } );
        }
    }

    public void flipCoin()
    {
        backCoin.setVisibility( View.VISIBLE );
        if ( !mIsBackVisible )
        {
            mSetRightOut.setTarget( frontCoin );
            mSetLeftIn.setTarget( backCoin );
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = true;
        } else
        {
            mSetRightOut.setTarget( backCoin );
            mSetLeftIn.setTarget( frontCoin );
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = false;
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        overridePendingTransition( R.anim.fade_in, R.anim.fade_out );
    }
}
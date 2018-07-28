package net.cox.mario_000.disneylandpressedpennies;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.img;

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
            final int resId = getApplicationContext().getResources().getIdentifier( frontImg, "drawable", getApplicationContext().getPackageName() );
            if( resId != 0 )
            {
                final int resId2;
                if ( backImg == null )
                {
                    resId2 = getApplicationContext().getResources().getIdentifier( frontImg + "_backstamp", "drawable", getApplicationContext().getPackageName() );
                } else
                {
                    resId2 = getApplicationContext().getResources().getIdentifier( backImg, "drawable", getApplicationContext().getPackageName() );
                }

                // Display image
                img = new DisplayImage();
                img.loadBitmap( resId, getResources(), 1200, 1200, frontCoin, 0 );
                BitmapFactory.Options dimensions = new BitmapFactory.Options();
                dimensions.inJustDecodeBounds = true;
                Bitmap mBitmap = BitmapFactory.decodeResource( getApplicationContext().getResources(), resId, dimensions );
                int height = dimensions.outHeight;
                int width = dimensions.outWidth;

                //Set orientation based on coin direction
                if ( width > height )
                {
                    Bitmap mBitmap2 = BitmapFactory.decodeResource( getApplicationContext().getResources(), resId2 );
                    if ( mBitmap2.getWidth() < mBitmap2.getHeight() )
                    {
                        Matrix matrix = new Matrix();
                        matrix.postRotate( 90 );
                        Bitmap bitmap = Bitmap.createBitmap( mBitmap2, 0, 0, mBitmap2.getWidth(), mBitmap2.getHeight(), matrix, true );
                        backCoin.setImageBitmap( bitmap );
                    } else
                    {
                        img.loadBitmap( resId2, getResources(), 1200, 1200, backCoin, resId );
                    }

                } else
                {
                    img.loadBitmap( resId2, getResources(), 1200, 1200, backCoin, resId );
                }
            }
            else
            {
                // Set images
                Uri frontImage = Uri.fromFile( new File( COIN_PATH + "/" + frontImg ) );
                Picasso.get().load( frontImage ).error( R.drawable.new_penny ).fit().into( frontCoin );

                Uri backImage = Uri.fromFile( new File( COIN_PATH + "/" + backImg ) );
                Picasso.get().load( backImage ).error( R.drawable.new_penny ).fit().into( backCoin );
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
                    /*if ( !( mSetRightOut.isRunning() || mSetLeftIn.isRunning() ) && resId != resId2 )
                    {
                        flipCoin();
                    }*/
                    if ( !( mSetRightOut.isRunning() || mSetLeftIn.isRunning() ) )
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
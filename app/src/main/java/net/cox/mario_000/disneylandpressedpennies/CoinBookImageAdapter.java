package net.cox.mario_000.disneylandpressedpennies;

/**
 * Created by mario_000 on 1/21/2017.
 */

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import static net.cox.mario_000.disneylandpressedpennies.MainActivity.COIN_PATH;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.find;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.img;

class CoinBookImageAdapter extends PagerAdapter
{
    // References
    private Context context;
    private LayoutInflater mLayoutInflater;
    private final SharedPreference sharedPreference = new SharedPreference();

    // Coins
    private List< Coin > coinList;
    private List< Coin > customCoins;

    // Animation
    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;


    CoinBookImageAdapter( Context context, List< Coin > coinList )
    {
        this.context = context;
        this.coinList = coinList;
        mLayoutInflater = ( LayoutInflater ) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        customCoins = sharedPreference.getCustomCoins( context );

        // Set animations
        mSetRightOut = ( AnimatorSet ) AnimatorInflater.loadAnimator( context, R.animator.flip1 );
        mSetLeftIn = ( AnimatorSet ) AnimatorInflater.loadAnimator( context, R.animator.flip2 );
    }

    @Override
    public int getCount()
    {
        return coinList.size();
    }

    @Override
    public boolean isViewFromObject( View view, Object object )
    {
        return view == object;
    }

    @Override
    public Object instantiateItem( ViewGroup container, final int position )
    {
        View itemView = mLayoutInflater.inflate( R.layout.big_image, container, false );

        // Link views
        final ImageView coinFront = itemView.findViewById( R.id.editCoinFront );
        final ImageView coinBack = itemView.findViewById( R.id.editCoinBack );

        // Get displayed coin
        final Coin currentCoin = coinList.get( position );

        if( customCoins.contains( currentCoin ) )
        {
            // Set image
            Uri frontImage = Uri.fromFile( new File( COIN_PATH + "/" + currentCoin.getCoinFrontImg() ) );
            Uri backImage = Uri.fromFile( new File( COIN_PATH + "/" + currentCoin.getCoinBackImg() ) );
            Picasso.get().load( frontImage ).error( R.drawable.new_penny ).fit().into( coinFront );
            Picasso.get().load( backImage ).error( R.drawable.new_penny ).fit().into( coinBack );
        }
        else
        {
            Machine currentMac = find( currentCoin );
            final int resId3 = context.getResources().getIdentifier( currentCoin.getCoinFrontImg(), "drawable", context.getPackageName() );
            final int resId4;
            if ( currentMac.getBackstampImg() == null )
            {
                resId4 = context.getResources().getIdentifier( currentCoin.getCoinFrontImg() + "_backstamp", "drawable", context.getPackageName() );
            } else
            {
                resId4 = context.getResources().getIdentifier( currentMac.getBackstampImg(), "drawable", context.getPackageName() );
            }

            // Display image
            img = new DisplayImage();
            img.loadBitmap( resId3, context.getResources(), 1200, 1200, coinFront, 0 );
            BitmapFactory.Options dimensions = new BitmapFactory.Options();
            dimensions.inJustDecodeBounds = true;
            Bitmap mBitmap = BitmapFactory.decodeResource( context.getResources(), resId3, dimensions );
            int height = dimensions.outHeight;
            int width = dimensions.outWidth;

            //Set orientation based on coin direction
            if ( width > height )
            {
                Bitmap mBitmap2 = BitmapFactory.decodeResource( context.getResources(), resId4 );
                if ( mBitmap2.getWidth() < mBitmap2.getHeight() )
                {
                    Matrix matrix = new Matrix();
                    matrix.postRotate( 90 );
                    Bitmap bitmap = Bitmap.createBitmap( mBitmap2, 0, 0, mBitmap2.getWidth(), mBitmap2.getHeight(), matrix, true );
                    coinBack.setImageBitmap( bitmap );
                } else
                {
                    img.loadBitmap( resId4, context.getResources(), 1200, 1200, coinBack, resId3 );
                }

            } else
            {
                img.loadBitmap( resId4, context.getResources(), 1200, 1200, coinBack, resId3 );
            }
        }

        // Set camera distance
        int distance = 4000;
        float scale = context.getResources().getDisplayMetrics().density * distance;
        coinFront.setCameraDistance( scale );
        coinBack.setCameraDistance( scale );

        coinFront.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                if ( !( mSetRightOut.isRunning() || mSetLeftIn.isRunning() ) )
                {
                    mSetRightOut.setTarget( coinFront );
                    mSetLeftIn.setTarget( coinBack );
                    mSetRightOut.start();
                    mSetLeftIn.start();

                    coinBack.setVisibility( View.VISIBLE );
                    final Handler handler = new Handler();
                    handler.postDelayed( new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            coinFront.setVisibility( View.INVISIBLE );
                        }
                    }, 1000 );
                }
            }
        } );
        coinBack.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                if ( !( mSetRightOut.isRunning() || mSetLeftIn.isRunning() ) )
                {
                    mSetRightOut.setTarget( coinBack );
                    mSetLeftIn.setTarget( coinFront );
                    mSetRightOut.start();
                    mSetLeftIn.start();

                    coinFront.setVisibility( View.VISIBLE );
                    final Handler handler = new Handler();
                    handler.postDelayed( new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            coinBack.setVisibility( View.INVISIBLE );

                        }
                    }, 1000 );
                }
            }
        } );

        container.addView( itemView );

        return itemView;
    }

    @Override
    public void destroyItem( ViewGroup container, int position, Object object )
    {
        container.removeView( ( LinearLayout ) object );
    }

}
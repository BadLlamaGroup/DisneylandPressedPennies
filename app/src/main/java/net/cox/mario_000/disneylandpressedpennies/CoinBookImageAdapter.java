package net.cox.mario_000.disneylandpressedpennies;

/**
 * Created by mario_000 on 1/21/2017.
 */

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

class CoinBookImageAdapter extends PagerAdapter
{
    // References
    private Context context;
    private LayoutInflater mLayoutInflater;
    private final SharedPreference sharedPreference = new SharedPreference();

    // Coins
    private List< Object > coinList;
    private List< CustomCoin > customCoins;

    // Animation
    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;


    CoinBookImageAdapter( Context context, List< Object > coinList )
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
        if ( coinList.get( position ) instanceof Coin )
        {
            final Coin currentCoin = ( Coin ) coinList.get( position );
            String frontImg = currentCoin.getCoinFrontImg();

            // Get image resId number
            final int frontResId = context.getResources().getIdentifier( frontImg, "drawable", context.getPackageName() );
            int backResId;
            // Get machine for coin
            Machine currentMac = find( currentCoin );
            String backImg = currentMac.getBackstampImg();

            if ( backImg == null )
            {
                backResId = context.getResources().getIdentifier( frontImg + "_backstamp", "drawable", context.getPackageName() );
            } else
            {
                backResId = context.getResources().getIdentifier( backImg, "drawable", context.getPackageName() );
            }

            if ( frontResId == 0 )
            {
                Picasso.get().load( R.drawable.new_penny ).into( coinFront );
            } else
            {
                // Display front image
                Picasso.get().load( frontResId ).error( R.drawable.new_penny ).into( coinFront );
            }

            if ( backResId == 0 )
            {
                backResId = context.getResources().getIdentifier( "new_penny_back", "drawable", context.getPackageName() );
            }

            // Create dimensions
            BitmapFactory.Options dimensions = new BitmapFactory.Options();
            dimensions.inJustDecodeBounds = true;

            // Check dimensions of front image
            Bitmap frontBitmap = BitmapFactory.decodeResource( context.getResources(), frontResId, dimensions );
            int frontHeight = dimensions.outHeight;
            int frontWidth = dimensions.outWidth;

            // Check dimensions of back image
            Bitmap backBitmap = BitmapFactory.decodeResource( context.getResources(), backResId, dimensions );
            int backHeight = dimensions.outHeight;
            int backWidth = dimensions.outWidth;

            // Set orientation based on coin direction
            if ( frontWidth > frontHeight )
            {
                if ( backWidth < backHeight )
                {
                    Picasso.get().load( backResId ).error( R.drawable.new_penny_back ).rotate( 90 ).into( coinBack );
                } else
                {
                    Picasso.get().load( backResId ).error( R.drawable.new_penny_back ).into( coinBack );
                }
            } else
            {
                if ( backWidth > backHeight )
                {
                    Picasso.get().load( backResId ).error( R.drawable.new_penny_back ).rotate( 90 ).into( coinBack );
                } else
                {
                    Picasso.get().load( backResId ).error( R.drawable.new_penny_back ).into( coinBack );
                }
            }
        } else
        {
            final CustomCoin currentCoin = ( CustomCoin ) coinList.get( position );
            String frontImg = currentCoin.getCoinFrontImg();

            int index = customCoins.indexOf( currentCoin );
            CustomCoin customCoin = customCoins.get( index );

            // Set images
            Uri frontImage = Uri.fromFile( new File( COIN_PATH + "/" + frontImg ) );
            Picasso.get().load( frontImage ).error( R.drawable.new_penny ).into( coinFront );

            Uri backImage = Uri.fromFile( new File( COIN_PATH + "/" + customCoin.getCoinBackImg() ) );
            Picasso.get().load( backImage ).error( R.drawable.new_penny_back ).into( coinBack );
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
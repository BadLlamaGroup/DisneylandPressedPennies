package net.cox.mario_000.disneylandpressedpennies;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import androidx.core.content.ContextCompat;

import static net.cox.mario_000.disneylandpressedpennies.MainActivity.find;

/**
 * Created by mario_000 on 7/7/2016.
 * Description: Adapter for creating the lists for all coins
 */
public class allCoinsAdapter extends ArrayAdapter< Coin >
{
    // Lists
    private ArrayList coins;
    private List< Coin > collectedCoins;
    private List< Coin > wantCoins;

    // Strings
    private String[] newCoins;
    private String[] offMachine;

    // Data
    private Coin coin;
    private Coin savedCoin;

    // Separators
    private ArrayList mSeparatorsSet = new ArrayList();
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;

    // References
    private final Context context;
    private final int mResource;
    private final SharedPreference sharedPreference = new SharedPreference();
    LayoutInflater inflater;
    private SimpleDateFormat dateFormat = new SimpleDateFormat( "MMMM dd, yyyy", Locale.US );


    public allCoinsAdapter( Context context, int resource, ArrayList coins )
    {
        super( context, resource, coins );
        this.context = context;
        this.mResource = resource;
        this.coins = coins;
        collectedCoins = sharedPreference.getCoins( getContext() );
        wantCoins = sharedPreference.getWantedCoins( getContext() );
        inflater = ( LayoutInflater ) getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        newCoins = getContext().getResources().getStringArray( R.array.new_coins );
        offMachine = getContext().getResources().getStringArray( R.array.off_mac );
    }

    public void addSeparatorItem( final String item )
    {
        coins.add( item );
        mSeparatorsSet.add( item );
        notifyDataSetChanged();
    }

    @Override
    public View getView( final int position, View row, ViewGroup parent )
    {
        final CoinHolder holder = new CoinHolder();

        // Check if coin or land name
        int type = getItemViewType( position );
        switch ( type )
        {
            case TYPE_ITEM:
                row = inflater.inflate( R.layout.row, null );
                holder.description = row.findViewById( R.id.rowDescription );
                holder.name = row.findViewById( R.id.rowName );
                holder.collected = row.findViewById( R.id.rowCollected );
                holder.imageView = row.findViewById( R.id.imgCoin );
                holder.new_coins_img = row.findViewById( R.id.newCoin );
                holder.off_mac_img = row.findViewById( R.id.offMac );
                holder.row_new_bg = row.findViewById( R.id.row_new_bg );
                break;
            case TYPE_SEPARATOR:
                row = inflater.inflate( R.layout.header, null );
                holder.separator = row.findViewById( R.id.separator );
                break;
        }

        if ( type == TYPE_ITEM )
        {
            // Get coin
            coin = ( Coin ) coins.get( position );

            // Find machine that coin belongs to
            Machine mac = find( coin );

            savedCoin = coin;

            // Check if coin is collected
            for ( Coin c : collectedCoins )
            {
                if ( c.equals( coin ) )
                {
                    savedCoin = collectedCoins.get( collectedCoins.indexOf( c ) );
                }
            }

            // Set image and res id
            int frontResId = context.getResources().getIdentifier( coin.getCoinFrontImg(), "drawable", context.getPackageName() );
            if( frontResId == 0 ){
                Picasso.get().load( R.drawable.new_penny ).into( holder.imageView );
            }else
            {
                // Display front image
                Picasso.get().load( frontResId ).error( R.drawable.new_penny ).into( holder.imageView );
            }

            setToGray( holder.imageView );

            //Set which row was clicked
            holder.imageView.setTag( position );

            //Set text value for row
            holder.name.setText( String.valueOf( coin.getTitleCoin() ) );
            holder.description.setText( String.valueOf( mac.getMachineName() ) );

            // Scroll text
            Handler handler = new Handler();
            handler.postDelayed( new Runnable()
            {
                public void run()
                {
                    holder.name.setSelected( true );
                }
            }, 2500 );

            // Check machine status
            if ( Arrays.asList( newCoins ).contains( mac.getMachineName() ) )
            {
                holder.row_new_bg.setBackgroundColor( Color.CYAN );
                //holder.new_coins_img.setVisibility( View.VISIBLE );
            } else if ( Arrays.asList( offMachine ).contains( mac.getMachineName() ) )
            {
                holder.row_new_bg.setBackgroundColor( Color.YELLOW );
                //holder.off_mac_img.setVisibility( View.VISIBLE );
            } else
            {
                holder.description.setTextColor( Color.GRAY );
                holder.name.setTextColor( Color.GRAY );
                holder.collected.setTextColor( Color.GRAY );
                holder.row_new_bg.setBackgroundColor( Color.WHITE );
                holder.collected.setText( "Not yet collected" );
            }

            if ( collectedCoins.contains( coin ) )
            {
                setToColor( holder.imageView );
                holder.description.setTextColor( ContextCompat.getColor( context, R.color.colorPrimaryDark ) );
                holder.name.setTextColor( ContextCompat.getColor( context, R.color.colorPrimaryDark ) );
                holder.collected.setTextColor( ContextCompat.getColor( context, R.color.colorPrimaryDark ) );

                // Check date
                if ( savedCoin.getDateCollected() != null )
                {
                    String date = dateFormat.format( savedCoin.getDateCollected() );
                    holder.collected.setText( String.format( "Collected: %s", date ) );
                } else
                {
                    holder.collected.setText( "Collected" );
                }
            }

            if ( checkWant( coin ) )
            {
                holder.description.setTextColor( ContextCompat.getColor( context, R.color.colorGreen ) );
                holder.name.setTextColor( ContextCompat.getColor( context, R.color.colorGreen ) );
                holder.collected.setTextColor( ContextCompat.getColor( context, R.color.colorGreen ) );
                holder.collected.setText( "Want It" );
            }

            // Set listener
            row.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick( View view )
                {
                    FragmentTransaction fragmentTransaction = ( ( Activity ) context ).getFragmentManager().beginTransaction();
                    singleCoin fragment = new singleCoin();
                    Bundle bundle = new Bundle();
                    Gson gson = new Gson();

                    // Save coin
                    Coin coin = ( Coin ) coins.get( position );
                    String jsonCoin = gson.toJson( coin );
                    String jsonMachine = null;

                    // Save machine
                    Machine mac = find( coin );
                    if ( mac != null )
                    {
                        jsonMachine = gson.toJson( mac );
                    }

                    view.setTag( position );

                    bundle.putString( "selectedCoin", jsonCoin );
                    bundle.putString( "selectedMachine", jsonMachine );
                    fragment.setArguments( bundle );
                    fragmentTransaction.setCustomAnimations(
                            R.animator.fade_in,
                            R.animator.fade_out,
                            R.animator.fade_in,
                            R.animator.fade_out );
                    fragmentTransaction.replace( R.id.mainFrag, fragment );
                    fragmentTransaction.addToBackStack( null );
                    fragmentTransaction.commit();
                }
            } );

            holder.imageView.setOnClickListener( PopupListener );
        } else
        {
            // Set separator text
            if ( position == 0 || mSeparatorsSet.contains( coins.get( position ) ) )
            {
                coin = ( Coin ) coins.get( position + 1 );
            } else
            {
                coin = ( Coin ) coins.get( position );
            }
            Machine mac = find( coin );

            if ( Arrays.asList( newCoins ).contains( mac.getMachineName() ) )
            {
                holder.separator.setText( mac.getMachineName() + " - NEW" );
            } else if ( Arrays.asList( offMachine ).contains( mac.getMachineName() ) )
            {
                holder.separator.setText( mac.getMachineName() + " - Offstage" );
            } else
            {
                holder.separator.setText( mac.getMachineName() );
            }
        }

        return row;
    }

    // Check if coin is in collected coins
    private boolean checkWant( Coin checkCoin )
    {
        boolean check = false;

        if ( wantCoins != null )
        {
            for ( Coin coin : wantCoins )
            {
                // Check if coin matches coin already collected
                if ( String.valueOf( coin.getTitleCoin() ).equals( String.valueOf( checkCoin.getTitleCoin() ) ) )
                {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    private final View.OnClickListener PopupListener = new View.OnClickListener()
    {
        @Override
        public void onClick( View v )
        {
            //Get which row was clicked
            Integer viewPos = ( Integer ) v.getTag();
            Coin coin = ( Coin ) coins.get( viewPos );
            Intent intent = new Intent( context, BigImage.class );
            intent.putExtra( "frontImg", coin.getCoinFrontImg() );

            Machine mac = find( coin );
            intent.putExtra( "backImg", mac.getBackstampImg() );
            intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
            context.startActivity( intent );
        }
    };

    // Set imageview to gray
    public void setToGray( ImageView v )
    {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation( 0 );  //0 means grayscale
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter( matrix );
        v.setColorFilter( cf );
    }

    // Set imageview to color
    public void setToColor( ImageView v )
    {
        v.setColorFilter( null );
    }

    private static class CoinHolder
    {
        TextView name;
        TextView description;
        TextView collected;
        ImageView imageView;
        TextView separator;
        ImageView new_coins_img;
        ImageView off_mac_img;
        RelativeLayout row_new_bg;
    }

    @Override
    public int getItemViewType( int position )
    {
        return mSeparatorsSet.contains( coins.get( position ) ) ? TYPE_SEPARATOR : TYPE_ITEM;
    }
}
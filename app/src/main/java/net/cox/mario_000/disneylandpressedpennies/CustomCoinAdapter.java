package net.cox.mario_000.disneylandpressedpennies;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TreeSet;

import static net.cox.mario_000.disneylandpressedpennies.MainActivity.COIN_PATH;

/**
 * Created by mario_000 on 7/14/2018.
 */

public class CustomCoinAdapter extends ArrayAdapter< CustomCoin > implements View.OnClickListener
{
    // Adapter types
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;

    // References
    private final Context context;
    private final int mResource;

    // Data
    private final ArrayList customCoins;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat( "MMMM dd, yyyy", Locale.US );
    private LayoutInflater inflater;
    private CoinHolder holder;
    private TreeSet< Integer > mSeparatorsSet = new TreeSet<>();

    public CustomCoinAdapter( Context context, int resource, ArrayList coins )
    {
        super( context, resource, coins );
        this.context = context;
        this.mResource = resource;
        this.customCoins = coins;
        inflater = ( LayoutInflater ) getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
    }

    @Override
    public View getView( final int position, View row, ViewGroup parent )
    {
        int type = getItemViewType( position );

        switch ( type )
        {
            case TYPE_ITEM:
                // Set up layout for row
                row = inflater.inflate( R.layout.row, parent, false );

                //Link views
                holder = new CoinHolder();
                holder.name = row.findViewById( R.id.rowName );
                holder.description = row.findViewById( R.id.rowDescription );
                holder.collected = row.findViewById( R.id.rowCollected );
                holder.imageView = row.findViewById( R.id.imgCoin );

                //Set which row was clicked
                holder.imageView.setTag( position );

                // Get single coin
                final CustomCoin savedCoin = ( CustomCoin ) customCoins.get( position );

                // Set image
                Uri frontImage = Uri.fromFile( new File( COIN_PATH + "/" + savedCoin.getCoinFrontImg() ) );
                Picasso.get().load( frontImage ).error( R.drawable.new_penny ).fit().into( holder.imageView );

                // Set data
                holder.description.setText( savedCoin.getCoinPark() );
                holder.name.setText( String.valueOf( savedCoin.getTitleCoin() ) );
                if ( savedCoin.getDateCollected() != null )
                {
                    holder.collected.setText( String.format( "Collected: %s", dateFormat.format( savedCoin.getDateCollected() ) ) );
                }

                // Set big image listener
                holder.imageView.setOnClickListener( this );

                // Make text scroll
                Handler handler = new Handler();
                handler.postDelayed( new Runnable()
                {
                    public void run()
                    {
                        holder.name.setSelected( true );
                    }
                }, 2500 );

                // Set detail listener
                row.setOnClickListener( new View.OnClickListener()
                {
                    @Override
                    public void onClick( View view )
                    {
                        view.setTag( position );

                        FragmentTransaction fragmentTransaction = ( ( Activity ) context ).getFragmentManager().beginTransaction();
                        CustomCoinFragment fragment = new CustomCoinFragment();
                        Bundle bundle = new Bundle();
                        Gson gson = new Gson();
                        String jsonCoin = gson.toJson( savedCoin );
                        bundle.putString( "selectedCoin", jsonCoin );
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

                return row;

            case TYPE_SEPARATOR:
                ListAdapter.ViewHolder sep = new ListAdapter.ViewHolder();
                row = inflater.inflate( R.layout.header, parent, false );
                sep.separator = row.findViewById( R.id.separator );
                row.setTag( sep );

                if ( position == 0 || mSeparatorsSet.contains( position ) )
                {
                    sep.separator.setText( customCoins.get( position ).toString() );
                }
                return row;
        }

        return row;
    }

    @Override
    public void onClick( View v )
    {
        //Get which row was clicked
        Integer viewPos = ( Integer ) v.getTag();
        CustomCoin coin = ( CustomCoin ) customCoins.get( viewPos );
        Intent bigIntent = new Intent( context, BigImage.class );
        bigIntent.putExtra( "frontImg", coin.getCoinFrontImg() );
        bigIntent.putExtra( "backImg", coin.getCoinBackImg() );
        bigIntent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
        context.startActivity( bigIntent );
    }

    public ArrayList getCustomCoins()
    {
        return customCoins;
    }

    public void addSeparatorItem( final String item )
    {
        customCoins.add( item );
        // save separator position
        mSeparatorsSet.add( customCoins.size() - 1 );
        notifyDataSetChanged();
    }

    public void removeSeparatorItem()
    {
        mSeparatorsSet.clear();
        notifyDataSetChanged();
    }

    public boolean duplicateSeparator( final String item )
    {
        return customCoins.contains( item );
    }


    @Override
    public int getItemViewType( int position )
    {
        return mSeparatorsSet.contains( position ) ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    private static final class CoinHolder
    {
        TextView name;
        TextView description;
        TextView collected;
        ImageView imageView;
    }
}
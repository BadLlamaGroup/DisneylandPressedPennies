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
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;

import static net.cox.mario_000.disneylandpressedpennies.MainActivity.COIN_PATH;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.find;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.img;

/**
 * Created by mario_000 on 7/7/2016.
 * Description: Adapter class for creating lists
 */
public class ListAdapter extends ArrayAdapter< Coin >
{
    // Adapter types
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;
    // References
    private final Context context;
    private final int mResource;
    // Data
    private final SimpleDateFormat dateFormat = new SimpleDateFormat( "MMMM dd, yyyy", Locale.US );
    private final ArrayList tempCoins;
    private final List< Coin > customCoins;
    private LayoutInflater inflater;
    private CoinHolder holder;
    private TreeSet< Integer > mSeparatorsSet = new TreeSet<>();
    private Machine machine;
    // References
    private final SharedPreference sharedPreference = new SharedPreference();

    private final View.OnClickListener PopupListener = new View.OnClickListener()
    {
        @Override
        public void onClick( View v )
        {
            //Get which row was clicked
            Integer viewPos = ( Integer ) v.getTag();
            Intent bigImageIntent = new Intent( context, BigImage.class );
            Coin coin = ( Coin ) tempCoins.get( viewPos );
            if( customCoins.contains( coin ) )
            {
                bigImageIntent.putExtra( "frontImg", coin.getCoinFrontImg() );
                bigImageIntent.putExtra( "backImg", coin.getCoinBackImg() );
            }
            else
            {
                machine = find( coin );
                bigImageIntent.putExtra( "frontImg", coin.getCoinFrontImg() );
                bigImageIntent.putExtra( "backImg", machine.getBackstampImg() );
            }

            bigImageIntent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
            context.startActivity( bigImageIntent );
        }
    };

    public ListAdapter( Context context, int resource, ArrayList coins )
    {
        super( context, resource, coins );
        this.context = context;
        this.mResource = resource;
        this.tempCoins = coins;
        this.customCoins = sharedPreference.getCustomCoins( context );
        inflater = ( LayoutInflater ) getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
    }

    public void addSeparatorItem( final String item )
    {
        tempCoins.add( item );
        // save separator position
        mSeparatorsSet.add( tempCoins.size() - 1 );
        notifyDataSetChanged();
    }

    public void removeSeparatorItem()
    {
        mSeparatorsSet.clear();
        notifyDataSetChanged();
    }

    public boolean duplicateSeparator( final String item )
    {
        return tempCoins.contains( item );
    }

    @Override
    public View getView( final int position, View row, ViewGroup parent )
    {

        int type = getItemViewType( position );

        switch ( type )
        {
            case TYPE_ITEM:
                holder = new CoinHolder();
                row = inflater.inflate( R.layout.row, parent, false );
                holder.name = row.findViewById( R.id.rowName );
                holder.description = row.findViewById( R.id.rowDescription );
                holder.collected = row.findViewById( R.id.rowCollected );
                holder.imageView = row.findViewById( R.id.imgCoin );
                row.setTag( holder );

                //Set which row was clicked
                holder.imageView.setTag( position );

                final Coin coin = ( Coin ) tempCoins.get( position );

                if( customCoins.contains( coin ) )
                {
                    // Set image
                    Uri frontImage = Uri.fromFile( new File( COIN_PATH + "/" + coin.getCoinFrontImg() ) );
                    Picasso.get().load( frontImage ).error( R.drawable.new_penny ).fit().into( holder.imageView );
                    //Set text value for row
                    holder.name.setText( String.valueOf( coin.getTitleCoin() ) );
                    holder.description.setText( String.valueOf( coin.getCoinPark() ) );

                    holder.imageView.setOnClickListener( PopupListener );
                }
                else
                {
                    //Set image and res id
                    int resId = context.getResources().getIdentifier( coin.getCoinFrontImg(), "drawable", context.getPackageName() );
                    img.loadBitmap( resId, context.getResources(), 100, 140, holder.imageView, 0 );
                    holder.imageView.setOnClickListener( PopupListener );

                    // Find machine that coin belongs to
                    Machine mac = find( coin );
                    //Set text value for row
                    holder.name.setText( String.valueOf( coin.getTitleCoin() ) );
                    holder.description.setText( String.valueOf( mac.getMachineName() ) );
                }

                if ( coin.getDateCollected() != null )
                {
                    holder.collected.setText( String.format( "Collected: %s", dateFormat.format( coin.getDateCollected() ) ) );
                }

                // Make text scroll
                Handler handler = new Handler();
                handler.postDelayed( new Runnable()
                {
                    public void run()
                    {
                        holder.name.setSelected( true );
                    }
                }, 1500 );

                // Set detail listener
                row.setOnClickListener( new View.OnClickListener()
                {
                    @Override
                    public void onClick( View view )
                    {
                        view.setTag( position );

                        FragmentTransaction fragmentTransaction = ( ( Activity ) context ).getFragmentManager().beginTransaction();
                        Bundle bundle = new Bundle();
                        Gson gson = new Gson();
                        String jsonCoin = gson.toJson( coin );

                        fragmentTransaction.setCustomAnimations(
                                R.animator.fade_in,
                                R.animator.fade_out,
                                R.animator.fade_in,
                                R.animator.fade_out );

                        if( customCoins.contains( coin ) )
                        {
                            CustomCoinFragment fragment = new CustomCoinFragment();
                            bundle.putString( "selectedCoin", jsonCoin );
                            fragment.setArguments( bundle );
                            fragmentTransaction.replace( R.id.mainFrag, fragment );
                        }
                        else
                        {
                            singleCoin fragment = new singleCoin();
                            String jsonMac = gson.toJson( find( coin ) );
                            bundle.putString( "selectedCoin", jsonCoin );
                            bundle.putString( "selectedMachine", jsonMac );
                            fragment.setArguments( bundle );
                            fragmentTransaction.replace( R.id.mainFrag, fragment );
                        }
                        fragmentTransaction.addToBackStack( null );
                        fragmentTransaction.commit();
                    }
                } );


                return row;

            case TYPE_SEPARATOR:
                ViewHolder sep = new ViewHolder();
                row = inflater.inflate( R.layout.header, parent, false );
                sep.separator = ( TextView ) row.findViewById( R.id.separator );
                row.setTag( sep );

                if ( position == 0 || mSeparatorsSet.contains( position ) )
                {
                    sep.separator.setText( tempCoins.get( position ).toString() );
                }
                return row;
        }

        return row;
    }

    @Override
    public int getItemViewType( int position )
    {
        return mSeparatorsSet.contains( position ) ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    private static class CoinHolder
    {
        TextView name;
        TextView description;
        TextView collected;
        ImageView imageView;
    }

    static class ViewHolder
    {
        TextView separator;
    }
}
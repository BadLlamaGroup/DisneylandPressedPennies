package net.cox.mario_000.disneylandpressedpennies;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

/**
 * Created by mario_000 on 6/25/2016.
 * Description: Adapter for creating a machine row
 */
public class MachineAdapter extends ArrayAdapter< Machine > implements View.OnClickListener
{
    // Data
    Machine[] machines;
    private List< Coin > collectedCoins;

    // References
    private final Context context;
    private final int mResource;
    private final SharedPreference sharedPreference = new SharedPreference();

    public MachineAdapter( Context context, int resource, Machine[] machines )
    {
        super( context, resource, machines );
        this.context = context;
        this.mResource = resource;
        this.machines = machines;
        collectedCoins = sharedPreference.getCoins( getContext() );
    }

    @Override
    public View getView( int position, View row, ViewGroup parent )
    {
        final MachineHolder holder;
        if ( row == null )
        {
            LayoutInflater inflater = LayoutInflater.from( context );
            row = inflater.inflate( mResource, parent, false );

            //Link views
            holder = new MachineHolder();
            holder.location = row.findViewById( R.id.rowName );
            holder.imageView = row.findViewById( R.id.imgMac );
            holder.description = row.findViewById( R.id.rowDescription );
            holder.collected = row.findViewById( R.id.rowCollected );

            //Set position of row
            row.setTag( holder );
        } else
        {
            //Get position of row
            holder = ( MachineHolder ) row.getTag();
        }

        //Get single machine
        Machine machine = machines[ position ];

        //Set image and res Id
        int macId = context.getResources().getIdentifier( machine.getMachineImg(), "drawable", context.getPackageName() );
        Picasso.get().load( macId ).error( R.drawable.new_searching ).fit().into( holder.imageView );
        holder.imageView.setOnClickListener( this );

        //Set which row was clicked
        Integer rowPos = position;
        holder.imageView.setTag( rowPos );

        // Check how many coins collected
        Coin[] coins = machine.getCoins();
        int amtInMachineCollected = 0;
        for ( Coin c : coins )
        {
            if ( collectedCoins.contains( c ) )
            {
                amtInMachineCollected++;
            }
        }

        //Set text values for row
        holder.location.setText( String.valueOf( machine.getMachineName() ) );
        holder.description.setText( "Type of coin: " + String.valueOf( machine.getTypeCoin() ) );
        holder.collected.setText( String.valueOf( "Collected: " + amtInMachineCollected + " / " + coins.length ) );

        // Make text scroll
        Handler handler = new Handler();
        handler.postDelayed( new Runnable()
        {
            public void run()
            {
                holder.location.setSelected( true );
            }
        }, 2500 );


        // Update machine status
        RelativeLayout new_mac_bg = row.findViewById( R.id.machine_new_bg );
        ImageView new_coins_img = row.findViewById( R.id.newCoin );
        ImageView off_mac_img = row.findViewById( R.id.offMac );

        String[] newCoins = getContext().getResources().getStringArray( R.array.new_coins );
        String[] offMachine = getContext().getResources().getStringArray( R.array.off_mac );

        if ( Arrays.asList( newCoins ).contains( machine.getMachineName() ) )
        {
            new_mac_bg.setBackgroundColor( Color.CYAN );
            new_coins_img.setVisibility( View.VISIBLE );
            off_mac_img.setVisibility( View.INVISIBLE );
        } else if ( Arrays.asList( offMachine ).contains( machine.getMachineName() ) )
        {
            new_mac_bg.setBackgroundColor( Color.YELLOW );
            holder.location.setText( String.format( "%s - %s", String.valueOf( machine.getMachineName() ), "Offstage" ) );
            off_mac_img.setVisibility( View.VISIBLE );
            new_coins_img.setVisibility( View.INVISIBLE );
        } else
        {
            new_mac_bg.setBackgroundColor( Color.WHITE );
            new_coins_img.setVisibility( View.INVISIBLE );
            off_mac_img.setVisibility( View.INVISIBLE );
        }

        return row;
    }

    @Override
    public void onClick( View v )
    {
        //Get which row was clicked
        Integer viewPos = ( Integer ) v.getTag();
        Machine machine = machines[ viewPos ];
        Intent intent = new Intent( getContext(), BigImage.class );
        intent.putExtra( "frontImg", machine.getMachineImg() );
        intent.putExtra( "backImg", machine.getMachineImg() );
        intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
        getContext().startActivity( intent );
    }

    private static class MachineHolder
    {
        TextView location;
        TextView description;
        TextView collected;
        ImageView imageView;
    }
}
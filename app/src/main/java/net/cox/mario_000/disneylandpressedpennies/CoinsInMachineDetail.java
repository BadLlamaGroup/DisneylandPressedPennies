package net.cox.mario_000.disneylandpressedpennies;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by mario_000 on 6/25/2016.
 * Description: Fragment for displaying coins in current machine
 */
public class CoinsInMachineDetail extends Fragment implements Data, View.OnClickListener
{
    // Data
    private Machine machine;
    private ListView listCoins;
    private int selection = 0;

    // References
    private ListAdapter mCoinAdapter;
    private Tracker mTracker;

    @Override
    public void onResume()
    {
        // Create coin adapter
        ArrayList< Coin > coins = new ArrayList<>( Arrays.asList( machine.getCoins() ) );
        mCoinAdapter = new ListAdapter( getActivity(), R.layout.row, coins );

        //Check that listCoins is linked to view
        if ( listCoins != null )
        {
            listCoins.setAdapter( mCoinAdapter );
            listCoins.setSelection( selection );
        }
        super.onResume();
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        View view = inflater.inflate( R.layout.activity_coin_detail, container, false );
        MainActivity application = ( MainActivity ) getActivity();
        mTracker = application.getDefaultTracker();

        // Link views to variables
        listCoins = view.findViewById( R.id.listCoinsInMachine );
        final ImageView coinPreview = view.findViewById( R.id.coinPreview );

        Bundle extras = getArguments();
        if ( extras != null )
        {
            // Get selected machine from json
            Gson gson = new Gson();
            String jsonMachine = extras.getString( "selectedMachine" );
            machine = gson.fromJson( jsonMachine, Machine.class );
            getActivity().setTitle( machine.getMachineName() );

            // Get resId for image from machine
            int coinPreviewResId = getActivity().getResources().getIdentifier( machine.getCoinPreviewImg(), "drawable", getActivity().getPackageName() );

            // Load preview image of coins in machine
            coinPreview.setImageResource( coinPreviewResId );
            //Picasso.get().load( coinPreviewResId ).error( R.drawable.new_searching ).fit().into( coinPreview );

            // Create coin adapter
            ArrayList< Coin > coins = new ArrayList<>( Arrays.asList( machine.getCoins() ) );
            mCoinAdapter = new ListAdapter( getActivity(), R.layout.row, coins );

            //Check that listCoins is linked to view
            if ( listCoins != null )
            {
                listCoins.setAdapter( mCoinAdapter );
            }
        }

        // Link views
        LinearLayout navigateBtn = view.findViewById( R.id.linNav );
        LinearLayout showOnMapBtn = view.findViewById( R.id.linMap );

        // Set listeners
        navigateBtn.setOnClickListener( this );
        showOnMapBtn.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                MapsActivity fragment = new MapsActivity();
                Gson gson = new Gson();
                Bundle bundle = new Bundle();
                String jsonMachine = gson.toJson( machine );
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

        TextView txtParkBanner = view.findViewById( R.id.parkBanner );
        txtParkBanner.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                String url = "http://www.parkpennies.com";
                mTracker.send( new HitBuilders.EventBuilder()
                        .setCategory( "Website" )
                        .setAction( "Clicked" )
                        .setLabel( machine.getMachineName() )
                        .setValue( 1 )
                        .build() );
                Intent intent = new Intent( Intent.ACTION_VIEW );
                intent.setData( Uri.parse( url ) );
                startActivity( intent );
            }
        } );

        return view;
    }

    @Override
    public void onClick( View v )
    {
        // Open google maps for navigation
        if ( isGoogleMapsInstalled() )
        {
            LatLng location = machine.getPosition();
            String geoUri = "google.navigation:q=" + location.latitude + "," + location.longitude + "&mode=w";
            //String url = "http://maps.google.com/maps?daddr=" + location.latitude + "," + location.longitude + " (" + machine.getMachineName() + ")" + " &dirflg=w";
            Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( geoUri ) );
            intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
            intent.setClassName( "com.google.android.apps.maps", "com.google.android.maps.MapsActivity" );
            startActivity( intent );
        }

        mTracker.send( new HitBuilders.EventBuilder()
                .setCategory( "Navigation" )
                .setAction( "Navigate To" )
                .setLabel( machine.getMachineName() )
                .setValue( 1 )
                .build() );
    }

    public boolean isGoogleMapsInstalled()
    {
        try
        {
            ApplicationInfo info = getActivity().getPackageManager().getApplicationInfo( "com.google.android.apps.maps", 0 );
            return true;
        } catch ( PackageManager.NameNotFoundException e )
        {
            Toast.makeText( getActivity(), "Google Maps not installed", Toast.LENGTH_SHORT ).show();
            showMapsNotInstalled();
            return false;
        }

    }

    public void showMapsNotInstalled()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder( getActivity() );
        alertDialogBuilder.setMessage( "Install Google Maps" )
                .setCancelable( false )
                .setPositiveButton( "Yes", new DialogInterface.OnClickListener()
                {
                    public void onClick( DialogInterface dialog, int id )
                    {
                        Intent installMaps = new Intent( Intent.ACTION_VIEW, Uri.parse( "market://details?id=com.google.android.apps.maps" ) );
                        startActivity( installMaps );
                    }
                } );
        alertDialogBuilder.setNegativeButton( "No", new DialogInterface.OnClickListener()
        {
            public void onClick( DialogInterface dialog, int id )
            {
                dialog.dismiss();
            }
        } );
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}
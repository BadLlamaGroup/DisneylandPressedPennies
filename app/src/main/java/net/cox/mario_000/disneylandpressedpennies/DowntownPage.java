package net.cox.mario_000.disneylandpressedpennies;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.List;

import static net.cox.mario_000.disneylandpressedpennies.Data.downtownMachines;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numDowntownCoinsCollected;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numDowntownCoinsTotal;

/**
 * Created by mario_000 on 7/11/2016.
 * Description: Fragment for displaying Downtown Disney page
 */
public class DowntownPage extends Fragment implements View.OnClickListener
{
    // Layouts
    LinearLayout mapBtn;
    LinearLayout coinBook;
    LinearLayout coinList;

    // Data
    TextView amount;

    // References
    private Tracker mTracker;
    private final SharedPreference sharedPreference = new SharedPreference();

    @Override
    public void onResume()
    {
        super.onResume();
        amount.setText( numDowntownCoinsCollected + " / " + numDowntownCoinsTotal );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        View myFragmentView = inflater.inflate( R.layout.downtown, container, false );
        getActivity().setTitle( "Downtown Disney" );
        MainActivity application = ( MainActivity ) getActivity();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName( "Page - Downtown Disney" );
        mTracker.send( new HitBuilders.ScreenViewBuilder().build() );

        // Get saved coins
        List< Coin > savedCoins = sharedPreference.getCoins( getActivity().getApplicationContext() );

        // Link buttons
        ImageButton wodBtn = myFragmentView.findViewById( R.id.wodBtn );
        ImageButton pinTradersBtn = myFragmentView.findViewById( R.id.tortillaJoBtn );
        ImageButton wetzelsBtn = myFragmentView.findViewById( R.id.wetzelsBtn );
        ImageButton grandCalifornianBtn = myFragmentView.findViewById( R.id.grandCalifornianBtn );
        ImageButton disneyBtn = myFragmentView.findViewById( R.id.disneyHotelBtn );
        ImageButton paradiseHotelBtn = myFragmentView.findViewById( R.id.paradiseHotelBtn );

        // Set listeners
        wodBtn.setOnClickListener( this );
        pinTradersBtn.setOnClickListener( this );
        wetzelsBtn.setOnClickListener( this );
        grandCalifornianBtn.setOnClickListener( this );
        disneyBtn.setOnClickListener( this );
        paradiseHotelBtn.setOnClickListener( this );

        // Link textviews
        TextView txtWod = myFragmentView.findViewById( R.id.txt_wod_collected );
        TextView txtWetzels = myFragmentView.findViewById( R.id.txt_wetzels_collected );
        TextView txtPin = myFragmentView.findViewById( R.id.txt_pin_collected );
        TextView txtDisneyHotel = myFragmentView.findViewById( R.id.txt_disneyHotel_collected );
        TextView txtGrandCal = myFragmentView.findViewById( R.id.txt_grandCal_collected );
        TextView txtPierHotel = myFragmentView.findViewById( R.id.txt_pierHotel_collected );

        TextView[] listOfLands = new TextView[]{ txtWod, txtWetzels, txtPin, txtDisneyHotel, txtGrandCal, txtPierHotel };

        // Get collected total for each land
        int collectedInLand;
        int landTotal;
        for ( int landNum = 0; landNum < downtownMachines.length; landNum++ )
        {
            //Land
            collectedInLand = 0;
            landTotal = 0;
            for ( Machine mach : downtownMachines[ landNum ] )
            {
                //Machine in land
                landTotal += mach.getCoins().length;
                for ( Coin c : mach.getCoins() )
                {
                    //Coin in machine
                    if ( savedCoins.contains( c ) )
                    {
                        collectedInLand++;
                    }
                }
            }

            listOfLands[ landNum ].setText( collectedInLand + " / " + landTotal );
        }

        // Link views
        amount = myFragmentView.findViewById( R.id.amt );
        coinBook = myFragmentView.findViewById( R.id.coinBookBtn );
        mapBtn = myFragmentView.findViewById( R.id.mapBtn );
        coinList = myFragmentView.findViewById( R.id.allCoinsBtn );

        // Set listeners
        coinBook.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                CoinBookDetail fragment = new CoinBookDetail();
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

        mapBtn.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                MapsActivity fragment = new MapsActivity();
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

        coinList.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                allCoins fragment = new allCoins();
                Bundle args = new Bundle();
                args.putString( "land", "Downtown Disney" );
                fragment.setArguments( args );
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

        return myFragmentView;
    }

    @Override
    public void onClick( View view )
    {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt( "land", view.getId() );
        LandDetail fragment = new LandDetail();
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
}
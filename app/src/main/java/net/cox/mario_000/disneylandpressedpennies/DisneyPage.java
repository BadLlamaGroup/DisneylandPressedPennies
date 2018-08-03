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

import static net.cox.mario_000.disneylandpressedpennies.Data.disneyMachines;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numDisneyCoinsCollected;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numDisneyCoinsTotal;

/**
 * Created by mario_000 on 7/11/2016.
 * Description: Fragment for displaying Disney page
 */
public class DisneyPage extends Fragment implements View.OnClickListener
{
    // Layouts
    LinearLayout mapBtn;
    LinearLayout coinBook;
    LinearLayout coinList;

    // References
    private Tracker mTracker;
    private final SharedPreference sharedPreference = new SharedPreference();

    // Data
    TextView amount;


    @Override
    public void onResume()
    {
        super.onResume();
        amount.setText( numDisneyCoinsCollected + " / " + numDisneyCoinsTotal );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        View myFragmentView = inflater.inflate( R.layout.disneyland, container, false );
        getActivity().setTitle( "Disneyland" );
        MainActivity application = ( MainActivity ) getActivity();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName( "Page - Disneyland" );
        mTracker.send( new HitBuilders.ScreenViewBuilder().build() );

        // Get saved coins
        List< Coin > savedCoins = sharedPreference.getCoins( getActivity().getApplicationContext() );

        // Link buttons
        ImageButton tomorrowBtn = myFragmentView.findViewById( R.id.tomorrowBtn );
        ImageButton adventureBtn = myFragmentView.findViewById( R.id.adventureBtn );
        ImageButton frontierBtn = myFragmentView.findViewById( R.id.frontierBtn );
        ImageButton fantasyBtn = myFragmentView.findViewById( R.id.fantasyBtn );
        ImageButton mainBtn = myFragmentView.findViewById( R.id.mainBtn );
        ImageButton critterBtn = myFragmentView.findViewById( R.id.critterBtn );
        ImageButton toonBtn = myFragmentView.findViewById( R.id.toonBtn );
        ImageButton newOrleansBtn = myFragmentView.findViewById( R.id.newOrleansBtn );

        // Set listeners
        tomorrowBtn.setOnClickListener( this );
        adventureBtn.setOnClickListener( this );
        frontierBtn.setOnClickListener( this );
        fantasyBtn.setOnClickListener( this );
        mainBtn.setOnClickListener( this );
        critterBtn.setOnClickListener( this );
        toonBtn.setOnClickListener( this );
        newOrleansBtn.setOnClickListener( this );

        // Link textviews
        TextView txtAdv = myFragmentView.findViewById( R.id.txt_adventure_collected );
        TextView txtTom = myFragmentView.findViewById( R.id.txt_tomorrowland_collected );
        TextView txtFro = myFragmentView.findViewById( R.id.txt_frontierland_collected );
        TextView txtToo = myFragmentView.findViewById( R.id.txt_toontown_collected );
        TextView txtFan = myFragmentView.findViewById( R.id.txt_fantasyland_collected );
        TextView txtMai = myFragmentView.findViewById( R.id.txt_main_collected );
        TextView txtCri = myFragmentView.findViewById( R.id.txt_critter_collected );
        TextView txtOrl = myFragmentView.findViewById( R.id.txt_orleans_collected );

        TextView[] listOfLands = new TextView[]{ txtMai, txtTom, txtFan, txtToo, txtFro, txtAdv, txtCri, txtOrl };

        // Get collected total for each land
        int collectedInLand;
        int landTotal;
        for ( int landNum = 0; landNum < disneyMachines.length; landNum++ )
        {
            // Land
            collectedInLand = 0;
            landTotal = 0;
            for ( Machine mach : disneyMachines[ landNum ] )
            {
                // Machine in land
                landTotal += mach.getCoins().length;
                for ( Coin c : mach.getCoins() )
                {
                    // Coin in machine
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
                args.putString( "land", "Disneyland" );
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
        MachineDetail fragment = new MachineDetail();
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
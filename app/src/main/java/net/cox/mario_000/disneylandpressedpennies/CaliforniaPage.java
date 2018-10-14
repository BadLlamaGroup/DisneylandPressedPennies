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

import static net.cox.mario_000.disneylandpressedpennies.Data.calMachines;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numCalCoinsCollected;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numCalCoinsTotal;


/**
 * Created by mario_000 on 7/11/2016.
 * Description: Fragment for California Adventure page
 */
public class CaliforniaPage extends Fragment implements View.OnClickListener
{
    // Layouts
    LinearLayout mapBtn;
    LinearLayout coinBook;
    LinearLayout coinList;

    // Data
    TextView amount;

    // References
    private Tracker mTracker;
    final SharedPreference sharedPreference = new SharedPreference();

    @Override
    public void onResume()
    {
        super.onResume();
        amount.setText( numCalCoinsCollected + " / " + numCalCoinsTotal );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        View myFragmentView = inflater.inflate( R.layout.california, container, false );
        getActivity().setTitle( "California Adventure" );
        MainActivity application = ( MainActivity ) getActivity();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName( "Page - California Adventure" );
        mTracker.send( new HitBuilders.ScreenViewBuilder().build() );

        // Get saved coins
        List< Coin > savedCoins = sharedPreference.getCoins( getActivity().getApplicationContext() );

        // Link buttons
        ImageButton buenaBtn = myFragmentView.findViewById( R.id.buenaBtn );
        ImageButton hollywoodBtn = myFragmentView.findViewById( R.id.hollywoodBtn );
        ImageButton grizzlyPeakAirfieldsBtn = myFragmentView.findViewById( R.id.grizzlyPeakAirfieldsBtn );
        ImageButton grizzlyPeakAreaBtn = myFragmentView.findViewById( R.id.grizzlyPeakAreaBtn );
        ImageButton paradisePierBtn = myFragmentView.findViewById( R.id.pixarPierBtn );
        ImageButton carsBtn = myFragmentView.findViewById( R.id.carsBtn );
        
        // Set listeners
        buenaBtn.setOnClickListener( this );
        hollywoodBtn.setOnClickListener( this );
        grizzlyPeakAirfieldsBtn.setOnClickListener( this );
        grizzlyPeakAreaBtn.setOnClickListener( this );
        paradisePierBtn.setOnClickListener( this );
        carsBtn.setOnClickListener( this );

        // Link textviews
        TextView txtBuena = myFragmentView.findViewById( R.id.txt_buena_collected );
        TextView txtAirfields = myFragmentView.findViewById( R.id.txt_airfields_collected );
        TextView txtRec = myFragmentView.findViewById( R.id.txt_rec_collected );
        TextView txtParadise = myFragmentView.findViewById( R.id.txt_paradise_collected );
        TextView txtHollywood = myFragmentView.findViewById( R.id.txt_hollywood_collected );
        TextView txtCars = myFragmentView.findViewById( R.id.txt_cars_collected );

        TextView[] listOfLands = new TextView[]{ txtBuena, txtAirfields, txtRec, txtParadise, txtHollywood, txtCars };

        // Get collected total for each land
        int collectedInLand;
        int landTotal;
        for ( int landNum = 0; landNum < calMachines.length; landNum++ )
        {
            //Land
            collectedInLand = 0;
            landTotal = 0;
            for ( Machine mach : calMachines[ landNum ] )
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
                args.putString( "land", "California Adventure" );
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
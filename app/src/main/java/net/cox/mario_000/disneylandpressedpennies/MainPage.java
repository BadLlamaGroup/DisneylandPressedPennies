package net.cox.mario_000.disneylandpressedpennies;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jinatonic.confetti.ConfettiManager;
import com.github.jinatonic.confetti.ConfettiSource;
import com.github.jinatonic.confetti.ConfettoGenerator;
import com.github.jinatonic.confetti.confetto.BitmapConfetto;
import com.github.jinatonic.confetti.confetto.Confetto;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.List;
import java.util.Random;

import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numArcCoinsCollected;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numCalCoinsCollected;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numCalCoinsTotal;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numCurrentCoinsCollected;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numCurrentCoinsTotal;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numDisneyCoinsCollected;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numDisneyCoinsTotal;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numDowntownCoinsCollected;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numDowntownCoinsTotal;


/**
 * Created by mario_000 on 7/11/2016.
 * Description: Main page for app
 */
public class MainPage extends Fragment implements View.OnClickListener
{
    // Textviews
    TextView totalCoins;
    TextView disneyCollected;
    TextView calCollected;
    TextView downtownCollected;

    // Data
    List< Coin > savedCoins;
    int numClicked;

    // References
    SharedPreference sharedPreference = new SharedPreference();
    MainActivity application;
    private Tracker mTracker;

    @Override
    public void onResume()
    {
        super.onResume();

        // Get saved coins
        savedCoins = sharedPreference.getCoins( getActivity().getApplicationContext() );

        // Update coins collected amount
        numCurrentCoinsCollected = savedCoins.size() - numArcCoinsCollected;

        // Set collected amounts
        totalCoins.setText( numCurrentCoinsCollected + " / " + numCurrentCoinsTotal );
        disneyCollected.setText( numDisneyCoinsCollected + " / " + numDisneyCoinsTotal );
        calCollected.setText( numCalCoinsCollected + " / " + numCalCoinsTotal );
        downtownCollected.setText( numDowntownCoinsCollected + " / " + numDowntownCoinsTotal );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState )
    {
        View myFragmentView = inflater.inflate( R.layout.main_page, container, false );
        getActivity().setTitle( "Main Page" );

        // Link buttons
        totalCoins = myFragmentView.findViewById( R.id.txt_total_collected );
        disneyCollected = myFragmentView.findViewById( R.id.txt_disney_collected );
        calCollected = myFragmentView.findViewById( R.id.txt_cal_collected );
        downtownCollected = myFragmentView.findViewById( R.id.txt_downtown_collected );

        // Link layouts and set listeners
        LinearLayout disneyLayout = myFragmentView.findViewById( R.id.disneyMain );
        disneyLayout.setOnClickListener( this );

        LinearLayout californiaLayout = myFragmentView.findViewById( R.id.californiaMain );
        californiaLayout.setOnClickListener( this );

        LinearLayout downtownLayout = myFragmentView.findViewById( R.id.downtownMain );
        downtownLayout.setOnClickListener( this );

        // Get saved coins
        savedCoins = sharedPreference.getCoins( getActivity().getApplicationContext() );

        // Update collected coin amount
        numCurrentCoinsCollected = savedCoins.size() - numArcCoinsCollected;

        // Set collected amounts
        totalCoins.setText( numCurrentCoinsCollected + " / " + numCurrentCoinsTotal );
        disneyCollected.setText( numDisneyCoinsCollected + " / " + numDisneyCoinsTotal );
        calCollected.setText( numCalCoinsCollected + " / " + numCalCoinsTotal );
        downtownCollected.setText( numDowntownCoinsCollected + " / " + numDowntownCoinsTotal );

        // Set hidden mickey listener
        View appTitle = myFragmentView.findViewById( R.id.txtName );
        numClicked = 0;

        appTitle.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                if ( numClicked == 2 )
                {
                    application = ( MainActivity ) getActivity();
                    mTracker = application.getDefaultTracker();
                    mTracker.send( new HitBuilders.EventBuilder()
                            .setCategory( "Hidden Mickeys" )
                            .setAction( "Activated" )
                            .setValue( 1 )
                            .build() );
                    Toast.makeText( getActivity().getApplicationContext(), "HIDDEN MICKEYS!!!", Toast.LENGTH_SHORT ).show();
                    final ConfettoGenerator confettoGenerator = new ConfettoGenerator()
                    {
                        @Override
                        public Confetto generateConfetto( Random random )
                        {
                            final Bitmap bitmap = BitmapFactory.decodeResource( getResources(), R.drawable.mickey );
                            return new BitmapConfetto( bitmap );
                        }
                    };
                    final ConfettiSource confettiSource = new ConfettiSource( 0, -200, container.getWidth(), -200 );
                    new ConfettiManager( getActivity().getApplicationContext(), confettoGenerator, confettiSource, container )
                            .setEmissionDuration( 6000 )
                            .setEmissionRate( 5 )
                            .setVelocityX( 0, 50 )
                            .setVelocityY( 500 )
                            .setRotationalVelocity( 180, 180 )
                            .animate();
                    numClicked = 0;
                } else
                {
                    Toast.makeText( getActivity().getApplicationContext(), "Only " + ( 2 - numClicked ) + " more click(s)!", Toast.LENGTH_SHORT ).show();
                    numClicked++;
                }
            }
        } );

        /* CHECK FOR DUPLICATE COINS */

//        List listCoins = new ArrayList(  );
//        List duplicateCoins = new ArrayList(  );
//
//        for ( Machine[][] parkMac : allMachines ){
//            for ( Machine[] areaMac : parkMac ){
//                for ( Machine mac : areaMac ){
//                    for ( Coin coin : mac.getCoins() ){
//                        if( listCoins.contains( coin.getTitleCoin() ) && !duplicateCoins.contains( coin.getTitleCoin() ) ){
//                            duplicateCoins.add( coin.getTitleCoin() );
//                        }
//                        else {
//                            listCoins.add( coin.getTitleCoin() );
//                        }
//
//                    }
//                }
//            }
//        }

        return myFragmentView;
    }

    @Override
    public void onClick( View landSelected )
    {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        int land = landSelected.getId();
        Fragment fragment = new Fragment();
        switch ( land )
        {
            case ( R.id.disneyMain ):
                fragment = new DisneyPage();
                break;
            case ( R.id.californiaMain ):
                fragment = new CaliforniaPage();
                break;
            case ( R.id.downtownMain ):
                fragment = new DowntownPage();
                break;
        }
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
package net.cox.mario_000.disneylandpressedpennies;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import static net.cox.mario_000.disneylandpressedpennies.MainActivity.fragmentManager;

/**
 * Created by mario_000 on 2/10/2017.
 */

public class about extends Fragment
{
    // References
    private Tracker mTracker;

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        getActivity().setTitle( "About Disneyland Pressed Coins" );
        View view = inflater.inflate( R.layout.about, container, false );
        TextView txtVersion = view.findViewById( R.id.txtVersion );
        txtVersion.setText( "Version: " + BuildConfig.VERSION_NAME );

        MainActivity application = ( MainActivity ) getActivity();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName( "Page - About" );
        mTracker.send( new HitBuilders.ScreenViewBuilder().build() );

        Button btnExit = view.findViewById( R.id.btnAboutExit );
        btnExit.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                MainPage fragment = new MainPage();
                fragmentTransaction.addToBackStack( null );
                fragmentTransaction.replace( R.id.mainFrag, fragment, "main" );
                fragmentTransaction.commit();
            }
        } );

        return view;
    }
}
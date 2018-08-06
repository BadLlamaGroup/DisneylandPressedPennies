package net.cox.mario_000.disneylandpressedpennies;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mario_000 on 2/15/2017.
 */

public class search extends Fragment implements Data
{
    // Views
    ListView foundCoinsList;
    TextView numFound;
    TextView noResults;
    EditText editSearch;
    Button searchBtn;

    // Lists
    ArrayList foundCoins;
    List< CustomCoin > customCoins;

    // References
    private final SharedPreference sharedPreference = new SharedPreference();
    ListAdapter mCoinAdapter;
    Parcelable state;
    private Tracker mTracker;

    @Override
    public void onResume()
    {
        if ( foundCoins != null && foundCoinsList != null )
        {
            numFound.setText( String.valueOf( foundCoins.size() ) );
            mCoinAdapter = new ListAdapter( getActivity(), R.layout.row, foundCoins );
            foundCoinsList.setAdapter( mCoinAdapter );
        }
        if ( state != null )
        {
            foundCoinsList.onRestoreInstanceState( state );
        }

        super.onResume();
    }

    @Override
    public void onStop()
    {
        state = foundCoinsList.onSaveInstanceState();
        super.onStop();
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        View view = inflater.inflate( R.layout.search, container, false );
        getActivity().setTitle( "Search" );
        MainActivity application = ( MainActivity ) getActivity();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName( "Page - Search" );
        mTracker.send( new HitBuilders.ScreenViewBuilder().build() );

        // Link views
        searchBtn = view.findViewById( R.id.btn_search );
        editSearch = view.findViewById( R.id.search_key );
        foundCoinsList = view.findViewById( R.id.searchList );
        numFound = view.findViewById( R.id.numFound );
        noResults = view.findViewById( R.id.noneFound );

        // Get custom coins
        customCoins = sharedPreference.getCustomCoins( view.getContext() );

        // Hide keyboard
        final InputMethodManager inputMethodManager = ( InputMethodManager ) getActivity().getSystemService( Context.INPUT_METHOD_SERVICE );
        editSearch.setOnKeyListener( new View.OnKeyListener()
        {
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if ( event.getAction() == KeyEvent.ACTION_DOWN )
                {
                    switch ( keyCode )
                    {
                        case KeyEvent.KEYCODE_ENTER:
                            searchBtn.callOnClick();
                            inputMethodManager.hideSoftInputFromWindow( editSearch.getWindowToken(), 0 );
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        } );

        // Set listeners
        editSearch.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                // Reset search key
                editSearch.setText( "" );
            }
        } );

        searchBtn.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {

                foundCoins = new ArrayList<>();

                for ( Machine[] macs : disneyMachines )
                {
                    for ( Machine mac : macs )
                    {
                        for ( Coin c : mac.getCoins() )
                        {
                            if ( c.getTitleCoin().toLowerCase().contains( editSearch.getText().toString().toLowerCase() ) )
                            {
                                foundCoins.add( c );
                            }
                        }
                    }
                }
                for ( Machine[] macs : calMachines )
                {
                    for ( Machine mac : macs )
                    {
                        for ( Coin c : mac.getCoins() )
                        {
                            if ( c.getTitleCoin().toLowerCase().contains( editSearch.getText().toString().toLowerCase() ) )
                            {
                                foundCoins.add( c );
                            }
                        }
                    }
                }
                for ( Machine[] macs : downtownMachines )
                {
                    for ( Machine mac : macs )
                    {
                        for ( Coin c : mac.getCoins() )
                        {
                            if ( c.getTitleCoin().toLowerCase().contains( editSearch.getText().toString().toLowerCase() ) )
                            {
                                foundCoins.add( c );
                            }
                        }
                    }
                }
                for ( Machine[] macs : retiredMachines )
                {
                    for ( Machine mac : macs )
                    {
                        for ( Coin c : mac.getCoins() )
                        {
                            if ( c.getTitleCoin().toLowerCase().contains( editSearch.getText().toString().toLowerCase() ) )
                            {
                                foundCoins.add( c );
                            }
                        }
                    }
                }

                for ( CustomCoin coin : customCoins )
                {
                    if ( coin.getTitleCoin().toLowerCase().contains( editSearch.getText().toString().toLowerCase() ) )
                    {
                        foundCoins.add( coin );
                    }
                }

                // Set amount found
                numFound.setText( String.valueOf( foundCoins.size() ) );
                if ( foundCoins.size() == 0 )
                {
                    foundCoinsList.setVisibility( View.INVISIBLE );
                    noResults.setVisibility( View.VISIBLE );
                } else
                {
                    foundCoinsList.setVisibility( View.VISIBLE );
                    noResults.setVisibility( View.INVISIBLE );

                    // Create coin adapter
                    mCoinAdapter = new ListAdapter( getActivity(), R.layout.row, foundCoins );
                    foundCoinsList.setAdapter( mCoinAdapter );
                }
                inputMethodManager.hideSoftInputFromWindow( editSearch.getWindowToken(), 0 );
            }
        } );

        TextView txtParkBanner = view.findViewById( R.id.parkBanner );
        txtParkBanner.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                String url = "http://www.parkpennies.com";
                Intent intent = new Intent( Intent.ACTION_VIEW );
                intent.setData( Uri.parse( url ) );
                startActivity( intent );
            }
        } );

        return view;
    }
}
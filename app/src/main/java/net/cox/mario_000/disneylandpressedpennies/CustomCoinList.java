package net.cox.mario_000.disneylandpressedpennies;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by mario_000 on 7/14/2018.
 */

public class CustomCoinList extends Fragment implements Data, AdapterView.OnItemSelectedListener
{

    /**
     * Created by mario_000 on 6/25/2016.
     * Description: Fragment for displaying coins in current machine
     */

    // References
    private ListAdapter mCoinAdapter;
    private SharedPreference sharedPreference = new SharedPreference();
    private Tracker mTracker;

    // Views
    private ImageView addCoinBtn;
    private TextView amtCollectedCustom;
    private ListView listCoins;

    // Data
    private List< Coin > customCoins;
    private ArrayList customCoinsList = new ArrayList();

    // Spinner
    private Spinner sortTypeSpinner;
    private int selection = 0;
    private final int SPINNER_A_Z = 0;
    private final int SPINNER_Z_A = 1;
    private final int SPINNER_OLD_NEW = 2;
    private final int SPINNER_NEW_OLD = 3;
    private final int SPINNER_PARK = 4;

    @Override
    public void onResume()
    {
        // Update coin list
        customCoins = sharedPreference.getCustomCoins( getActivity() );

        // Update coin adapter
        mCoinAdapter.notifyDataSetChanged();
        listCoins.setSelection( selection );
        super.onResume();
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        View view = inflater.inflate( R.layout.custom_coin_list, container, false );
        MainActivity application = ( MainActivity ) getActivity();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName("Page - Custom Coin List");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        getActivity().setTitle( "Custom Coin List" );

        customCoins = sharedPreference.getCustomCoins( getActivity() );

        // Link views
        listCoins = view.findViewById( R.id.listCustomCoins );
        sortTypeSpinner = view.findViewById( R.id.customSortType );
        addCoinBtn = view.findViewById( R.id.customAdd );
        amtCollectedCustom = view.findViewById( R.id.customCollected );

        // Set listeners
        addCoinBtn.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                CustomCoinFragment fragment = new CustomCoinFragment();

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

        sortTypeSpinner.setOnItemSelectedListener( this );

        // Create coin adapter
        customCoinsList.clear();
        customCoinsList.addAll( customCoins );
        mCoinAdapter = new ListAdapter( getActivity(), R.layout.row, customCoinsList );
        listCoins.setAdapter( mCoinAdapter );

        // Create spinner adapter
        final ArrayAdapter< CharSequence > parkAdapter = ArrayAdapter.createFromResource( getActivity(), R.array.Custom_Sort_Type, android.R.layout.simple_spinner_dropdown_item );
        parkAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        sortTypeSpinner.setAdapter( parkAdapter );

        // Set data
        amtCollectedCustom.setText( String.valueOf( customCoins.size() ) );

        return view;
    }

    @Override
    public void onItemSelected( AdapterView< ? > adapterView, View view, final int pos, long l )
    {
        SharedPreferences sharedPref = getActivity().getSharedPreferences( "FileName", 0 );
        SharedPreferences.Editor prefEditor = sharedPref.edit();
        prefEditor.putInt( "userChoiceSpinner", pos );
        prefEditor.apply();
        Collections.sort( customCoins, new Comparator< Coin >()
        {
            @Override
            public int compare( Coin o1, Coin o2 )
            {
                switch ( pos )
                {
                    case SPINNER_A_Z:
                        return o1.getTitleCoin().compareTo( o2.getTitleCoin() );
                    case SPINNER_Z_A:
                        return o2.getTitleCoin().compareTo( o1.getTitleCoin() );
                    case SPINNER_OLD_NEW:
                        return o1.getDateCollected().compareTo( o2.getDateCollected() );
                    case SPINNER_NEW_OLD:
                        return o2.getDateCollected().compareTo( o1.getDateCollected() );
                    default:
                        return 0;
                }
            }
        } );

        mCoinAdapter.removeSeparatorItem();
        mCoinAdapter.clear();
        mCoinAdapter.addAll( customCoins );
        mCoinAdapter.notifyDataSetChanged();


        if ( pos == SPINNER_PARK )
        {
            mCoinAdapter.clear();
            // Sort by coin name A-Z
            Collections.sort( customCoins, new Comparator< Coin >()
            {
                @Override
                public int compare( Coin o1, Coin o2 )
                {
                    return o1.getTitleCoin().compareTo( o2.getTitleCoin() );
                }
            } );

            // Sort by land name A-Z
            Collections.sort( customCoins, new Comparator< Coin >()
            {
                @Override
                public int compare( Coin o1, Coin o2 )
                {
                    return o1.getCoinPark().compareTo( o2.getCoinPark() );
                }
            } );

            // Add land titles and coins from land
            for ( int i = 0; i < customCoins.size(); i++ )
            {
                String park = customCoins.get( i ).getCoinPark();
                if ( !mCoinAdapter.duplicateSeparator( park ) )
                {
                    mCoinAdapter.addSeparatorItem( park );
                }
                mCoinAdapter.add( customCoins.get( i ) );
            }

            mCoinAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onNothingSelected( AdapterView< ? > adapterView )
    {

    }
}
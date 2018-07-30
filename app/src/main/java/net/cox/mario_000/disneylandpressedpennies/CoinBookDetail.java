package net.cox.mario_000.disneylandpressedpennies;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;

import org.askerov.dynamicgrid.DynamicGridView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static net.cox.mario_000.disneylandpressedpennies.Data.calMachines;
import static net.cox.mario_000.disneylandpressedpennies.Data.defaultMac;
import static net.cox.mario_000.disneylandpressedpennies.Data.disneyMachines;
import static net.cox.mario_000.disneylandpressedpennies.Data.downtownMachines;
import static net.cox.mario_000.disneylandpressedpennies.Data.retiredMachines;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.find;

/**
 * Created by mario_000 on 7/6/2016.
 * Description: Fragment for coin book feature. Displays all collected coins with sorting options.
 */
public class CoinBookDetail extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener
{
    // References
    private final SharedPreference sharedPreference = new SharedPreference();
    private Tracker mTracker;

    // Views
    private Button displayBtn;
    private TextView coinBookDetailsHeader;
    private ListView listFav;
    private DynamicGridView gridView;
    private GridViewAdapter gridViewAdapter;
    private ListAdapter listAdapter;
    private TextView totalCoins;

    // Spinner
    private Spinner sortType = null;
    private final int SPINNER_A_Z = 0;
    private final int SPINNER_Z_A = 1;
    private final int SPINNER_OLD_NEW = 2;
    private final int SPINNER_NEW_OLD = 3;
    private final int SPINNER_CUSTOM = 4;
    private final int SPINNER_LAND = 5;

    // Lists
    private List< Coin > customOrderCoins;
    private List< Coin > spinnerCustomOrderList;
    private List< Coin > savedCoins;
    private List< Coin > customCoins;

    // Data
    private boolean coinDetailsDisplayed;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        View view = inflater.inflate( R.layout.coin_book, container, false );
        getActivity().setTitle( "Coin Book" );
        MainActivity application = ( MainActivity ) getActivity();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName( "Page - Coin Book" );
        mTracker.send( new HitBuilders.ScreenViewBuilder().build() );

        // Get collected coins list and machines
        customOrderCoins = sharedPreference.getCoins( view.getContext() );
        customCoins = sharedPreference.getCustomCoins( view.getContext() );
        savedCoins = sharedPreference.getCoins( view.getContext() );

        // Add custom coins
        customOrderCoins.addAll( customCoins );
        savedCoins.addAll( customCoins );

        // Link views
        sortType = view.findViewById( R.id.coinBookSortType );
        displayBtn = view.findViewById( R.id.picBut );
        listFav = view.findViewById( R.id.listFav );
        coinBookDetailsHeader = view.findViewById( R.id.coin_book_details_header );
        totalCoins = view.findViewById( R.id.txt_total );
        gridView = view.findViewById( R.id.dynamic_grid );

        totalCoins.setText( String.valueOf( savedCoins.size() ) );

        // Set spinner adapter
        ArrayAdapter< CharSequence > adapter = ArrayAdapter.createFromResource( getActivity(),
                R.array.Sort_Type, android.R.layout.simple_spinner_item );
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        // Apply the adapter to the sortType
        sortType.setAdapter( adapter );
        sortType.setOnItemSelectedListener( this );
        SharedPreferences sharedPref = getActivity().getSharedPreferences( "FileName", MODE_PRIVATE );
        final int spinnerValue = sharedPref.getInt( "userChoiceSpinner", -1 );
        if ( spinnerValue != -1 )
        {
            // set the value of the sortType
            sortType.setSelection( spinnerValue );
        }

        // Create list adapter for collected coins list
        ArrayList coinDetailList = new ArrayList();
        coinDetailList.addAll( savedCoins );
        listAdapter = new ListAdapter( view.getContext(), R.layout.row, coinDetailList );
        //listFav.setOnItemClickListener( this );
        listFav.setAdapter( listAdapter );

        // Create gridView adapter
        gridViewAdapter = new GridViewAdapter( getActivity(), savedCoins, 3 );
        gridView.setAdapter( gridViewAdapter );

        // Grid view listeners
        gridView.setOnItemLongClickListener( new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick( AdapterView parent, View view, int position, long id )
            {
                if ( sortType.getSelectedItemPosition() == SPINNER_CUSTOM )
                {
                    gridView.startEditMode( position );
                } else
                {
                    Toast.makeText( getActivity(), "Switch to custom", Toast.LENGTH_SHORT ).show();
                }
                return true;
            }
        } );
        gridView.setOnItemClickListener( new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick( AdapterView< ? > adapterView, View view, int pos, long l )
            {
                Bundle args = new Bundle();

                if ( sortType.getSelectedItemPosition() == SPINNER_CUSTOM )
                {
                    args.putSerializable( "ARRAYLIST", ( Serializable ) spinnerCustomOrderList );
                } else
                {
                    args.putSerializable( "ARRAYLIST", ( Serializable ) savedCoins );
                }

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                CoinBookBigImage fragment = new CoinBookBigImage();
                args.putInt( "pos", pos );
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

        gridView.setOnDropListener( new DynamicGridView.OnDropListener()
        {
            @Override
            public void onActionDrop()
            {
                gridView.stopEditMode();
                spinnerCustomOrderList.clear();
                // Rebuild custom list based on edits
                for ( int itemPos = 0; itemPos < gridView.getCount(); itemPos++ )
                {
                    spinnerCustomOrderList.add( ( Coin ) gridView.getItemAtPosition( itemPos ) );
                }
                SharedPreferences settings = getActivity().getSharedPreferences( "File", Context.MODE_PRIVATE );
                SharedPreferences.Editor editor = settings.edit();
                Gson gson = new Gson();
                String jsonCoins = gson.toJson( spinnerCustomOrderList );
                editor.putString( "custom", jsonCoins );
                editor.apply();
            }
        } );

        // Switch display between pictures and list
        displayBtn.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                if ( listFav.getVisibility() == View.VISIBLE )
                {
                    displayPictures();
                } else
                {
                    displayList();
                }
            }
        } );

        // ParkPennies promotion
        TextView txtParkBanner = view.findViewById( R.id.parkBanner );
        txtParkBanner.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                String url = "http://www.parkpennies.com";
                Intent i = new Intent( Intent.ACTION_VIEW );
                i.setData( Uri.parse( url ) );
                startActivity( i );

            }
        } );

        return view;
    }

    @Override
    public void onPause()
    {
        super.onPause();
        // Save which screen is shown
        coinDetailsDisplayed = listFav.getVisibility() == View.VISIBLE;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        // Show previous screen before pause
        if ( coinDetailsDisplayed )
        {
            displayList();
        }
        totalCoins.setText( String.valueOf( savedCoins.size() ) );
    }

    private void displayPictures()
    {
        getActivity().setTitle( "Coin Book" );
        listFav.setVisibility( View.INVISIBLE );
        gridView.setVisibility( View.VISIBLE );
        coinBookDetailsHeader.setText( "Display Details" );
        displayBtn.setBackground( ContextCompat.getDrawable( getActivity(), R.drawable.list ) );

        if ( sortType.getSelectedItemPosition() == SPINNER_CUSTOM )
        {
            gridViewAdapter.clear();
            gridViewAdapter.add( spinnerCustomOrderList );
            gridViewAdapter.notifyDataSetChanged();
        } else
        {
            gridViewAdapter.clear();
            gridViewAdapter.add( savedCoins );
            gridViewAdapter.notifyDataSetChanged();
        }
    }

    public void updateImages()
    {
        for ( Coin coin : spinnerCustomOrderList )
        {
            //outerloop:
            for ( Machine[] m : disneyMachines )
            {
                for ( Machine mach : m )
                {
                    for ( Coin c : mach.getCoins() )
                    {
                        if ( c.getTitleCoin().equals( coin.getTitleCoin() ) )
                        {
                            if ( !coin.getCoinFrontImg().equals( c.getCoinFrontImg() ) )
                            {
                                coin.setCoinFrontImg( c.getCoinFrontImg() );
                                //                    break outerloop;
                            }
                        }
                    }
                }
            }
            //outerloop:
            for ( Machine[] m : calMachines )
            {
                for ( Machine mach : m )
                {
                    for ( Coin c : mach.getCoins() )
                    {
                        if ( c.getTitleCoin().equals( coin.getTitleCoin() ) )
                        {
                            if ( !coin.getCoinFrontImg().equals( c.getCoinFrontImg() ) )
                            {
                                coin.setCoinFrontImg( c.getCoinFrontImg() );
                                //                  break outerloop;
                            }
                        }
                    }
                }
            }
            //outerloop:
            for ( Machine[] m : downtownMachines )
            {
                for ( Machine mach : m )
                {
                    for ( Coin c : mach.getCoins() )
                    {
                        if ( c.getTitleCoin().equals( coin.getTitleCoin() ) )
                        {
                            if ( !coin.getCoinFrontImg().equals( c.getCoinFrontImg() ) )
                            {
                                coin.setCoinFrontImg( c.getCoinFrontImg() );
                                //                    break outerloop;
                            }
                        }
                    }
                }
            }

            //outerloop:
            for ( Machine[] m : retiredMachines )
            {
                for ( Machine mach : m )
                {
                    for ( Coin c : mach.getCoins() )
                    {
                        if ( c.getTitleCoin().equals( coin.getTitleCoin() ) )
                        {
                            if ( !coin.getCoinFrontImg().equals( c.getCoinFrontImg() ) )
                            {
                                coin.setCoinFrontImg( c.getCoinFrontImg() );
                                //                  break outerloop;
                            }
                        }
                    }
                }
            }


            SharedPreferences settings = getActivity().getSharedPreferences( "File", Context.MODE_PRIVATE );
            SharedPreferences.Editor editor = settings.edit();
            Gson gson = new Gson();
            String jsonCoins = gson.toJson( spinnerCustomOrderList );
            editor.putString( "custom", jsonCoins );
            editor.apply();
        }
    }

    private void displayList()
    {
        getActivity().setTitle( "Coin Book - Details" );
        coinBookDetailsHeader.setText( "Display Book" );
        displayBtn.setBackground( ContextCompat.getDrawable( getActivity(), R.drawable.book ) );
        gridView.setVisibility( View.INVISIBLE );
        listFav.setVisibility( View.VISIBLE );

        if ( sortType.getSelectedItemPosition() == SPINNER_CUSTOM )
        {
            listAdapter.clear();
            listAdapter.addAll( spinnerCustomOrderList );
            listAdapter.notifyDataSetChanged();
        } else
        {
            listAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick( AdapterView< ? > parent, View view, int position, long id )
    {
        /*Bundle bundle = new Bundle();
        Gson gson = new Gson();
        String jsonCoin;
        String jsonMachine;
        Coin coin;

        // Get coin information based on sorting method
        if ( listAdapter.getItem( position ) instanceof Coin )
        {
            if ( sortType.getSelectedItemPosition() == SPINNER_CUSTOM )
            {
                coin = spinnerCustomOrderList.get( position );
                jsonCoin = gson.toJson( spinnerCustomOrderList.get( position ) );
                jsonMachine = gson.toJson( find( spinnerCustomOrderList.get( position ) ) );
            } else if ( sortType.getSelectedItemPosition() == SPINNER_LAND )
            {
                coin = listAdapter.getItem( position );
                jsonCoin = gson.toJson( listAdapter.getItem( position ) );
                jsonMachine = gson.toJson( find( listAdapter.getItem( position ) ) );
            } else
            {
                coin = savedCoins.get( position );
                jsonCoin = gson.toJson( savedCoins.get( position ) );
                jsonMachine = gson.toJson( find( savedCoins.get( position ) ) );
            }


            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(
                    R.animator.fade_in,
                    R.animator.fade_out,
                    R.animator.fade_in,
                    R.animator.fade_out );

            if ( customCoins.contains( coin ) )
            {
                CustomCoinFragment fragment = new CustomCoinFragment();
                bundle.putString( "selectedCoin", jsonCoin );
                fragment.setArguments( bundle );
                fragmentTransaction.replace( R.id.mainFrag, fragment );

            } else
            {
                singleCoin fragment = new singleCoin();
                bundle.putString( "selectedCoin", jsonCoin );
                bundle.putString( "selectedMachine", jsonMachine );
                fragment.setArguments( bundle );
                fragmentTransaction.replace( R.id.mainFrag, fragment );
            }

            fragmentTransaction.addToBackStack( null );
            fragmentTransaction.commit();


        }*/
    }

    @Override
    // Sort method
    public void onItemSelected( AdapterView< ? > adapterView, View view, final int pos, long id )
    {
        SharedPreferences sharedPref = getActivity().getSharedPreferences( "FileName", 0 );
        SharedPreferences.Editor prefEditor = sharedPref.edit();
        prefEditor.putInt( "userChoiceSpinner", pos );
        prefEditor.apply();
        Collections.sort( savedCoins, new Comparator< Coin >()
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
        gridViewAdapter.clear();
        gridViewAdapter.add( savedCoins );
        gridViewAdapter.notifyDataSetChanged();

        listAdapter.removeSeparatorItem();
        listAdapter.clear();
        listAdapter.addAll( savedCoins );
        listAdapter.notifyDataSetChanged();


        if ( pos == SPINNER_CUSTOM )
        {
            SharedPreferences settings = getActivity().getSharedPreferences( "File", Context.MODE_PRIVATE );

            if ( settings.contains( "custom" ) )
            {
                String jsonFavorites = settings.getString( "custom", null );
                Gson gson = new Gson();
                Coin[] customCoins = gson.fromJson( jsonFavorites, Coin[].class );
                spinnerCustomOrderList = Arrays.asList( customCoins );
                spinnerCustomOrderList = new ArrayList<>( spinnerCustomOrderList );

                if ( customOrderCoins.size() != spinnerCustomOrderList.size() )
                {
                    for ( int i = customOrderCoins.size() - 1; i >= 0; i-- )
                    {
                        if ( !spinnerCustomOrderList.contains( customOrderCoins.get( i ) ) )
                        {
                            spinnerCustomOrderList.add( customOrderCoins.get( i ) );
                        }
                    }
                }

                for ( int i = 0; i < spinnerCustomOrderList.size(); i++ )
                {
                    if ( !customOrderCoins.contains( spinnerCustomOrderList.get( i ) ) )
                    {
                        spinnerCustomOrderList.remove( i );
                    }
                }

                SharedPreferences.Editor editor = settings.edit();
                String jsonCoins = gson.toJson( spinnerCustomOrderList );
                editor.putString( "custom", jsonCoins );
                editor.apply();

                updateImages();
                gridViewAdapter.clear();
                gridViewAdapter.add( spinnerCustomOrderList );
                gridViewAdapter.notifyDataSetChanged();
                listAdapter.clear();
                listAdapter.addAll( spinnerCustomOrderList );
                listAdapter.notifyDataSetChanged();
            } else
            {
                spinnerCustomOrderList = new ArrayList<>();
                spinnerCustomOrderList.addAll( savedCoins );
            }
        }

        if ( pos == SPINNER_LAND )
        {
            listAdapter.clear();


            // Sort by machine name A-Z
            Collections.sort( savedCoins, new Comparator< Coin >()
            {
                @Override
                public int compare( Coin o1, Coin o2 )
                {
                    Machine mac1 = find( o1 );
                    Machine mac2 = find( o2 );
                    if ( mac1 != defaultMac[ 0 ] && mac2 != defaultMac[ 0 ] )
                    {
                        return mac1.getMachineName().compareTo( mac2.getMachineName() );
                    } else if ( mac1 == defaultMac[ 0 ] )
                    {
                        return o1.getTitleCoin().compareTo( mac2.getMachineName() );
                    } else if ( mac2 == defaultMac[ 0 ] )
                    {
                        return mac1.getMachineName().compareTo( o2.getTitleCoin() );
                    } else
                    {
                        return o1.getTitleCoin().compareTo( o2.getTitleCoin() );
                    }
                }
            } );

            // Sort by land name A-Z
            Collections.sort( savedCoins, new Comparator< Coin >()
            {
                @Override
                public int compare( Coin o1, Coin o2 )
                {
                    Machine mac1 = find( o1 );
                    Machine mac2 = find( o2 );
                    if ( mac1 != defaultMac[ 0 ] && mac2 != defaultMac[ 0 ] )
                    {
                        return mac1.getLand().compareTo( mac2.getLand() );
                    } else if ( mac1 == defaultMac[ 0 ] )
                    {
                        return o1.getCoinPark().compareTo( mac2.getLand() );
                    } else if ( mac2 == defaultMac[ 0 ] )
                    {
                        return mac1.getLand().compareTo( o2.getCoinPark() );
                    } else
                    {
                        return o1.getCoinPark().compareTo( o2.getCoinPark() );
                    }
                }
            } );

            // Add land titles and coins from land
            for ( int i = 0; i < savedCoins.size(); i++ )
            {
                String land;
                if ( customCoins.contains( savedCoins.get( i ) ) )
                {
                    land = "Custom Coins";
                    /*land = savedCoins.get( i ).getCoinPark();*/
                } else
                {
                    land = find( savedCoins.get( i ) ).getLand();
                }
                if ( !listAdapter.duplicateSeparator( land ) )
                {
                    listAdapter.addSeparatorItem( land );
                }
                listAdapter.add( savedCoins.get( i ) );
            }
            gridViewAdapter.clear();
            gridViewAdapter.add( savedCoins );
            gridViewAdapter.notifyDataSetChanged();
            listAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onNothingSelected( AdapterView< ? > adapterView )
    {

    }

}
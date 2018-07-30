package net.cox.mario_000.disneylandpressedpennies;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import static net.cox.mario_000.disneylandpressedpennies.MainActivity.CALIFORNIA_ADVENTURE;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.DISNEYLAND;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.DOWNTOWN_DISNEY;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.RETIRED;
import static net.cox.mario_000.disneylandpressedpennies.R.id.view_pager;


/**
 * Created by mario_000 on 9/26/2016.
 * Description: Fragment for displaying all coins from all machines for each park.
 */

public class allCoins extends Fragment implements Data, View.OnClickListener
{
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton disneylandFAB, californiaFAB, downtownFAB, retiredFAB;
    ViewPager viewPager;
    TabLayout tabLayout;
    Vector< View > pages;
    List< ListView > lists;
    Parcelable state;
    Machine[][] mach;
    int pos;
    private Tracker mTracker;
    private final SharedPreference sharedPreference = new SharedPreference();
    private String selectedLand;



    @Override
    public void onStop()
    {
        pos = tabLayout.getSelectedTabPosition();
        ListView l = ( ListView ) pages.get( pos );
        state = l.onSaveInstanceState();
        super.onStop();
    }


    @Override
    public void onResume()
    {
        if ( mach != null )
        {
            setLand( mach );
        }
        if ( state != null )
        {
            TabLayout.Tab tab = tabLayout.getTabAt( pos );
            tab.select();
            ListView l = ( ListView ) pages.get( pos );
            l.onRestoreInstanceState( state );
        }
        mTracker.setScreenName( "Page - Coin List" );
        mTracker.send( new HitBuilders.ScreenViewBuilder().build() );
        super.onResume();
    }


    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        View view = inflater.inflate( R.layout.all_coins, container, false );

        MainActivity application = ( MainActivity ) getActivity();
        mTracker = application.getDefaultTracker();


        viewPager = view.findViewById( view_pager );
        tabLayout = view.findViewById( R.id.tab_layout );
        materialDesignFAM = view.findViewById( R.id.fab_menu );
        disneylandFAB = view.findViewById( R.id.californiaFAB );
        californiaFAB = view.findViewById( R.id.disneylandFAB );
        downtownFAB = view.findViewById( R.id.downtownFAB );
        retiredFAB = view.findViewById( R.id.retiredFAB );

        pages = new Vector<>();
        lists = new ArrayList<>();

        materialDesignFAM.setClosedOnTouchOutside( true );

        disneylandFAB.setOnClickListener( this );
        californiaFAB.setOnClickListener( this );
        downtownFAB.setOnClickListener( this );
        retiredFAB.setOnClickListener( this );

        disneylandFAB.setShowShadow( false );
        californiaFAB.setShowShadow( false );
        downtownFAB.setShowShadow( false );
        retiredFAB.setShowShadow( false );

        //Default land
        Bundle args = getArguments();
        String land = args.getString( "land" );
        switch ( land )
        {
            case DISNEYLAND:
                setLand( disneyMachines );
                break;
            case CALIFORNIA_ADVENTURE:
                setLand( calMachines );
                break;
            case DOWNTOWN_DISNEY:
                setLand( downtownMachines );
                break;
            case RETIRED:
                setLand( retiredMachines );
            default:
                setLand( disneyMachines );
        }

        tabLayout.setTabGravity( TabLayout.MODE_SCROLLABLE );
        viewPager.addOnPageChangeListener( new TabLayout.TabLayoutOnPageChangeListener( tabLayout ) );
        viewPager.setOffscreenPageLimit( 10 );

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

    public void setLand( Machine[][] machines )
    {
        int addCustomPage = 1;
        // Select land
        if(machines == disneyMachines){
            selectedLand = DISNEYLAND;
        }
        else if(machines == calMachines){
            selectedLand = CALIFORNIA_ADVENTURE;
        }
        else if(machines == downtownMachines){
            selectedLand = DOWNTOWN_DISNEY;
        }
        else if( machines == retiredMachines )
        {
            selectedLand = RETIRED;
            addCustomPage = 0;
        }

        final ArrayList< ArrayList > coinList = new ArrayList<>();
        getActivity().setTitle(selectedLand);

        // Clear previous list
        if ( pages != null && lists != null )
        {
            pages.clear();
            lists.clear();
        }

        // Setup pages with lists of lands
        for ( int i = 0; i < machines.length + addCustomPage; i++ )
        {
            coinList.add( new ArrayList<>() );
            lists.add( new ListView( getActivity() ) );
            lists.get( i ).setDivider( ContextCompat.getDrawable( getActivity(), R.drawable.gradient ) );
            lists.get( i ).setDividerHeight( 4 );
            pages.add( lists.get( i ) );
        }

        // Create viewpager with pages
        CustomPagerAdapter adapter = new CustomPagerAdapter( getActivity(), pages );
        viewPager.setAdapter( adapter );

        int j = 0;
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("Disneyland");
        tabLayout.getTabAt(1).setText("California Adventure");
        tabLayout.getTabAt(2).setText("Downtown Disney");
        // Add coins with separators

        if( !selectedLand.equals( RETIRED ) )
        {
            // Add custom coins
            tabLayout.getTabAt( j ).setText( "Custom" );
            List< Coin > coins = sharedPreference.getCustomCoins( getActivity().getApplicationContext() );
            ArrayList customCoinsList = new ArrayList();
            for ( Coin coin : coins )
            {
                if ( coin.getCoinPark().equals( selectedLand ) )
                {
                    customCoinsList.add( coin );
                }
            }

            ListAdapter customCoinAdapter = new ListAdapter( getActivity(), R.layout.row, customCoinsList );
            lists.get( j ).setAdapter( customCoinAdapter );
            j++;
        }

        // Add normal coins
        for ( Machine[] machine : machines )
        {
            allCoinsAdapter coinAdapter = new allCoinsAdapter( getActivity(), R.layout.row, coinList.get( j ) );
            if(mach == retiredMachines){
                //coinAdapter.addSeparatorItem("LAND");
                for (Machine mac : machine) {
                    coinAdapter.addSeparatorItem(mac.getMachineName());
                    coinList.get(j).addAll(Arrays.asList(mac.getCoins()));
                }
            }
            else
            {
                for ( Machine mac : machine )
                {
                    coinAdapter.addSeparatorItem( mac.getMachineName() );
                    coinList.get( j ).addAll( Arrays.asList( mac.getCoins() ) );
                    tabLayout.getTabAt( j ).setText( mac.getLand() );
                }
            }
            lists.get( j ).setAdapter( coinAdapter );
            j++;
        }
    }

    @Override
    public void onClick( View view )
    {
        switch ( view.getId() )
        {
            case R.id.disneylandFAB:
                selectedLand = DISNEYLAND;
                mach = disneyMachines;
                setLand( disneyMachines );
                break;
            case R.id.californiaFAB:
                selectedLand = CALIFORNIA_ADVENTURE;
                mach = calMachines;
                setLand( calMachines );
                break;
            case R.id.downtownFAB:
                selectedLand = DOWNTOWN_DISNEY;
                mach = downtownMachines;
                setLand( downtownMachines );
                break;
            case R.id.retiredFAB:
                selectedLand = RETIRED;
                mach = retiredMachines;
                setLand(retiredMachines);
                break;
        }
        materialDesignFAM.close( true );
    }

    public class CustomPagerAdapter extends PagerAdapter
    {

        private Context mContext;
        private Vector< View > pages;


        public CustomPagerAdapter( Context context, Vector< View > pages )
        {
            this.mContext = context;
            this.pages = pages;
        }

        @Override
        public Object instantiateItem( ViewGroup container, int position )
        {
            View page = pages.get( position );
            container.addView( page );
            return page;
        }

        @Override
        public int getCount()
        {
            return pages.size();
        }

        @Override
        public boolean isViewFromObject( View view, Object object )
        {
            return view.equals( object );
        }

        @Override
        public void destroyItem( ViewGroup container, int position, Object object )
        {
            container.removeView( ( View ) object );
        }

    }
}
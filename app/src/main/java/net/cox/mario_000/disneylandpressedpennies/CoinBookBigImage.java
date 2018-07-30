package net.cox.mario_000.disneylandpressedpennies;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static net.cox.mario_000.disneylandpressedpennies.MainActivity.find;

/**
 * Created by mario_000 on 1/21/2017.
 */

public class CoinBookBigImage extends Fragment implements ViewPager.OnPageChangeListener
{
    private List< Coin > coinList;
    private List< Coin > customCoins;
    private TextView coinTitle;
    private TextView coinDate;
    private TextView coinMac;
    private TextView coinLand;
    private ViewPager viewPager;
    private final SharedPreference sharedPreference = new SharedPreference();
    private SimpleDateFormat dateFormat = new SimpleDateFormat( "MMMM dd, yyyy", Locale.US );

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        View view = inflater.inflate( R.layout.coin_book_pager, container, false );
        Bundle args = getArguments();
        int pos = args.getInt( "pos" );
        coinList = ( List< Coin > ) args.getSerializable( "ARRAYLIST" );
        getActivity().setTitle( coinList.get( pos ).getTitleCoin() );

        customCoins = sharedPreference.getCustomCoins( view.getContext() );

        // Link views
        viewPager = view.findViewById( R.id.view_pager );
        coinTitle = view.findViewById( R.id.txt_title );
        coinDate = view.findViewById( R.id.txt_date );
        coinMac = view.findViewById( R.id.txt_mac );
        coinLand = view.findViewById( R.id.txt_land );

        // Create image adapter
        CoinBookImageAdapter adapter = new CoinBookImageAdapter( getActivity(), coinList );
        viewPager.setAdapter( adapter );
        viewPager.setCurrentItem( pos );
        viewPager.setOffscreenPageLimit( 10 );
        viewPager.addOnPageChangeListener( this );

        // Set coin data
        Coin savedCoin = coinList.get( pos );
        coinTitle.setText( savedCoin.getTitleCoin() );
        coinDate.setText( dateFormat.format( savedCoin.getDateCollected() ) );

        if( customCoins.contains( savedCoin ) )
        {
            coinMac.setVisibility( View.GONE );
            coinLand.setText( savedCoin.getCoinPark() );
        }
        else
        {
            coinMac.setVisibility( View.VISIBLE );
            coinMac.setText( find( savedCoin ).getMachineName() );
            coinLand.setText( find( savedCoin ).getLand() );
        }

        return view;
    }

    @Override
    public void onPageScrolled( int position, float positionOffset, int positionOffsetPixels )
    {

    }

    @Override
    public void onPageSelected( int position )
    {
        // Set new title
        getActivity().setTitle( coinList.get( position ).getTitleCoin() );

        // Set new coin data
        Coin savedCoin = coinList.get( position );
        coinTitle.setText( savedCoin.getTitleCoin() );
        coinDate.setText( dateFormat.format( savedCoin.getDateCollected() ) );

        if( customCoins.contains( savedCoin ) )
        {
            coinMac.setVisibility( View.GONE );
            coinLand.setText( savedCoin.getCoinPark() );
        }
        else
        {
            coinMac.setVisibility( View.VISIBLE );
            coinMac.setText( find( savedCoin ).getMachineName() );
            coinLand.setText( find( savedCoin ).getLand() );
        }
    }

    @Override
    public void onPageScrollStateChanged( int state )
    {

    }
}
package net.cox.mario_000.disneylandpressedpennies;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import static net.cox.mario_000.disneylandpressedpennies.MainActivity.find;

/**
 * Created by mario_000 on 1/21/2017.
 */

public class CoinBookBigImage extends Fragment implements ViewPager.OnPageChangeListener
{
    // Coin lists
    private List< Object > coinList;
    private List< CustomCoin > customCoins;

    // Textviews
    private TextView coinTitle;
    private TextView coinDate;
    private TextView coinMac;
    private TextView coinLand;

    // References
    private ViewPager viewPager;
    private final SharedPreference sharedPreference = new SharedPreference();
    private SimpleDateFormat dateFormat = new SimpleDateFormat( "MMMM dd, yyyy", Locale.US );

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        View view = inflater.inflate( R.layout.coin_book_pager, container, false );
        getActivity().setTitle( "Coin Book" );
        Bundle args = getArguments();
        int pos = args.getInt( "pos" );

        // Get coins
        customCoins = sharedPreference.getCustomCoins( view.getContext() );
        coinList = ( List< Object > ) args.getSerializable( "ARRAYLIST" );

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
        if( coinList.get( pos ) instanceof Coin )
        {
            // Get coin data
            Coin savedCoin = ( Coin ) coinList.get( pos );
            coinTitle.setText( savedCoin.getTitleCoin() );
            coinDate.setText( dateFormat.format( savedCoin.getDateCollected() ) );

            // Get machine
            Machine selectedMac = find( savedCoin );
            coinMac.setVisibility( View.VISIBLE );
            coinMac.setText( selectedMac.getMachineName() );
            coinLand.setText( selectedMac.getLand() );
        }
        else
        {
            // Set custom coin data
            CustomCoin savedCoin = ( CustomCoin ) coinList.get( pos );
            int index = customCoins.indexOf( savedCoin );
            CustomCoin customCoin = customCoins.get( index );
            coinMac.setVisibility( View.GONE );
            coinLand.setText( customCoin.getCoinPark() );
            coinTitle.setText( customCoin.getTitleCoin() );
            coinDate.setText( dateFormat.format( customCoin.getDateCollected() ) );
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
        // Set new coin data
        if( coinList.get( position ) instanceof Coin )
        {
            Coin savedCoin = ( Coin ) coinList.get( position );
            coinTitle.setText( savedCoin.getTitleCoin() );
            coinDate.setText( dateFormat.format( savedCoin.getDateCollected() ) );

            // Get machine
            Machine selectedMac = find( savedCoin );
            coinMac.setVisibility( View.VISIBLE );
            coinMac.setText( selectedMac.getMachineName() );
            coinLand.setText( selectedMac.getLand() );
        }
        else
        {
            CustomCoin savedCoin = ( CustomCoin ) coinList.get( position );
            coinTitle.setText( savedCoin.getTitleCoin() );
            coinDate.setText( dateFormat.format( savedCoin.getDateCollected() ) );
            int index = customCoins.indexOf( savedCoin );
            CustomCoin customCoin = customCoins.get( index );
            coinMac.setVisibility( View.GONE );
            coinLand.setText( customCoin.getCoinPark() );
        }
    }

    @Override
    public void onPageScrollStateChanged( int state )
    {

    }
}
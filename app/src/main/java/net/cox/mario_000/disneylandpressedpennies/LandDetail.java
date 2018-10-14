package net.cox.mario_000.disneylandpressedpennies;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;

/**
 * Created by mario_000 on 6/25/2016.
 * Description: Fragment for displaying machines in land
 */
public class LandDetail extends Fragment implements Data, AdapterView.OnItemClickListener
{
    // Data
    private MachineAdapter mMachineAdapter;

    @Override
    public void onItemClick( AdapterView< ? > parent, View view, int position, long id )
    {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        Gson gson = new Gson();
        String jsonMachine = gson.toJson( mMachineAdapter.machines[ position ] );
        bundle.putString( "selectedMachine", jsonMachine );
        CoinsInMachineDetail fragment = new CoinsInMachineDetail();
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

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        View view = inflater.inflate( R.layout.activity_land, container, false );

        // Logo on page of machines in picked land
        ImageView logoView = view.findViewById( R.id.logo );

        Bundle extras = getArguments();
        if ( extras != null )
        {
            int land = extras.getInt( "land" );
            String logoImg = null;
            Machine[] tempCoins = null;
            String landTitle = null;

            //Set array based on land picked
            switch ( land )
            {
                case R.id.tomorrowBtn:
                    logoImg = "tomorrowland";
                    tempCoins = tomorrowCoins;
                    landTitle = "Tomorrowland";
                    break;
                case R.id.adventureBtn:
                    logoImg = "adventureland";
                    tempCoins = adventureCoins;
                    landTitle = "Adventureland";
                    break;
                case R.id.fantasyBtn:
                    logoImg = "fantasyland";
                    tempCoins = fantasyCoins;
                    landTitle = "Fantasyland";
                    break;
                case R.id.frontierBtn:
                    logoImg = "frontierland";
                    tempCoins = frontierCoins;
                    landTitle = "Frontierland";
                    break;
                case R.id.mainBtn:
                    logoImg = "main_street_2";
                    tempCoins = mainCoins;
                    landTitle = "Main Street";
                    break;
                case R.id.critterBtn:
                    logoImg = "critter_country";
                    tempCoins = critterCountryCoins;
                    landTitle = "Critter Country";
                    break;
                case R.id.toonBtn:
                    logoImg = "toontown";
                    tempCoins = toontownCoins;
                    landTitle = "Toon Town";
                    break;
                case R.id.newOrleansBtn:
                    logoImg = "new_orleans_square";
                    tempCoins = newOrleansCoins;
                    landTitle = "New Orleans Square";
                    break;
                case R.id.buenaBtn:
                    logoImg = "buena_vista";
                    tempCoins = buenaVistaCoins;
                    landTitle = "Buena Vista Street";
                    break;
                case R.id.hollywoodBtn:
                    logoImg = "hollywood_land";
                    tempCoins = hollywoodCoins;
                    landTitle = "Hollywood Land";
                    break;
                case R.id.grizzlyPeakAirfieldsBtn:
                    logoImg = "grizzly_airfield";
                    tempCoins = grizzlyPeakAirfieldsCoins;
                    landTitle = "Grizzly Peak Airfields";
                    break;
                case R.id.grizzlyPeakAreaBtn:
                    logoImg = "grizzly_rec_area";
                    tempCoins = grizzlyPeakAreaCoins;
                    landTitle = "Grizzly Peak Recreational Area";
                    break;
                case R.id.pixarPierBtn:
                    logoImg = "pixar_pier_logo";
                    tempCoins = pixarPierCoins;
                    landTitle = "Pixar Pier";
                    break;
                case R.id.carsBtn:
                    logoImg = "cars_land2";
                    tempCoins = carsLandCoins;
                    landTitle = "Cars Land";
                    break;
                case R.id.wodBtn:
                    logoImg = "wod";
                    tempCoins = worldDisneyCoins;
                    landTitle = "World of Disney";
                    break;
                case R.id.tortillaJoBtn:
                    logoImg = "tortilla_jo";
                    tempCoins = tortillaCoins;
                    landTitle = "Tortilla Jo's";
                    break;
                case R.id.wetzelsBtn:
                    logoImg = "wetzels";
                    tempCoins = wetzelsCoins;
                    landTitle = "Near Wetzels Pretzels";
                    break;
                case R.id.grandCalifornianBtn:
                    logoImg = "grand_californian";
                    tempCoins = grandCalifornianCoins;
                    landTitle = "Grand Californian Hotel";
                    break;
                case R.id.disneyHotelBtn:
                    logoImg = "disneyland_hotel";
                    tempCoins = disneylandHotelCoins;
                    landTitle = "Disneyland Hotel";
                    break;
                case R.id.paradiseHotelBtn:
                    logoImg = "paradise_pier_hotel";
                    tempCoins = paradiseHotelCoins;
                    landTitle = "Paradise Pier Hotel";
                    break;
            }

            // Set nav bar title
            getActivity().setTitle( landTitle );
            int logoResId = getResources().getIdentifier( logoImg, "drawable", getActivity().getPackageName() );
            logoView.setImageResource( logoResId );
            //Picasso.get().load( logoResId ).error( R.drawable.new_searching ).fit().into( logoView );
            mMachineAdapter = new MachineAdapter( getActivity().getApplicationContext(), R.layout.machine_row, tempCoins );
        }

        //List of machines in land
        ListView list = view.findViewById( R.id.landCoins );
        if ( list != null )
        {
            list.setAdapter( mMachineAdapter );
            list.setOnItemClickListener( this );
        }
        return view;
    }
}
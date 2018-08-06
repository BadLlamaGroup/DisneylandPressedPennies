package net.cox.mario_000.disneylandpressedpennies;
/**
 * Created by mario_000 on 7/11/2016.
 * Description: Maps fragment for displaying all machine locations
 */

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentCompat;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.List;

public class MapsActivity extends Fragment implements Data, GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, GoogleMap.OnMyLocationButtonClickListener
{
    // Map
    private GoogleApiClient mGoogleApiClient;
    private GoogleMap mGoogleMap;

    // Location
    private LocationRequest mLocationRequest;
    private static final long INTERVAL = 1000;
    private static final long FASTEST_INTERVAL = 500;
    private final static int REQUEST_LOCATION = 1000;
    private final LatLngBounds DISNEYLAND = new LatLngBounds( new LatLng( 33.803375, -117.928346 ), new LatLng( 33.818192, -117.915411 ) );
    private final LatLngBounds DISNEYLAND_CENTER = new LatLngBounds( new LatLng( 33.803721, -117.923705 ), new LatLng( 33.815796, -117.915536 ) );
    private Marker pos;

    // Zoom
    private final float defaultZoomLevel = 18.5f;
    private final float zoomedInLevel = 20f;

    // Data
    private boolean playServices;
    private boolean locationApproved;
    private Machine last;
    private List< Coin > collectedCoins;
    private Machine selectedMac;

    // References
    private Tracker mTracker;
    private SharedPreference sharedPreference = new SharedPreference();

    private boolean isGooglePlayServicesAvailable( Activity activity )
    {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable( activity );
        if ( status != ConnectionResult.SUCCESS )
        {
            if ( googleApiAvailability.isUserResolvableError( status ) )
            {
                googleApiAvailability.getErrorDialog( activity, status, 2404 ).show();
                getFragmentManager().popBackStack();
            }
            return false;
        } else
        {
            return true;
        }
    }

    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        if ( isGooglePlayServicesAvailable( getActivity() ) )
        {
            mGoogleApiClient = new GoogleApiClient.Builder( getActivity() )
                    .addApi( LocationServices.API )
                    .addConnectionCallbacks( this )
                    .addOnConnectionFailedListener( this )
                    .build();
            mGoogleApiClient.connect();
            playServices = true;
        }
        MainActivity application = ( MainActivity ) getActivity();
        mTracker = application.getDefaultTracker();

        // Get coins
        collectedCoins = sharedPreference.getCoins( getActivity().getApplicationContext() );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        View myFragmentView = inflater.inflate( R.layout.activity_maps, container, false );
        getActivity().setTitle( "Map of Machines" );
        mTracker.setScreenName( "Page - Maps" );
        mTracker.send( new HitBuilders.ScreenViewBuilder().build() );

        Bundle extras = getArguments();
        if ( extras != null )
        {
            // Get selected machine from json
            Gson gson = new Gson();
            String jsonMachine = extras.getString( "selectedMachine" );
            selectedMac = gson.fromJson( jsonMachine, Machine.class );
        }

        MapView mMapView = myFragmentView.findViewById( R.id.mapView );

        if ( mMapView != null )
        {
            mMapView.onCreate( savedInstanceState );
            mMapView.onResume();
            mMapView.getMapAsync( this );
        }

        return myFragmentView;

    }

    @Override
    public void onStart()
    {
        if ( playServices && !mGoogleApiClient.isConnected() )
        {
            mGoogleApiClient.connect();
        }
        super.onStart();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if ( mGoogleApiClient != null && mGoogleApiClient.isConnected() )
        {
            startLocationUpdates();
        }

        collectedCoins = sharedPreference.getCoins( getActivity().getApplicationContext() );
    }

    @Override
    public void onMapReady( GoogleMap googleMap )
    {
        // Set map
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setZoomControlsEnabled( true );
        mGoogleMap.setMapType( GoogleMap.MAP_TYPE_HYBRID );
        mGoogleMap.setOnMyLocationButtonClickListener( this );
        mGoogleMap.setOnInfoWindowClickListener( this );
        mGoogleMap.clear();
        final float zoom = mGoogleMap.getCameraPosition().zoom;

        // Set machine markers
        placeMarkers();

        mGoogleMap.setOnInfoWindowCloseListener( new GoogleMap.OnInfoWindowCloseListener()
        {
            @Override
            public void onInfoWindowClose( Marker marker )
            {

                switch ( marker.getSnippet() )
                {
                    case "4 coins left":
                        marker.setIcon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_CYAN ) );
                        break;
                    case "3 coins left":
                        marker.setIcon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_YELLOW ) );
                        break;
                    case "2 coins left":
                        marker.setIcon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_ORANGE ) );
                        break;
                    case "1 coin left":
                        marker.setIcon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_AZURE ) );
                        break;
                    case "All Collected":
                        marker.setIcon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_GREEN ) );
                        break;
                }

                if ( zoom > defaultZoomLevel )
                {
                    animateCamera( marker.getPosition(), defaultZoomLevel );
                }
            }
        } );
        mGoogleMap.setOnMarkerClickListener( new GoogleMap.OnMarkerClickListener()
        {
            @Override
            public boolean onMarkerClick( final Marker marker )
            {
                marker.setIcon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_BLUE ) );
                final float zoom = mGoogleMap.getCameraPosition().zoom;
                if ( zoom <= zoomedInLevel )
                {
                    animateCamera( marker.getPosition(), zoomedInLevel );
                }
                return false;
            }
        } );
    }

    @Override
    public void onConnected( @Nullable Bundle bundle )
    {
        // Order matters
        createLocationRequest();
        checkIfLocationServicesEnabled();
        startLocationUpdates();
        setMapCamera();
    }

    private void setMapCamera()
    {
        if ( last != null )
        {
            mGoogleMap.moveCamera( CameraUpdateFactory.newLatLngZoom( last.getPosition(), zoomedInLevel ) );
        } else if ( selectedMac != null )
        {
            mGoogleMap.moveCamera( CameraUpdateFactory.newLatLngZoom( selectedMac.getPosition(), zoomedInLevel ) );
        } else
        {
            mGoogleMap.setLatLngBoundsForCameraTarget( DISNEYLAND );
            mGoogleMap.setMinZoomPreference( 14.7f );
            mGoogleMap.moveCamera( CameraUpdateFactory.newLatLngBounds( DISNEYLAND_CENTER, 0 ) );
        }
    }

    private void animateCamera( final LatLng place, final float zoomLevel )
    {
        new Handler().postDelayed( new Runnable()
        {
            @Override
            public void run()
            {
                mGoogleMap.animateCamera( CameraUpdateFactory.newLatLngZoom( place, zoomLevel ), 1000, null );
            }
        }, 300 );
    }

    @Override
    public void onLocationChanged( Location location )
    {
        if ( pos != null )
        {
            pos.remove();
        }
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        LatLng position = new LatLng( latitude, longitude );
        pos = mGoogleMap.addMarker( new MarkerOptions().position( position ) );
    }

    @Override
    public void onStop()
    {
        if ( playServices && mGoogleApiClient.isConnected() )
        {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if ( playServices )
        {
            stopLocationUpdates();
        }
    }

    private void createLocationRequest()
    {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval( INTERVAL );
        mLocationRequest.setFastestInterval( FASTEST_INTERVAL );
        mLocationRequest.setPriority( LocationRequest.PRIORITY_HIGH_ACCURACY );
    }

    private void startLocationUpdates()
    {
        checkLocationPermission();
        if ( locationApproved )
        {
            LocationServices.FusedLocationApi.requestLocationUpdates( mGoogleApiClient, mLocationRequest, this );
            mGoogleMap.setMyLocationEnabled( true );
        }
    }

    private void stopLocationUpdates()
    {
        if ( ActivityCompat.checkSelfPermission( getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION ) == PackageManager.PERMISSION_GRANTED )
        {
            LocationServices.FusedLocationApi.removeLocationUpdates( mGoogleApiClient, this );
            mGoogleMap.setMyLocationEnabled( false );
        }
    }

    private void checkIfLocationServicesEnabled()
    {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest( mLocationRequest );
        builder.setAlwaysShow( true );
        PendingResult< LocationSettingsResult > result = LocationServices.SettingsApi.checkLocationSettings( mGoogleApiClient, builder.build() );

        result.setResultCallback( new ResultCallback< LocationSettingsResult >()
        {
            @Override
            public void onResult( @NonNull LocationSettingsResult result )
            {
                final Status status = result.getStatus();
                switch ( status.getStatusCode() )
                {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try
                        {
                            status.startResolutionForResult( getActivity(), REQUEST_LOCATION );
                        } catch ( Exception e )
                        {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        } );
    }

    @Override
    public void onConnectionSuspended( int i )
    {

    }

    @Override
    public void onConnectionFailed( @NonNull ConnectionResult result )
    {
        playServices = false;
    }

    @Override
    public void onInfoWindowClick( Marker marker )
    {
        Machine pressed = null;
        for ( Machine[] machineArray : Data.disneyMachines )
        {
            for ( Machine machine : machineArray )
            {
                if ( machine.getMachineName().equals( marker.getTitle() ) )
                {
                    pressed = machine;
                }
            }
        }
        for ( Machine[] machineArray : Data.calMachines )
        {
            for ( Machine machine : machineArray )
            {
                if ( machine.getMachineName().equals( marker.getTitle() ) )
                {
                    pressed = machine;
                }
            }
        }
        for ( Machine[] machineArray : Data.downtownMachines )
        {
            for ( Machine machine : machineArray )
            {
                if ( machine.getMachineName().equals( marker.getTitle() ) )
                {
                    pressed = machine;
                }
            }
        }

        last = pressed;
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        CoinsInMachineDetail fragment = new CoinsInMachineDetail();
        Bundle bundle = new Bundle();
        Gson gson = new Gson();
        String jsonCoin = gson.toJson( pressed );
        bundle.putString( "selectedMachine", jsonCoin );
        fragment.setArguments( bundle );
        fragmentTransaction.setCustomAnimations(
                R.animator.fade_in,
                R.animator.fade_out,
                R.animator.fade_in,
                R.animator.fade_out );
        fragmentTransaction.replace( R.id.mainFrag, fragment );
        fragmentTransaction.addToBackStack( null );
        if ( pressed != null )
        {
            fragmentTransaction.commit();
        }
    }

    private void checkLocationPermission()
    {
        if ( ContextCompat.checkSelfPermission( getActivity(), Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED )
        {
            FragmentCompat.requestPermissions( this, new String[]{ Manifest.permission.ACCESS_FINE_LOCATION }, REQUEST_LOCATION );
        } else if ( ContextCompat.checkSelfPermission( getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED )
        {
            FragmentCompat.requestPermissions( this, new String[]{ Manifest.permission.ACCESS_COARSE_LOCATION }, REQUEST_LOCATION );
        } else
        {
            locationApproved = true;
        }
    }

    @Override
    public void onRequestPermissionsResult( int requestCode, @NonNull String permissions[], @NonNull int[] grantResults )
    {

        switch ( requestCode )
        {
            case REQUEST_LOCATION:
            {
                if ( grantResults[ 0 ] == PackageManager.PERMISSION_GRANTED )
                {
                    locationApproved = true;

                } else if ( grantResults[ 0 ] == PackageManager.PERMISSION_DENIED )
                {
                    mGoogleApiClient.disconnect();
                }
            }
        }
    }

    private void placeMarkers()
    {
        final Handler handler = new Handler();
        final Handler handler2 = new Handler();
        final Handler handler3 = new Handler();
        new Thread()
        {
            @Override
            public void run()
            {
                handler.post( new Runnable()
                {
                    @Override
                    public void run()
                    {
                        for ( final Machine[] machineArray : Data.disneyMachines )
                        {
                            for ( final Machine machine : machineArray )
                            {
                                int amtCollected = machine.getCoins().length;
                                for ( Coin c : machine.getCoins() )
                                {
                                    if ( collectedCoins.contains( c ) )
                                    {
                                        amtCollected--;
                                    }
                                }

                                MarkerOptions markerOptions = new MarkerOptions();
                                markerOptions.position( machine.getPosition() );
                                markerOptions.title( machine.getMachineName() );

                                switch ( amtCollected )
                                {
                                    case 0:
                                        markerOptions.snippet( "All Collected" );
                                        markerOptions.icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_GREEN ) );
                                        break;
                                    case 1:
                                        markerOptions.snippet( "1 coin left" );
                                        markerOptions.icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_AZURE ) );
                                        break;
                                    case 2:
                                        markerOptions.snippet( "2 coins left" );
                                        markerOptions.icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_ORANGE ) );
                                        break;
                                    case 3:
                                        markerOptions.snippet( "3 coins left" );
                                        markerOptions.icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_YELLOW ) );
                                        break;
                                    case 4:
                                        markerOptions.snippet( "4 coins left" );
                                        markerOptions.icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_CYAN ) );
                                        break;
                                    default:
                                        markerOptions.icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_CYAN ) );
                                        break;
                                }
                                Marker lastMarker = mGoogleMap.addMarker( markerOptions );
                                if ( last != null )
                                {
                                    if ( last.getMachineName().equals( markerOptions.getTitle() ) )
                                    {
                                        lastMarker.setIcon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_BLUE ) );
                                        lastMarker.showInfoWindow();
                                    }
                                } else if ( selectedMac != null )
                                {
                                    if ( selectedMac.getMachineName().equals( markerOptions.getTitle() ) )
                                    {
                                        lastMarker.setIcon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_BLUE ) );
                                        lastMarker.showInfoWindow();
                                    }
                                }
                            }
                        }
                    }
                } );
            }
        }.run();
        new Thread()
        {
            @Override
            public void run()
            {
                handler2.post( new Runnable()
                {
                    @Override
                    public void run()
                    {
                        for ( final Machine[] machineArray : Data.calMachines )
                        {
                            for ( final Machine machine : machineArray )
                            {
                                int amtCollected = machine.getCoins().length;
                                for ( Coin c : machine.getCoins() )
                                {
                                    if ( collectedCoins.contains( c ) )
                                    {
                                        amtCollected--;
                                    }
                                }

                                MarkerOptions markerOptions = new MarkerOptions();
                                markerOptions.position( machine.getPosition() );
                                markerOptions.title( machine.getMachineName() );

                                switch ( amtCollected )
                                {
                                    case 0:
                                        markerOptions.snippet( "All Collected" );
                                        markerOptions.icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_GREEN ) );
                                        break;
                                    case 1:
                                        markerOptions.snippet( "1 coin left" );
                                        markerOptions.icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_AZURE ) );
                                        break;
                                    case 2:
                                        markerOptions.snippet( "2 coins left" );
                                        markerOptions.icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_ORANGE ) );
                                        break;
                                    case 3:
                                        markerOptions.snippet( "3 coins left" );
                                        markerOptions.icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_YELLOW ) );
                                        break;
                                    case 4:
                                        markerOptions.snippet( "4 coins left" );
                                        markerOptions.icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_CYAN ) );
                                        break;
                                    default:
                                        markerOptions.icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_CYAN ) );
                                        break;
                                }
                                Marker lastMarker = mGoogleMap.addMarker( markerOptions );
                                if ( last != null )
                                {
                                    if ( last.getMachineName().equals( markerOptions.getTitle() ) )
                                    {
                                        lastMarker.setIcon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_BLUE ) );
                                        lastMarker.showInfoWindow();
                                    }
                                } else if ( selectedMac != null )
                                {
                                    if ( selectedMac.getMachineName().equals( markerOptions.getTitle() ) )
                                    {
                                        lastMarker.setIcon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_BLUE ) );
                                        lastMarker.showInfoWindow();
                                    }
                                }
                            }
                        }
                    }
                } );
            }
        }.run();
        new Thread()
        {
            @Override
            public void run()
            {
                handler3.post( new Runnable()
                {
                    @Override
                    public void run()
                    {
                        for ( final Machine[] machineArray : Data.downtownMachines )
                        {
                            for ( final Machine machine : machineArray )
                            {
                                int amtCollected = machine.getCoins().length;
                                for ( Coin c : machine.getCoins() )
                                {
                                    if ( collectedCoins.contains( c ) )
                                    {
                                        amtCollected--;
                                    }
                                }

                                MarkerOptions markerOptions = new MarkerOptions();
                                markerOptions.position( machine.getPosition() );
                                markerOptions.title( machine.getMachineName() );
                                switch ( amtCollected )
                                {
                                    case 0:
                                        markerOptions.snippet( "All Collected" );
                                        markerOptions.icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_GREEN ) );
                                        break;
                                    case 1:
                                        markerOptions.snippet( "1 coin left" );
                                        markerOptions.icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_AZURE ) );
                                        break;
                                    case 2:
                                        markerOptions.snippet( "2 coins left" );
                                        markerOptions.icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_ORANGE ) );
                                        break;
                                    case 3:
                                        markerOptions.snippet( "3 coins left" );
                                        markerOptions.icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_YELLOW ) );
                                        break;
                                    case 4:
                                        markerOptions.snippet( "4 coins left" );
                                        markerOptions.icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_CYAN ) );
                                        break;
                                    default:
                                        markerOptions.icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_CYAN ) );
                                        break;
                                }

                                Marker lastMarker = mGoogleMap.addMarker( markerOptions );
                                if ( last != null )
                                {
                                    if ( last.getMachineName().equals( markerOptions.getTitle() ) )
                                    {
                                        lastMarker.setIcon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_BLUE ) );
                                        lastMarker.showInfoWindow();
                                    }
                                } else if ( selectedMac != null )
                                {
                                    if ( selectedMac.getMachineName().equals( markerOptions.getTitle() ) )
                                    {
                                        lastMarker.setIcon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_BLUE ) );
                                        lastMarker.showInfoWindow();
                                    }
                                }
                            }
                        }
                    }
                } );
            }
        }.run();
    }

    @Override
    public boolean onMyLocationButtonClick()
    {
        checkIfLocationServicesEnabled();
        if ( pos != null )
        {
            animateCamera( pos.getPosition(), defaultZoomLevel );
        }
        return false;
    }

}
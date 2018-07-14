package net.cox.mario_000.disneylandpressedpennies;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import static net.cox.mario_000.disneylandpressedpennies.MainActivity.img;

/**
 * Created by mario_000 on 6/25/2016.
 * Description: Fragment for displaying coins in current machine
 */
public class CoinsInMachineDetail extends Fragment implements Data, AdapterView.OnItemClickListener, View.OnClickListener {
    private CoinAdapter mCoinAdapter = null;
    private Machine machine;
    private ListView listCoins;
    private Tracker mTracker;
    private int selection = 0;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selection = position;
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        singleCoin fragment = new singleCoin();
        Bundle bundle = new Bundle();
        Gson gson = new Gson();
        String jsonCoin = gson.toJson(mCoinAdapter.getCoinsInMachine()[position]);
        String jsonMachine = gson.toJson(machine);
        bundle.putString("selectedCoin", jsonCoin);
        bundle.putString("selectedMachine", jsonMachine);
        fragment.setArguments(bundle);
        fragmentTransaction.setCustomAnimations(
                R.animator.fade_in,
                R.animator.fade_out,
                R.animator.fade_in,
                R.animator.fade_out);
        fragmentTransaction.replace(R.id.mainFrag, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onResume() {

        // Create coin adapter
        mCoinAdapter = new CoinAdapter(getActivity(), R.layout.row, machine.getCoins(), machine);
        //Check that listCoins is linked to view
        if (listCoins != null) {
            listCoins.setAdapter(mCoinAdapter);
            listCoins.setOnItemClickListener(this);
            listCoins.setSelection(selection);
        }
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_coin_detail, container, false);

        MainActivity application = (MainActivity) getActivity();
        mTracker = application.getDefaultTracker();

        // Link views to variables
        listCoins = (ListView) view.findViewById(R.id.listCoinsInMachine);
        final ImageView coinPreview = (ImageView) view.findViewById(R.id.coinPreview);
        //final ImageView macPreview = (ImageView) view.findViewById(R.id.macPreview);

        Bundle extras = getArguments();
        if (extras != null) {
            // Get selected machine from json
            Gson gson = new Gson();
            String jsonMachine = extras.getString("selectedMachine");
            machine = gson.fromJson(jsonMachine, Machine.class);
            getActivity().setTitle(machine.getMachineName());



            // Get resId for image from machine
            int resId = getActivity().getResources().getIdentifier(machine.getCoinPreviewImg(), "drawable", getActivity().getPackageName());
            int resId2 = getActivity().getResources().getIdentifier(machine.getMachineImg(), "drawable", getActivity().getPackageName());
            // Load preview image of coins in machine
            img.loadBitmap(resId, getResources(), 300, 160, coinPreview, 0);
            //img.loadBitmap(resId2, getResources(), 300, 160, macPreview, 0);

            // Create coin adapter
            mCoinAdapter = new CoinAdapter(getActivity(), R.layout.row, machine.getCoins(), machine);

            //Check that listCoins is linked to view
            if (listCoins != null) {
                listCoins.setAdapter(mCoinAdapter);
                listCoins.setOnItemClickListener(this);
            }
        }

        LinearLayout btn = (LinearLayout) view.findViewById(R.id.linNav);
        btn.setOnClickListener(this);
        // TODO: 7/27/2016 Report machine missing function
        LinearLayout showOnMap = (LinearLayout) view.findViewById(R.id.linMap);
        showOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(inv.hasPurchase("premium")) {
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    MapsActivity fragment = new MapsActivity();
                    Gson gson = new Gson();
                    Bundle bundle = new Bundle();
                    String jsonMachine = gson.toJson(machine);
                    bundle.putString("selectedMachine", jsonMachine);
                    fragment.setArguments(bundle);
                    fragmentTransaction.setCustomAnimations(
                            R.animator.fade_in,
                            R.animator.fade_out,
                            R.animator.fade_in,
                            R.animator.fade_out);
                    fragmentTransaction.replace(R.id.mainFrag, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
//                }
//                else{
//                    ((MainActivity)getActivity()).buy();
//                }
            }
        });

        TextView txtParkBanner = (TextView) view.findViewById(R.id.parkBanner);
        txtParkBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://www.parkpennies.com";
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Website")
                        .setAction("Clicked")
                        .setLabel(machine.getMachineName())
                        .setValue(1)
                        .build());
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });


        return view;
    }

    @Override
    public void onClick(View v) {



        // Open google maps for navigation
        if (isGoogleMapsInstalled()){// && inv.hasPurchase("premium")) {
            LatLng location = machine.getPosition();
            String geoUri = "google.navigation:q=" + location.latitude + "," + location.longitude + "&mode=w";
            String url = "http://maps.google.com/maps?daddr=" + location.latitude + "," + location.longitude + " (" + machine.getMachineName() + ")" + " &dirflg=w";  //Other option
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            startActivity(intent);
        }

        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Navigation")
                .setAction("Navigate To")
                .setLabel(machine.getMachineName())
                .setValue(1)
                .build());


    }

    public boolean isGoogleMapsInstalled()
    {
        try
        {
            ApplicationInfo info = getActivity().getPackageManager().getApplicationInfo("com.google.android.apps.maps", 0 );
            return true;
        }
        catch(PackageManager.NameNotFoundException e)
        {
            Toast.makeText(getActivity(), "Google Maps not installed", Toast.LENGTH_SHORT).show();
            showMapsNotInstalled();
            return false;
        }

    }

    public void showMapsNotInstalled(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("Install Google Maps")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent installMaps = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.google.android.apps.maps"));
                        startActivity(installMaps);
                    }
                });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}
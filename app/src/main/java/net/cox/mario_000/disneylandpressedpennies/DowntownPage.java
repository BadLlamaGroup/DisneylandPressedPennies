package net.cox.mario_000.disneylandpressedpennies;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.List;

import static net.cox.mario_000.disneylandpressedpennies.Data.downtownMachines;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numDowntownCoinsCollected;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numDowntownCoinsTotal;


/**
 * Created by mario_000 on 7/11/2016.
 * Description: Fragment for displaying Downtown Disney page
 */
public class DowntownPage extends Fragment implements View.OnClickListener {
    LinearLayout mapBtn;
    LinearLayout coinBook;
    LinearLayout coinList;
    TextView amount;
    private Tracker mTracker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myFragmentView = inflater.inflate(R.layout.downtown, container, false);
        getActivity().setTitle("Downtown Disney");

        MainActivity application = (MainActivity) getActivity();
        mTracker = application.getDefaultTracker();


        //Link buttons and set on click listener
        ImageButton wodBtn = (ImageButton) myFragmentView.findViewById(R.id.wodBtn);
        wodBtn.setOnClickListener(this);
        ImageButton pinTradersBtn = (ImageButton) myFragmentView.findViewById(R.id.tortillaJoBtn);
        pinTradersBtn.setOnClickListener(this);
        ImageButton wetzelsBtn = (ImageButton) myFragmentView.findViewById(R.id.wetzelsBtn);
        wetzelsBtn.setOnClickListener(this);
        ImageButton grandCalifornianBtn = (ImageButton) myFragmentView.findViewById(R.id.grandCalifornianBtn);
        grandCalifornianBtn.setOnClickListener(this);
        ImageButton disneyBtn = (ImageButton) myFragmentView.findViewById(R.id.disneyHotelBtn);
        disneyBtn.setOnClickListener(this);
        ImageButton paradiseHotelBtn = (ImageButton) myFragmentView.findViewById(R.id.paradiseHotelBtn);
        paradiseHotelBtn.setOnClickListener(this);


        final SharedPreference sharedPreference = new SharedPreference();
        List<Coin> savedCoins = sharedPreference.getCoins(getActivity().getApplicationContext());

        TextView txtWod = (TextView) myFragmentView.findViewById(R.id.txt_wod_collected);
        TextView txtWetzels = (TextView) myFragmentView.findViewById(R.id.txt_wetzels_collected);
        TextView txtPin = (TextView) myFragmentView.findViewById(R.id.txt_pin_collected);
        TextView txtDisneyHotel = (TextView) myFragmentView.findViewById(R.id.txt_disneyHotel_collected);
        TextView txtGrandCal = (TextView) myFragmentView.findViewById(R.id.txt_grandCal_collected);
        TextView txtPierHotel = (TextView) myFragmentView.findViewById(R.id.txt_pierHotel_collected);

        TextView[] list = new TextView[] {txtWod, txtWetzels, txtPin, txtDisneyHotel, txtGrandCal, txtPierHotel};


        int collectedInLand;
        int landTotal;
        for(int i = 0; i < downtownMachines.length; i++){ //Land
            collectedInLand = 0;
            landTotal = 0;
            for (Machine mach : downtownMachines[i]) { //Machine in land
                landTotal += mach.getCoins().length;
                for (Coin c : mach.getCoins()) { //Coin in machine
                    if (savedCoins.contains(c)) {
                        collectedInLand++;
                    }
                }
            }
            list[i].setText(collectedInLand + " / " + landTotal);
        }





        amount = (TextView) myFragmentView.findViewById(R.id.amt);


        coinBook = (LinearLayout) myFragmentView.findViewById(R.id.coinBookBtn);
        coinBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                CoinBookDetail fragment = new CoinBookDetail();
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
        });

        mapBtn = (LinearLayout) myFragmentView.findViewById(R.id.mapBtn);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(inv.hasPurchase("premium")) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                MapsActivity fragment = new MapsActivity();
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

        coinList = (LinearLayout) myFragmentView.findViewById(R.id.allCoinsBtn);
        coinList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                allCoins fragment = new allCoins();
                Bundle args = new Bundle();
                args.putString("land", "Downtown Disney");
                fragment.setArguments(args);
                fragmentTransaction.setCustomAnimations(
                        R.animator.fade_in,
                        R.animator.fade_out,
                        R.animator.fade_in,
                        R.animator.fade_out);
                fragmentTransaction.replace(R.id.mainFrag, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return myFragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        amount.setText(numDowntownCoinsCollected + " / " + numDowntownCoinsTotal);
        mTracker.setScreenName("Page - Downtown Disney");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("land", v.getId());
        MachineDetail fragment = new MachineDetail();
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
}
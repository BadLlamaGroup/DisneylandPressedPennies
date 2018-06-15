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

import static net.cox.mario_000.disneylandpressedpennies.Data.disneyMachines;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numDisneyCoinsCollected;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numDisneyCoinsTotal;


/**
 * Created by mario_000 on 7/11/2016.
 * Description: Fragment for displaying Disney page
 */
public class DisneyPage extends Fragment implements View.OnClickListener {
    LinearLayout mapBtn;
    LinearLayout coinBook;
    LinearLayout coinList;
    TextView amount;
    private Tracker mTracker;

    @Override
    public void onResume() {
        super.onResume();
        amount.setText(numDisneyCoinsCollected + " / " + numDisneyCoinsTotal);
        mTracker.setScreenName("Page - Disneyland");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myFragmentView = inflater.inflate(R.layout.disneyland, container, false);
        getActivity().setTitle("Disneyland");

        MainActivity application = (MainActivity) getActivity();
        mTracker = application.getDefaultTracker();


        //Link buttons and set on click listener
        ImageButton tomorrowBtn = (ImageButton) myFragmentView.findViewById(R.id.tomorrowBtn);
        tomorrowBtn.setOnClickListener(this);
        ImageButton adventureBtn = (ImageButton) myFragmentView.findViewById(R.id.adventureBtn);
        adventureBtn.setOnClickListener(this);
        ImageButton frontierBtn = (ImageButton) myFragmentView.findViewById(R.id.frontierBtn);
        frontierBtn.setOnClickListener(this);
        ImageButton fantasyBtn = (ImageButton) myFragmentView.findViewById(R.id.fantasyBtn);
        fantasyBtn.setOnClickListener(this);
        ImageButton mainBtn = (ImageButton) myFragmentView.findViewById(R.id.mainBtn);
        mainBtn.setOnClickListener(this);
        ImageButton critterBtn = (ImageButton) myFragmentView.findViewById(R.id.critterBtn);
        critterBtn.setOnClickListener(this);
        ImageButton toonBtn = (ImageButton) myFragmentView.findViewById(R.id.toonBtn);
        toonBtn.setOnClickListener(this);
        ImageButton newOrleansBtn = (ImageButton) myFragmentView.findViewById(R.id.newOrleansBtn);
        newOrleansBtn.setOnClickListener(this);

        final SharedPreference sharedPreference = new SharedPreference();
        List<Coin> savedCoins = sharedPreference.getCoins(getActivity().getApplicationContext());

        TextView txtAdv = (TextView) myFragmentView.findViewById(R.id.txt_adventure_collected);
        TextView txtTom = (TextView) myFragmentView.findViewById(R.id.txt_tomorrowland_collected);
        TextView txtFro = (TextView) myFragmentView.findViewById(R.id.txt_frontierland_collected);
        TextView txtToo = (TextView) myFragmentView.findViewById(R.id.txt_toontown_collected);
        TextView txtFan = (TextView) myFragmentView.findViewById(R.id.txt_fantasyland_collected);
        TextView txtMai = (TextView) myFragmentView.findViewById(R.id.txt_main_collected);
        TextView txtCri = (TextView) myFragmentView.findViewById(R.id.txt_critter_collected);
        TextView txtOrl = (TextView) myFragmentView.findViewById(R.id.txt_orleans_collected);

        TextView[] list = new TextView[] {txtMai, txtTom, txtFan, txtToo, txtFro, txtAdv, txtCri, txtOrl};

        int collectedInLand;
        int landTotal;
        for(int i = 0; i < disneyMachines.length; i++){ //Land
            collectedInLand = 0;
            landTotal = 0;
            for (Machine mach : disneyMachines[i]) { //Machine in land
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
//                if (inv.hasPurchase("premium")) {
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
//                } else {
//                    ((MainActivity) getActivity()).buy();
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
                args.putString("land", "Disneyland");
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
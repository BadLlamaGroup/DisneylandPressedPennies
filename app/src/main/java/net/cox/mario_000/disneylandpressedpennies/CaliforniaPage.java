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

import static net.cox.mario_000.disneylandpressedpennies.Data.calMachines;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numCalCoinsCollected;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numCalCoinsTotal;


/**
 * Created by mario_000 on 7/11/2016.
 * Description: Fragment for California Adventure page
 */
public class CaliforniaPage extends Fragment implements View.OnClickListener {
    LinearLayout mapBtn;
    LinearLayout coinBook;
    LinearLayout coinList;
    TextView amount;
    private Tracker mTracker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myFragmentView = inflater.inflate(R.layout.california, container, false);
        getActivity().setTitle("California Adventure");


        MainActivity application = (MainActivity) getActivity();
        mTracker = application.getDefaultTracker();


        //Link buttons and set on click listener
        ImageButton buenaBtn = (ImageButton) myFragmentView.findViewById(R.id.buenaBtn);
        buenaBtn.setOnClickListener(this);
        ImageButton hollywoodBtn = (ImageButton) myFragmentView.findViewById(R.id.hollywoodBtn);
        hollywoodBtn.setOnClickListener(this);
        ImageButton grizzlyPeakAirfieldsBtn = (ImageButton) myFragmentView.findViewById(R.id.grizzlyPeakAirfieldsBtn);
        grizzlyPeakAirfieldsBtn.setOnClickListener(this);
        ImageButton grizzlyPeakAreaBtn = (ImageButton) myFragmentView.findViewById(R.id.grizzlyPeakAreaBtn);
        grizzlyPeakAreaBtn.setOnClickListener(this);
        ImageButton paradisePierBtn = (ImageButton) myFragmentView.findViewById(R.id.pixarPierBtn);
        paradisePierBtn.setOnClickListener(this);
        ImageButton carsBtn = (ImageButton) myFragmentView.findViewById(R.id.carsBtn);
        carsBtn.setOnClickListener(this);


        final SharedPreference sharedPreference = new SharedPreference();
        List<Coin> savedCoins = sharedPreference.getCoins(getActivity().getApplicationContext());

        TextView txtBuena = (TextView) myFragmentView.findViewById(R.id.txt_buena_collected);
        TextView txtAirfields = (TextView) myFragmentView.findViewById(R.id.txt_airfields_collected);
        TextView txtRec = (TextView) myFragmentView.findViewById(R.id.txt_rec_collected);
        TextView txtParadise = (TextView) myFragmentView.findViewById(R.id.txt_paradise_collected);
        TextView txtHollywood = (TextView) myFragmentView.findViewById(R.id.txt_hollywood_collected);
        TextView txtCars = (TextView) myFragmentView.findViewById(R.id.txt_cars_collected);

        TextView[] list = new TextView[] {txtBuena, txtAirfields, txtRec, txtParadise, txtHollywood, txtCars};


        int collectedInLand;
        int landTotal;
        for(int i = 0; i < calMachines.length; i++){ //Land
            collectedInLand = 0;
            landTotal = 0;
            for (Machine mach : calMachines[i]) { //Machine in land
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
                args.putString("land", "California Adventure");
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
        amount.setText(numCalCoinsCollected + " / " + numCalCoinsTotal);
        mTracker.setScreenName("Page - California Adventure");
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
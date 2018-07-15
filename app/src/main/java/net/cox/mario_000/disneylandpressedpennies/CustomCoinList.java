package net.cox.mario_000.disneylandpressedpennies;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by mario_000 on 7/14/2018.
 */

public class CustomCoinList extends Fragment implements Data, AdapterView.OnItemClickListener{

/**
 * Created by mario_000 on 6/25/2016.
 * Description: Fragment for displaying coins in current machine
 */

    private CustomCoinAdapter mCoinAdapter = null;
    private SharedPreference sharedPreference;
    private ListView listCoins;
    private Tracker mTracker;
    private int selection = 0;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selection = position;
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        CustomCoinFragment fragment = new CustomCoinFragment();
        Bundle bundle = new Bundle();
        Gson gson = new Gson();
        String jsonCoin = gson.toJson(mCoinAdapter.getCustomCoins()[position]);
        bundle.putString("selectedCoin", jsonCoin);
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
        List<Coin> customCoinsList = sharedPreference.getCustomCoins(getActivity());
        Coin[] customCoins = customCoinsList.toArray(new Coin[customCoinsList.size()]);
        mCoinAdapter = new CustomCoinAdapter(getActivity(), R.layout.row, customCoins);
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
        View view = inflater.inflate(R.layout.custom_coin_list, container, false);

        MainActivity application = (MainActivity) getActivity();
        mTracker = application.getDefaultTracker();

        getActivity().setTitle("Custom Coins");

        sharedPreference = new SharedPreference();

        // Link views to variables
        listCoins = view.findViewById(R.id.listCustomCoins);

        // Create coin adapter
        List<Coin> customCoinsList = sharedPreference.getCustomCoins(getActivity());
        Coin[] customCoins = customCoinsList.toArray(new Coin[customCoinsList.size()]);
        mCoinAdapter = new CustomCoinAdapter(getActivity(), R.layout.row, customCoins);

        //Check that listCoins is linked to view
        if (listCoins != null) {
            listCoins.setAdapter(mCoinAdapter);
            listCoins.setOnItemClickListener(this);
        }

        return view;
    }

}
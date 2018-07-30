package net.cox.mario_000.disneylandpressedpennies;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mario_000 on 2/15/2017.
 */

public class search extends Fragment implements Data, AdapterView.OnItemClickListener {
    private ListView l;
    ArrayList foundCoins;
    List< Coin > customCoins;
    private final SharedPreference sharedPreference = new SharedPreference();
    ListAdapter mCoinAdapter;
    Parcelable state;
    TextView numFound;
    TextView noResults;
    private Tracker mTracker;

    @Override
    public void onResume() {
        if (foundCoins != null) {
            numFound.setText(String.valueOf(foundCoins.size()));
        }
        if (l != null) {
            l.setAdapter(mCoinAdapter);
            l.setOnItemClickListener(this);
        }
        if (state != null) {
            l.onRestoreInstanceState(state);
        }

        mTracker.setScreenName("Page - Search");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        super.onResume();
    }

    @Override
    public void onStop() {
        state = l.onSaveInstanceState();
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.search, container, false);
        getActivity().setTitle("Search");

        MainActivity application = (MainActivity) getActivity();
        mTracker = application.getDefaultTracker();

        final Button b = (Button) view.findViewById(R.id.btn_test);
        final EditText e = (EditText) view.findViewById(R.id.search);
        l = (ListView) view.findViewById(R.id.searchList);
        numFound = (TextView) view.findViewById(R.id.numFound);
        noResults = (TextView) view.findViewById(R.id.noneFound);


        customCoins = sharedPreference.getCustomCoins( view.getContext() );


        l.setOnItemClickListener(this);
        final InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        e.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ENTER:
                            b.callOnClick();
                            inputMethodManager.hideSoftInputFromWindow(e.getWindowToken(), 0);
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                e.setText("");
            }
        });


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchKey = e.getText().toString();
                foundCoins = new ArrayList<>();

                for (Machine[] macs : disneyMachines) {
                    for (Machine mac : macs) {
                        for (Coin c : mac.getCoins()) {
                            if (c.getTitleCoin().toLowerCase().contains(searchKey.toLowerCase())) {
                                foundCoins.add(c);
                            }
                        }
                    }
                }
                for (Machine[] macs : calMachines) {
                    for (Machine mac : macs) {
                        for (Coin c : mac.getCoins()) {
                            if (c.getTitleCoin().toLowerCase().contains(searchKey.toLowerCase())) {
                                foundCoins.add(c);
                            }
                        }
                    }
                }
                for (Machine[] macs : downtownMachines) {
                    for (Machine mac : macs) {
                        for (Coin c : mac.getCoins()) {
                            if (c.getTitleCoin().toLowerCase().contains(searchKey.toLowerCase())) {
                                foundCoins.add(c);
                            }
                        }
                    }
                }
                for (Machine[] macs : retiredMachines) {
                    for (Machine mac : macs) {
                        for (Coin c : mac.getCoins()) {
                            if (c.getTitleCoin().toLowerCase().contains(searchKey.toLowerCase())) {
                                foundCoins.add(c);
                            }
                        }
                    }
                }

                for( Coin coin : customCoins )
                {
                    if (coin.getTitleCoin().toLowerCase().contains(searchKey.toLowerCase())) {
                        foundCoins.add(coin);
                    }
                }

                numFound.setText(String.valueOf(foundCoins.size()));
                if(foundCoins.size() == 0){
                    l.setVisibility(View.INVISIBLE);
                    noResults.setVisibility(View.VISIBLE);
                }else {
                    l.setVisibility(View.VISIBLE);
                    noResults.setVisibility(View.INVISIBLE);
                    mCoinAdapter = new ListAdapter(getActivity(), R.layout.row, foundCoins);
                    l.setAdapter(mCoinAdapter);
                }
                inputMethodManager.hideSoftInputFromWindow(e.getWindowToken(), 0);

            }
        });

        TextView txtParkBanner = (TextView) view.findViewById(R.id.parkBanner);
        txtParkBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://www.parkpennies.com";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /*FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        singleCoin fragment = new singleCoin();
        Bundle bundle = new Bundle();
        Gson gson = new Gson();
        String jsonCoin = gson.toJson(mCoinAdapter.getCoinsInMachine()[position]);
        Machine machine = find(mCoinAdapter.getCoinsInMachine()[position]);
        String jsonMachine = gson.toJson(machine);
        bundle.putString("selectedCoin", jsonCoin);
        bundle.putString("selectedMachine", jsonMachine);
        fragment.setArguments(bundle);
        fragmentTransaction.setCustomAnimations(
                R.animator.fade_in,
                R.animator.fade_out,
                R.animator.fade_in,
                R.animator.fade_out);
        //fragmentTransaction.hide(this);
        //fragmentTransaction.add(R.id.mainFrag, fragment);
        fragmentTransaction.replace(R.id.mainFrag, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();*/
    }
}

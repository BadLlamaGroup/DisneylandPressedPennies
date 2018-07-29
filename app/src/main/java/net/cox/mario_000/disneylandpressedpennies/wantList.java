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

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static net.cox.mario_000.disneylandpressedpennies.Data.calMachines;
import static net.cox.mario_000.disneylandpressedpennies.Data.disneyMachines;
import static net.cox.mario_000.disneylandpressedpennies.Data.downtownMachines;
import static net.cox.mario_000.disneylandpressedpennies.Data.retiredMachines;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.find;


/**
 * Created by mario_000 on 3/29/2017.
 */


public class wantList extends Fragment implements AdapterView.OnItemClickListener {
    private final SharedPreference sharedPreference;
    private ListAdapter wantListAdapter;
    private List<Coin> wantCoins;
    private Tracker mTracker;

    public wantList() {
        sharedPreference = new SharedPreference();
    }

    public void updateImages() {
        for (Coin coin : wantCoins) {
            //outerloop:
            for (Machine[] m : disneyMachines) {
                for (Machine mach : m) {
                    for (Coin c : mach.getCoins()) {
                        if (c.getTitleCoin().equals(coin.getTitleCoin())) {
                            if (!coin.getCoinFrontImg().equals(c.getCoinFrontImg())) {
                                coin.setCoinFrontImg(c.getCoinFrontImg());
                                //                    break outerloop;
                            }
                        }
                    }
                }
            }
            //outerloop:
            for (Machine[] m : calMachines) {
                for (Machine mach : m) {
                    for (Coin c : mach.getCoins()) {
                        if (c.getTitleCoin().equals(coin.getTitleCoin())) {
                            if (!coin.getCoinFrontImg().equals(c.getCoinFrontImg())) {
                                coin.setCoinFrontImg(c.getCoinFrontImg());
                                //                  break outerloop;
                            }
                        }
                    }
                }
            }
            //outerloop:
            for (Machine[] m : downtownMachines) {
                for (Machine mach : m) {
                    for (Coin c : mach.getCoins()) {
                        if (c.getTitleCoin().equals(coin.getTitleCoin())) {
                            if (!coin.getCoinFrontImg().equals(c.getCoinFrontImg())) {
                                coin.setCoinFrontImg(c.getCoinFrontImg());
                                //                    break outerloop;
                            }
                        }
                    }
                }
            }

            //outerloop:
            for (Machine[] m : retiredMachines) {
                for (Machine mach : m) {
                    for (Coin c : mach.getCoins()) {
                        if (c.getTitleCoin().equals(coin.getTitleCoin())) {
                            if (!coin.getCoinFrontImg().equals(c.getCoinFrontImg())) {
                                coin.setCoinFrontImg(c.getCoinFrontImg());
                                //                  break outerloop;
                            }
                        }
                    }
                }
            }



            sharedPreference.saveWantedCoins(getActivity().getApplicationContext(), wantCoins);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Want It List");
        View view = inflater.inflate(R.layout.want_list, container, false);
        wantCoins = sharedPreference.getWantedCoins(getActivity().getApplicationContext());
        updateImages();
        ListView wantList = (ListView) view.findViewById(R.id.listWantCoins);
        ImageView emptyPic = (ImageView) view.findViewById(R.id.emptyListPic);
        MainActivity application = (MainActivity) getActivity();
        mTracker = application.getDefaultTracker();

        // Create list adapter for collected coins list
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(wantCoins);
        wantListAdapter = new ListAdapter(view.getContext(), R.layout.row, arrayList);

        // Check if variable is linked to view and set adapter
        if (wantList != null) {
            wantList.setAdapter(wantListAdapter);
            wantList.setOnItemClickListener(this);
        }

        if (wantCoins.size() == 0) {
            int resId = getActivity().getResources().getIdentifier("empty_want_list", "drawable", getActivity().getPackageName());
            emptyPic.setImageResource(resId);
            emptyPic.setVisibility(View.VISIBLE);
        } else {
            emptyPic.setVisibility(View.GONE);
        }

        wantListAdapter.clear();
        // Sort by machine name A-Z
        Collections.sort(wantCoins, new Comparator<Coin>() {
            @Override
            public int compare(Coin o1, Coin o2) {
                Machine mac1 = find(o1);
                Machine mac2 = find(o2);
                return mac1.getMachineName().compareTo(mac2.getMachineName());
            }
        });

        // Sort by land name A-Z
        Collections.sort(wantCoins, new Comparator<Coin>() {
            @Override
            public int compare(Coin o1, Coin o2) {
                Machine mac1 = find(o1);
                Machine mac2 = find(o2);
                return mac1.getLand().compareTo(mac2.getLand());
            }
        });

        // Add land titles and coins from land
        for (int i = 0; i < wantCoins.size(); i++) {
            String land = find(wantCoins.get(i)).getLand();
            if (!wantListAdapter.duplicateSeparator(land)) {
                wantListAdapter.addSeparatorItem(land);
            }
            wantListAdapter.add(wantCoins.get(i));
        }

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (wantListAdapter.getItem(position) instanceof Coin) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            singleCoin fragment = new singleCoin();
            Bundle bundle = new Bundle();
            Gson gson = new Gson();
            String jsonCoin = gson.toJson(wantListAdapter.getItem(position));
            Machine machine = find(wantListAdapter.getItem(position));
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
    }

    @Override
    public void onResume() {
        super.onResume();
        mTracker.setScreenName("Page - Want It List");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

}

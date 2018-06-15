package net.cox.mario_000.disneylandpressedpennies;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;

import org.askerov.dynamicgrid.DynamicGridView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static net.cox.mario_000.disneylandpressedpennies.Data.calMachines;
import static net.cox.mario_000.disneylandpressedpennies.Data.disneyMachines;
import static net.cox.mario_000.disneylandpressedpennies.Data.downtownMachines;
import static net.cox.mario_000.disneylandpressedpennies.Data.retiredMachines;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.find;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numCoins;

/**
 * Created by mario_000 on 7/6/2016.
 * Description: Fragment for coin book feature. Displays all collected coins with sorting options.
 */
public class CoinBookDetail extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
    private Button displayBut = null;
    private TextView displayText = null;
    private ListView listFav = null;
    private Spinner spinner = null;
    private List<Coin> collectedCoins = null;
    private List<Coin> customCoinList = null;
    private List<Coin> tempCoins = null;
    private final SharedPreference sharedPreference = new SharedPreference();
    private final int SPINNER_A_Z = 0;
    private final int SPINNER_Z_A = 1;
    private final int SPINNER_OLD_NEW = 2;
    private final int SPINNER_NEW_OLD = 3;
    private final int SPINNER_CUSTOM = 4;
    private final int SPINNER_LAND = 5;
    private DynamicGridView gridView;
    private GridViewAdapter gridViewAdapter;
    private ListAdapter listAdapter;
    private boolean list = false; // Boolean if list is displayed or pictures
    private TextView t = null;
    private Tracker mTracker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.coin_book, container, false);
        getActivity().setTitle("Coin Book");

        MainActivity application = (MainActivity) getActivity();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName("Page - Coin Book");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        // Get collected coins list and machines
        collectedCoins = sharedPreference.getCoins(view.getContext());
        tempCoins = sharedPreference.getCoins(view.getContext());

        // Link views
        spinner = (Spinner) view.findViewById(R.id.spinner);
        displayBut = (Button) view.findViewById(R.id.picBut);
        listFav = (ListView) view.findViewById(R.id.listFav);
        displayText = (TextView) view.findViewById(R.id.txt_book_switch);
        t = (TextView) view.findViewById(R.id.txt_total);

        t.setText(String.valueOf(numCoins));

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.Sort_Type, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("FileName", MODE_PRIVATE);
        final int spinnerValue = sharedPref.getInt("userChoiceSpinner", -1);
        if (spinnerValue != -1) {
            // set the value of the spinner
            spinner.setSelection(spinnerValue);
        }

        // TODO: 9/23/2016 Make draggable listadapter
        // Create list adapter for collected coins list
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(tempCoins);
        listAdapter = new ListAdapter(view.getContext(), R.layout.row, arrayList);
        listFav.setOnItemClickListener(this);

        // Check if variable is linked to view and set adapter
        if (listFav != null) {
            listFav.setAdapter(listAdapter);
        }

        // Check if variable is linked to view and set adapter
        gridViewAdapter = new GridViewAdapter(getActivity(), tempCoins, 3);
        gridView = (DynamicGridView) view.findViewById(R.id.dynamic_grid);
        if (gridView != null) {
            gridView.setAdapter(gridViewAdapter);
        }
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {
                if (spinner.getSelectedItemPosition() == SPINNER_CUSTOM) {
                    gridView.startEditMode(position);
                } else {
                    Toast.makeText(getActivity(), "Switch to custom", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                // Display big image
//                Intent i = new Intent(getActivity(), CoinBookBigImage.class);
//                Coin coin = (Coin) gridView.getItemAtPosition(pos);
//                Machine machine = find(coin);
//                i.putExtra("frontImg", coin.getCoinFrontImg());
//                i.putExtra("backImg", machine.getBackstampImg());
//
                Bundle args = new Bundle();
//
                if (spinner.getSelectedItemPosition() == SPINNER_CUSTOM) {
                    args.putSerializable("ARRAYLIST",(Serializable)customCoinList);
                }
                else{
                    args.putSerializable("ARRAYLIST",(Serializable)tempCoins);
                }
//                i.putExtra("BUNDLE",args);
//                i.putExtra("pos", pos);
//                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                getActivity().startActivity(i);

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                CoinBookBigImage fragment = new CoinBookBigImage();
                Gson gson = new Gson();
                //String jsonMachine = gson.toJson(coin);
                //bundle.putString("selectedCoin", jsonMachine);
                args.putInt("pos", pos);
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

        // Listener for drop after long click
        gridView.setOnDropListener(new DynamicGridView.OnDropListener() {
            @Override
            public void onActionDrop() {
                gridView.stopEditMode();
                customCoinList.clear();
                // Rebuild custom list based on edits
                for (int i = 0; i < gridView.getCount(); i++) {
                    customCoinList.add((Coin) gridView.getItemAtPosition(i));
                }

                SharedPreferences settings = getActivity().getSharedPreferences("File", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                Gson gson = new Gson();
                String jsonCoins = gson.toJson(customCoinList);
                editor.putString("custom", jsonCoins);
                editor.apply();
            }
        });

        // Switch display between pictures and list
        displayBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display pictures
                if (listFav.getVisibility() == View.VISIBLE) {
                    displayPictures();
                }
                // Display list
                else {
                    displayList();
                }
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
    public void onPause() {
        super.onPause();
        // Save which screen is shown
        list = listFav.getVisibility() == View.VISIBLE;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Show previous screen before pause
        if (list) {
            displayList();
        }
        t.setText(String.valueOf(numCoins));
    }

    private void displayPictures() {
        getActivity().setTitle("Coin Book");
        listFav.setVisibility(View.INVISIBLE);
        gridView.setVisibility(View.VISIBLE);
        displayText.setText("Display Details");
        displayBut.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.list));

        if (spinner.getSelectedItemPosition() == SPINNER_CUSTOM) {
            gridViewAdapter.clear();
            gridViewAdapter.add(customCoinList);
            gridViewAdapter.notifyDataSetChanged();
        } else {
            gridViewAdapter.clear();
            gridViewAdapter.add(tempCoins);
            gridViewAdapter.notifyDataSetChanged();
        }
    }

    public void updateImages() {
        for (Coin coin : customCoinList) {
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



            SharedPreferences settings = getActivity().getSharedPreferences("File", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            Gson gson = new Gson();
            String jsonCoins = gson.toJson(customCoinList);
            editor.putString("custom", jsonCoins);
            editor.apply();
        }
    }

    private void displayList() {
        getActivity().setTitle("Coin Book - Details");

        gridView.setVisibility(View.INVISIBLE);

        if (spinner.getSelectedItemPosition() == SPINNER_CUSTOM) {
            listAdapter.clear();
            listAdapter.addAll(customCoinList);
            listAdapter.notifyDataSetChanged();
        } else {
            listAdapter.notifyDataSetChanged();
        }
        listFav.setVisibility(View.VISIBLE);
        displayText.setText("Display Book");
        displayBut.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.book));
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        singleCoin fragment = new singleCoin();
        Bundle bundle = new Bundle();
        Gson gson = new Gson();
        String jsonCoin;
        String jsonMachine;

        // Get coin information based on sorting method
        if (listAdapter.getItem(position) instanceof Coin) {
            if (spinner.getSelectedItemPosition() == SPINNER_CUSTOM) {
                jsonCoin = gson.toJson(customCoinList.get(position));
                jsonMachine = gson.toJson(find(customCoinList.get(position)));

            } else if (spinner.getSelectedItemPosition() == SPINNER_LAND) {
                jsonCoin = gson.toJson(listAdapter.getItem(position));
                jsonMachine = gson.toJson(find(listAdapter.getItem(position)));

            } else {
                jsonCoin = gson.toJson(tempCoins.get(position));
                jsonMachine = gson.toJson(find(tempCoins.get(position)));
            }

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
    // Sort method
    public void onItemSelected(AdapterView<?> adapterView, View view, final int pos, long id) {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("FileName", 0);
        SharedPreferences.Editor prefEditor = sharedPref.edit();
        prefEditor.putInt("userChoiceSpinner", pos);
        prefEditor.apply();
        Collections.sort(tempCoins, new Comparator<Coin>() {
            @Override
            public int compare(Coin o1, Coin o2) {
                switch (pos) {
                    case SPINNER_A_Z:
                        return o1.getTitleCoin().compareTo(o2.getTitleCoin());
                    case SPINNER_Z_A:
                        return o2.getTitleCoin().compareTo(o1.getTitleCoin());
                    case SPINNER_OLD_NEW:
                        return o1.getDateCollected().compareTo(o2.getDateCollected());
                    case SPINNER_NEW_OLD:
                        return o2.getDateCollected().compareTo(o1.getDateCollected());

                    default:
                        return 0;

                }
            }
        });
        gridViewAdapter.clear();
        gridViewAdapter.add(tempCoins);
        gridViewAdapter.notifyDataSetChanged();

        listAdapter.removeSeparatorItem();
        listAdapter.clear();
        listAdapter.addAll(tempCoins);
        listAdapter.notifyDataSetChanged();


        if (pos == SPINNER_CUSTOM) {
            SharedPreferences settings = getActivity().getSharedPreferences("File", Context.MODE_PRIVATE);

            if (settings.contains("custom")) {
                String jsonFavorites = settings.getString("custom", null);
                Gson gson = new Gson();
                Coin[] customCoins = gson.fromJson(jsonFavorites, Coin[].class);
                customCoinList = Arrays.asList(customCoins);
                customCoinList = new ArrayList<>(customCoinList);

                if (collectedCoins.size() != customCoinList.size()) {
                    for (int i = collectedCoins.size() - 1; i >= 0; i--) {
                        if (!customCoinList.contains(collectedCoins.get(i))) {
                            customCoinList.add(collectedCoins.get(i));
                        }
                    }
                }

                for (int i = 0; i < customCoinList.size(); i++) {
                    if (!collectedCoins.contains(customCoinList.get(i))) {
                        customCoinList.remove(i);
                    }
                }

                SharedPreferences.Editor editor = settings.edit();
                String jsonCoins = gson.toJson(customCoinList);
                editor.putString("custom", jsonCoins);
                editor.apply();

                updateImages();
                gridViewAdapter.clear();
                gridViewAdapter.add(customCoinList);
                gridViewAdapter.notifyDataSetChanged();
                listAdapter.clear();
                listAdapter.addAll(customCoinList);
                listAdapter.notifyDataSetChanged();
            } else {
                customCoinList = new ArrayList<>();
                customCoinList.addAll(tempCoins);
            }
        }

        if (pos == SPINNER_LAND) {
            listAdapter.clear();
            // Sort by machine name A-Z
            Collections.sort(tempCoins, new Comparator<Coin>() {
                @Override
                public int compare(Coin o1, Coin o2) {
                    Machine mac1 = find(o1);
                    Machine mac2 = find(o2);
                    return mac1.getMachineName().compareTo(mac2.getMachineName());
                }
            });

            // Sort by land name A-Z
            Collections.sort(tempCoins, new Comparator<Coin>() {
                @Override
                public int compare(Coin o1, Coin o2) {
                    Machine mac1 = find(o1);
                    Machine mac2 = find(o2);
                    return mac1.getLand().compareTo(mac2.getLand());
                }
            });

            // Add land titles and coins from land
            for (int i = 0; i < tempCoins.size(); i++) {
                String land = find(tempCoins.get(i)).getLand();
                if (!listAdapter.duplicateSeparator(land)) {
                    listAdapter.addSeparatorItem(land);
                }
                listAdapter.add(tempCoins.get(i));
            }
            gridViewAdapter.clear();
            gridViewAdapter.add(tempCoins);
            gridViewAdapter.notifyDataSetChanged();
            listAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
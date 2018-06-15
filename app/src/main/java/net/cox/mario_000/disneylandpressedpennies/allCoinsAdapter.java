package net.cox.mario_000.disneylandpressedpennies;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static net.cox.mario_000.disneylandpressedpennies.MainActivity.find;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.img;

/**
 * Created by mario_000 on 7/7/2016.
 * Description: Adapter for creating the lists for all coins
 */
public class allCoinsAdapter extends ArrayAdapter<Coin> {
    private final Context context;
    private final int mResource;
    private Coin coin;
    private ArrayList coins;
    private List<Coin> collectedCoins;
    private List<Coin> wantCoins;
    private final SharedPreference sharedPreference;
    private ArrayList mSeparatorsSet = new ArrayList();
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;
    LayoutInflater inflater;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
    private String[] newCoins;
    private String[] offMachine;

    public allCoinsAdapter(Context context, int resource, ArrayList coins) {
        super(context, resource, coins);
        this.context = context;
        this.mResource = resource;
        this.coins = coins;
        this.sharedPreference = new SharedPreference();
        collectedCoins = sharedPreference.getCoins(getContext());
        wantCoins = sharedPreference.getWantedCoins(getContext());
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        newCoins = getContext().getResources().getStringArray(R.array.new_coins);
        offMachine = getContext().getResources().getStringArray(R.array.off_mac);
        String a = "Testing";
    }

    public void addSeparatorItem(final String item) {
        coins.add(item);
        // Save separator position
        mSeparatorsSet.add(item);
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View row, ViewGroup parent) {

        final CoinHolder holder = new CoinHolder();

        // Check if coin or land name
        int type = getItemViewType(position);
        switch (type) {
            case TYPE_ITEM:
                row = inflater.inflate(R.layout.row, null);
                holder.description = (TextView) row.findViewById(R.id.rowDescription);
                holder.name = (TextView) row.findViewById(R.id.rowName);
                holder.collected = (TextView) row.findViewById(R.id.rowCollected);
                holder.imageView = (ImageView) row.findViewById(R.id.imgCoin);
                holder.new_coins_img = (ImageView) row.findViewById(R.id.newCoin);
                holder.off_mac_img = (ImageView) row.findViewById(R.id.offMac);
                holder.row_new_bg = (RelativeLayout) row.findViewById(R.id.row_new_bg);
                break;
            case TYPE_SEPARATOR:
                row = inflater.inflate(R.layout.header, null);
                holder.separator = (TextView) row.findViewById(R.id.separator);
                break;
        }

        if (type == TYPE_ITEM) {

            // Get coin
            coin = (Coin) coins.get(position);
            // Find machine that coin belongs to
            Machine mac = find(coin);

            // Set image and res id
            int resId = context.getResources().getIdentifier(coin.getCoinFrontImg(), "drawable", context.getPackageName());
            img.loadBitmap(resId, context.getResources(), 100, 140, holder.imageView, 0);


//            DisplayMetrics displayMetrics = new DisplayMetrics();
//            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//            Display display = wm.getDefaultDisplay();
//            display.getMetrics(displayMetrics);
//            int width = displayMetrics.widthPixels;

//            holder.new_coins_img.getLayoutParams().height = (int) ((width / 4) * .82);
//            holder.new_coins_img.getLayoutParams().width = (width / 4);
//
//            holder.off_mac_img.getLayoutParams().height = (int) ((width / 4) * .82);
//            holder.off_mac_img.getLayoutParams().width = (width / 4);

            img.setToGray(holder.imageView);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    holder.name.setSelected(true);
                }
            }, 1500);



            if (Arrays.asList(newCoins).contains(mac.getMachineName())) {
                holder.row_new_bg.setBackgroundColor(Color.CYAN);
                //holder.description.setTextColor(ContextCompat.getColor(context, R.color.colorOrange));
                //holder.name.setTextColor(ContextCompat.getColor(context, R.color.colorOrange));
                //holder.collected.setTextColor(ContextCompat.getColor(context, R.color.colorOrange));
                holder.new_coins_img.setVisibility(View.VISIBLE);
            } else if (Arrays.asList(offMachine).contains(mac.getMachineName())) {
                holder.row_new_bg.setBackgroundColor(Color.YELLOW);
                //holder.description.setTextColor(Color.RED);
                //holder.name.setTextColor(Color.RED);
                //holder.collected.setTextColor(Color.RED);
                holder.off_mac_img.setVisibility(View.VISIBLE);
            } else {
                holder.description.setTextColor(Color.GRAY);
                holder.name.setTextColor(Color.GRAY);
                holder.collected.setTextColor(Color.GRAY);
                holder.row_new_bg.setBackgroundColor(Color.WHITE);
                holder.collected.setText("Not yet collected");
            }

            if (collectedCoins.contains(coin)) {
                img.setToColor(holder.imageView);
                holder.description.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
                holder.name.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
                holder.collected.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
                holder.collected.setText(dateFormat.format(collectedCoins.get(collectedCoins.indexOf(coin)).getDateCollected()));
            }

            if (checkWant(coin)) {
                holder.description.setTextColor(ContextCompat.getColor(context, R.color.colorGreen));
                holder.name.setTextColor(ContextCompat.getColor(context, R.color.colorGreen));
                holder.collected.setTextColor(ContextCompat.getColor(context, R.color.colorGreen));
                holder.collected.setText("Want It");
            }


            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentTransaction fragmentTransaction = ((Activity) context).getFragmentManager().beginTransaction();
                    singleCoin fragment = new singleCoin();
                    Bundle bundle = new Bundle();
                    Gson gson = new Gson();

                    // Save coin
                    String jsonCoin = gson.toJson(coins.get(position));
                    String jsonMachine = null;

                    // Save machine
                    Machine mac = find((Coin) coins.get(position));
                    if (mac != null) {
                        jsonMachine = gson.toJson(mac);
                    }

                    view.setTag(position);

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
            });
            holder.imageView.setOnClickListener(PopupListener);

            //Set which row was clicked
            Integer rowPos = position;
            holder.imageView.setTag(rowPos);

            //Set text value for row
            holder.name.setText(String.valueOf(coin.getTitleCoin()));
            holder.description.setText(String.valueOf(mac.getMachineName()));

            if (collectedCoins.size() == 0 && wantCoins.size() == 0) {
                img.setToGray(holder.imageView);
                holder.description.setTextColor(Color.GRAY);
                holder.name.setTextColor(Color.GRAY);
                holder.collected.setTextColor(Color.GRAY);
            }
        } else {
            // Set separator text
            if (position == 0 || mSeparatorsSet.contains(coins.get(position))) {
                coin = (Coin) coins.get(position + 1);
            } else {
                coin = (Coin) coins.get(position);
            }
            Machine mac = find(coin);

            if (Arrays.asList(newCoins).contains(mac.getMachineName())) {
                holder.separator.setText(mac.getMachineName() + " - NEW");
            } else if (Arrays.asList(offMachine).contains(mac.getMachineName())) {
                holder.separator.setText(mac.getMachineName() + " - Inaccessible");
            } else {
                holder.separator.setText(mac.getMachineName());
            }


        }

        return row;
    }

    // Check if coin is in collected coins
    private boolean checkWant(Coin checkCoin) {
        boolean check = false;

        if (wantCoins != null) {
            for (Coin coin : wantCoins) {
                // Check if coin matches coin already collected
                if (String.valueOf(coin.getTitleCoin()).equals(String.valueOf(checkCoin.getTitleCoin()))) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    private final View.OnClickListener PopupListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Get which row was clicked
            Integer viewPos = (Integer) v.getTag();
            Coin coin = (Coin) coins.get(viewPos);
            Intent i = new Intent(context, bigImage.class);
            i.putExtra("frontImg", coin.getCoinFrontImg());

            Machine mac = find(coin);
            i.putExtra("backImg", mac.getBackstampImg());
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    };

    private static class CoinHolder {
        TextView name;
        TextView description;
        TextView collected;
        ImageView imageView;
        TextView separator;
        ImageView new_coins_img;
        ImageView off_mac_img;
        RelativeLayout row_new_bg;
    }

    @Override
    public int getItemViewType(int position) {
        return mSeparatorsSet.contains(coins.get(position)) ? TYPE_SEPARATOR : TYPE_ITEM;
    }
}
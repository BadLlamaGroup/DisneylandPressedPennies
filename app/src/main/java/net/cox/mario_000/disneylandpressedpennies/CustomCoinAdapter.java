package net.cox.mario_000.disneylandpressedpennies;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static net.cox.mario_000.disneylandpressedpennies.MainActivity.img;

/**
 * Created by mario_000 on 7/14/2018.
 */

public class CustomCoinAdapter extends ArrayAdapter<Coin> implements View.OnClickListener{

    private final Context context;
    private final int mResource;
    private final Coin[] customCoins;
    private final SharedPreference sharedPreference;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);

    public CustomCoinAdapter(Context context, int resource, Coin[] coins) {
        super(context, resource, coins);
        this.context = context;
        this.mResource = resource;
        this.customCoins = coins;
        sharedPreference = new SharedPreference();
    }

    @Override
    public View getView(final int position, View row, ViewGroup parent) {

        final CoinHolder holder;
        // Get collected coins list
        List<Coin> savedCoins = sharedPreference.getCustomCoins(getContext());
        if (row == null) {
            // Set up layout for row
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(mResource, parent, false);

            //Link views
            holder = new CoinHolder();
            holder.name = row.findViewById(R.id.rowName);
            holder.description = row.findViewById(R.id.rowDescription);
            holder.collected = row.findViewById(R.id.rowCollected);
            holder.imageView = row.findViewById(R.id.imgCoin);

            //Set position of row
            row.setTag(holder);
        } else {
            //Get position of row
            holder = (CoinHolder) row.getTag();
        }
        // Get single coin
        Coin coin = customCoins[position];
        Coin tempCoin = coin;

        // Check if coin is collected
        for (Coin c : savedCoins){
            if(c.equals(coin)){
                tempCoin = savedCoins.get(savedCoins.indexOf(c));
            }
        }

        //Set image and resId
        int resId = context.getResources().getIdentifier(coin.getCoinFrontImg(), "drawable", context.getPackageName());
        img.loadBitmap(resId, context.getResources(), 100, 200, holder.imageView, 0);
        holder.imageView.setOnClickListener(this);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                holder.name.setSelected(true); // Make text scroll
            }
        }, 1500);


        //Set which row was clicked
        Integer rowPos = position;
        holder.imageView.setTag(rowPos);

        //Set text values for row
        holder.name.setText(String.valueOf(coin.getTitleCoin()));
        if(tempCoin.getDateCollected() != null){
            holder.collected.setText("Collected: " + dateFormat.format(tempCoin.getDateCollected()));
        }
        else{
            if(checkWant(coin)){
                holder.collected.setText("Want It");
            }else{
                holder.collected.setText("Not yet collected");
            }

        }

        return row;
    }
    // Check if coin is in collected coins
    private boolean checkWant(Coin checkCoin) {
        boolean check = false;
        // Get collected coins list
        List<Coin> wantCoins = sharedPreference.getWantedCoins(getContext());

        if (wantCoins != null) {
            for (Coin coin : wantCoins) {
                // Check if coin matches coin already collected
                if (String.valueOf(coin.getTitleCoin()).equals(String.valueOf(checkCoin.getTitleCoin()))) {
                    check = true;
                    //collectedDate = coin.getDateCollected();
                    break;
                }
            }
        }
        return check;
    }

    @Override
    public void onClick(View v) {
        //Get which row was clicked
        /*Integer viewPos = (Integer) v.getTag();
        Coin coin = customCoins[viewPos];
        Intent i = new Intent(context, bigImage.class);
        i.putExtra("frontImg", coin.getCoinFrontImg());
        i.putExtra("backImg", machine.getBackstampImg());
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);*/

    }

    public Coin[] getCustomCoins() {
        return customCoins;
    }

    private static final class CoinHolder {
        TextView name;
        TextView description;
        TextView collected;
        ImageView imageView;
    }
}

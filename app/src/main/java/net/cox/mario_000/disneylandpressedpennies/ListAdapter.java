package net.cox.mario_000.disneylandpressedpennies;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TreeSet;

import static net.cox.mario_000.disneylandpressedpennies.MainActivity.find;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.img;

/**
 * Created by mario_000 on 7/7/2016.
 * Description: Adapter class for creating lists
 */
public class ListAdapter extends ArrayAdapter<Coin> {
    private final Context context;
    private final int mResource;
    private final ArrayList tempCoins;
    private TreeSet<Integer> mSeparatorsSet = new TreeSet<>();
    private final SharedPreference sharedPreference;
    private Machine machine;
    LayoutInflater inflater;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
    //List<Machine> machines = null;

    public ListAdapter(Context context, int resource, ArrayList coins) {
        super(context, resource, coins);
        this.context = context;
        this.mResource = resource;
        this.tempCoins = coins;
        this.sharedPreference = new SharedPreference();
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addSeparatorItem(final String item) {
        tempCoins.add(item);
        // save separator position
        mSeparatorsSet.add(tempCoins.size() - 1);
        notifyDataSetChanged();
    }

    public void removeSeparatorItem() {
        mSeparatorsSet.clear();
        notifyDataSetChanged();
    }

    public boolean duplicateSeparator(final String item) {
        return tempCoins.contains(item);
    }

    @Override
    public View getView(final int position, View row, ViewGroup parent) {

        int type = getItemViewType(position);

        switch (type) {
            case TYPE_ITEM:
                CoinHolder holder = new CoinHolder();
                row = inflater.inflate(R.layout.row, parent, false);
                holder.name = (TextView) row.findViewById(R.id.rowName);
                holder.description = (TextView) row.findViewById(R.id.rowDescription);
                holder.collected = (TextView) row.findViewById(R.id.rowCollected);
                holder.imageView = (ImageView) row.findViewById(R.id.imgCoin);
                row.setTag(holder);

                Coin coin = (Coin) tempCoins.get(position);

                //Set image and res id
                int resId = context.getResources().getIdentifier(coin.getCoinFrontImg(), "drawable", context.getPackageName());
                img.loadBitmap(resId, context.getResources(), 100, 140, holder.imageView, 0);
                holder.imageView.setOnClickListener(PopupListener);

                //Set which row was clicked
                holder.imageView.setTag( position );

                // Find machine that coin belongs to
                Machine mac = find(coin);

                //Set text value for row
                // TODO: 2/10/2017 Remove machine causes crash because it needs machine name
                holder.name.setText(String.valueOf(coin.getTitleCoin()));
                if(mac != null){
                    holder.description.setText(String.valueOf(mac.getMachineName()));
                } else{
                    holder.description.setText("N/A");
                }

                if(coin.getDateCollected() != null){
                    holder.collected.setText(dateFormat.format(coin.getDateCollected()));
                }


                return row;

            case TYPE_SEPARATOR:
                ViewHolder sep = new ViewHolder();
                row = inflater.inflate(R.layout.header, parent, false);
                sep.separator = (TextView) row.findViewById(R.id.separator);
                row.setTag(sep);

                if (position == 0 || mSeparatorsSet.contains(position)) {
                    sep.separator.setText(tempCoins.get(position).toString());
                }
                return row;
        }

        return row;
    }

    private final View.OnClickListener PopupListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Get which row was clicked
            Integer viewPos = (Integer) v.getTag();
            Intent i = new Intent(context, BigImage.class);
            Coin coin = (Coin) tempCoins.get(viewPos);
            machine = find(coin);
            i.putExtra("frontImg", coin.getCoinFrontImg());
            i.putExtra("backImg", machine.getBackstampImg());
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    };

    @Override
    public int getItemViewType(int position) {
        return mSeparatorsSet.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    private static class CoinHolder {
        TextView name;
        TextView description;
        TextView collected;
        ImageView imageView;
    }

    static class ViewHolder {
        TextView separator;
    }
}
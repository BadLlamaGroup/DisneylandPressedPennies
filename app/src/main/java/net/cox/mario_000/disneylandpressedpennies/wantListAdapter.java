package net.cox.mario_000.disneylandpressedpennies;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.TreeSet;

import static net.cox.mario_000.disneylandpressedpennies.MainActivity.find;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.img;

/**
 * Created by mario_000 on 7/7/2016.
 * Description: Adapter class for creating lists
 */
public class wantListAdapter extends ArrayAdapter<Coin> {
    private final Context context;
    private final int mResource;
    private final ArrayList tempCoins;
    private TreeSet<Integer> mSeparatorsSet = new TreeSet<>();
    private final SharedPreference sharedPreference;
    private Machine machine;
    LayoutInflater inflater;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;

    public wantListAdapter(Context context, int resource, ArrayList coins) {
        super(context, resource, coins);
        this.context = context;
        this.mResource = resource;
        this.tempCoins = coins;
        this.sharedPreference = new SharedPreference();
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        int type = getItemViewType(position);
        switch (type) {
            case TYPE_ITEM:
                ViewHolder holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.row, parent, false);
                holder.name = (TextView) convertView.findViewById(R.id.rowName);
                holder.description = (TextView) convertView.findViewById(R.id.rowDescription);
                holder.collected = (TextView) convertView.findViewById(R.id.rowCollected);
                holder.imageView = (ImageView) convertView.findViewById(R.id.imgCoin);
                Coin coin = (Coin) tempCoins.get(position);
                convertView.setTag(holder);

                //Set image and res id
                int resId = context.getResources().getIdentifier(coin.getCoinFrontImg(), "drawable", context.getPackageName());
                img.loadBitmap(resId, context.getResources(), 150, 300, holder.imageView, 0);
                //holder.imageView.setImageResource(resId);
                holder.imageView.setOnClickListener(PopupListener);

                //Set which row was clicked
                Integer rowPos = position;
                holder.imageView.setTag(rowPos);

                // Find machine that coin belongs to
                Machine mac = find(coin);

                //Set text value for row
                // TODO: 2/10/2017 Remove machine causes crash because it needs machine name
                holder.name.setText(String.valueOf(coin.getTitleCoin()));
                holder.description.setText(String.valueOf(mac.getMachineName()));
                holder.collected.setText("Want It");

                return convertView;

            case TYPE_SEPARATOR:
                SepHolder sep = new SepHolder();
                convertView = inflater.inflate(R.layout.header, parent, false);
                sep.separator = (TextView) convertView.findViewById(R.id.separator);
                convertView.setTag(sep);

                if (position == 0 || mSeparatorsSet.contains(position)) {
                    sep.separator.setText(tempCoins.get(position).toString());
                }
                return convertView;
        }

        return convertView;

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


    private static class ViewHolder {
        TextView name;
        TextView description;
        TextView collected;
        ImageView imageView;
    }

    static class SepHolder {
        TextView separator;
    }


}
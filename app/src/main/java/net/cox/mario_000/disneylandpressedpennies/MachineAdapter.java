package net.cox.mario_000.disneylandpressedpennies;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import static net.cox.mario_000.disneylandpressedpennies.MainActivity.img;

/**
 * Created by mario_000 on 6/25/2016.
 * Description: Adapter for creating a machine row
 */
public class MachineAdapter extends ArrayAdapter<Machine> implements View.OnClickListener {
    private final Context context;
    private final int mResource;
    Machine[] machines;
    private final SharedPreference sharedPreference;
    private List<Coin> collectedCoins;


    public MachineAdapter(Context context, int resource, Machine[] machines) {
        super(context, resource, machines);
        this.context = context;
        this.mResource = resource;
        this.machines = machines;
        this.sharedPreference = new SharedPreference();
        collectedCoins = sharedPreference.getCoins(getContext());

    }

    @Override
    public View getView(int position, View row, ViewGroup parent) {

        final MachineHolder holder;
        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(mResource, parent, false);

            //Link views
            holder = new MachineHolder();
            holder.location = (TextView) row.findViewById(R.id.rowName);
            holder.imageView = (ImageView) row.findViewById(R.id.imgMac);
            holder.description = (TextView) row.findViewById(R.id.rowDescription);
            holder.collected = (TextView) row.findViewById(R.id.rowCollected);
            //Set position of row
            row.setTag(holder);
        } else {
            //Get position of row
            holder = (MachineHolder) row.getTag();
        }

        //Get single machine
        Machine machine = machines[position];

        //Set image and res Id
        int resId = context.getResources().getIdentifier(machine.getMachineImg(), "drawable", context.getPackageName());
        img.loadBitmap(resId, context.getResources(), 150, 300, holder.imageView, 0);
        holder.imageView.setOnClickListener(this);

        //Set which row was clicked
        Integer rowPos = position;
        holder.imageView.setTag(rowPos);

        //Set text values for row
        holder.location.setText(String.valueOf(machine.getMachineName()));

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                holder.location.setSelected(true); // Make text scroll
            }
        }, 1500);

        holder.description.setText("Type of coin: " + String.valueOf(machine.getTypeCoin()));

        Coin[] coins = machine.getCoins();
        int have = 0;
        for (Coin c : coins) {
            if (collectedCoins.contains(c)) {
                have++;
            }
        }

        holder.collected.setText(String.valueOf("Collected: " + have + " / " + coins.length));
        RelativeLayout new_mac_bg = (RelativeLayout) row.findViewById(R.id.machine_new_bg);
        ImageView new_coins_img = (ImageView) row.findViewById(R.id.newCoin);
        ImageView off_mac_img = (ImageView) row.findViewById(R.id.offMac);


//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        Display display = wm.getDefaultDisplay();
//        display.getMetrics(displayMetrics);
//        int width = displayMetrics.widthPixels;

//        new_coins_img.getLayoutParams().height = (int) ((width / 4) * .82);
//        new_coins_img.getLayoutParams().width = (width / 4);
//
//        off_mac_img.getLayoutParams().height = (int) ((width / 4) * .82);
//        off_mac_img.getLayoutParams().width = (width / 4);

        String[] newCoins = getContext().getResources().getStringArray(R.array.new_coins);
        String[] offMachine = getContext().getResources().getStringArray(R.array.off_mac);

        if (Arrays.asList(newCoins).contains(machine.getMachineName())) {
            new_mac_bg.setBackgroundColor(Color.CYAN);
            new_coins_img.setVisibility(View.VISIBLE);
        } else if(Arrays.asList(offMachine).contains(machine.getMachineName())){
            new_mac_bg.setBackgroundColor(Color.YELLOW);
            holder.location.setText(String.format("%s - %s", String.valueOf(machine.getMachineName()), "Inaccessible"));
            off_mac_img.setVisibility(View.VISIBLE);
        }
        else {
            new_mac_bg.setBackgroundColor(Color.WHITE);
        }


        return row;
    }

    @Override
    public void onClick(View v) {
        //Get which row was clicked
        Integer viewPos = (Integer) v.getTag();
        Machine machine = machines[viewPos];
        Intent i = new Intent(getContext(), BigImage.class);
        i.putExtra("frontImg", machine.getMachineImg());
        i.putExtra("backImg", machine.getMachineImg());
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(i);
    }

    private static class MachineHolder {
        TextView location;
        TextView description;
        TextView collected;
        ImageView imageView;
    }
}
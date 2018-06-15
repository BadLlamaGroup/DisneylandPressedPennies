package net.cox.mario_000.disneylandpressedpennies;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import static net.cox.mario_000.disneylandpressedpennies.MainActivity.find;

/**
 * Created by mario_000 on 1/28/2017.
 */

public class CoinBookImageOverlay extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.coin_book_overlay, container, false);


        TextView t = (TextView) view.findViewById(R.id.txt_title);
        TextView l = (TextView) view.findViewById(R.id.txt_date);
        TextView m = (TextView) view.findViewById(R.id.txt_mac);
        TextView k = (TextView) view.findViewById(R.id.txt_land);

        Bundle extras = getArguments();
            // Get selected machine from json
            Gson gson = new Gson();
            String jsonCoin = extras.getString("pos");
            Coin c = gson.fromJson(jsonCoin, Coin.class);
        t.setText(c.getTitleCoin());
        l.setText(c.getDateCollected().toString());
        m.setText(find(c).getMachineName());
        k.setText(find(c).getLand());

        return view;
    }
}

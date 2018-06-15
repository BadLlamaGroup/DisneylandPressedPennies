package net.cox.mario_000.disneylandpressedpennies;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static net.cox.mario_000.disneylandpressedpennies.MainActivity.find;

/**
 * Created by mario_000 on 1/21/2017.
 */

public class CoinBookBigImage extends Fragment implements ViewPager.OnPageChangeListener{
    private List<Coin> object;
    private TextView t;
    private TextView l;
    private TextView m;
    private TextView k;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.coin_book_pager, container, false);

        Bundle args = getArguments();
        int pos = args.getInt("pos");

        //Bundle args = extras;//getBundleExtra("BUNDLE");
        object = (List<Coin>) args.getSerializable("ARRAYLIST");
        getActivity().setTitle(object.get(pos).getTitleCoin());
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        CoinBookImageAdapter adapter = new CoinBookImageAdapter(getActivity(), object);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(pos);
        viewPager.setOffscreenPageLimit(10);
        viewPager.addOnPageChangeListener(this);

        t = (TextView) view.findViewById(R.id.txt_title);
        l = (TextView) view.findViewById(R.id.txt_date);
        m = (TextView) view.findViewById(R.id.txt_mac);
        k = (TextView) view.findViewById(R.id.txt_land);

        Coin c = object.get(pos);
        t.setText(c.getTitleCoin());
        l.setText(dateFormat.format(c.getDateCollected()));
        m.setText(find(c).getMachineName());
        k.setText(find(c).getLand());


        return view;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        getActivity().setTitle(object.get(position).getTitleCoin());
        Coin c = object.get(position);
        t.setText(c.getTitleCoin());
        l.setText(dateFormat.format(c.getDateCollected()));
        m.setText(find(c).getMachineName());
        k.setText(find(c).getLand());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

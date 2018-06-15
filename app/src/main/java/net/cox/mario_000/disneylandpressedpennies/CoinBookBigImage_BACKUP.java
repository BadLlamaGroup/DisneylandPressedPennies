package net.cox.mario_000.disneylandpressedpennies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import java.util.List;

/**
 * Created by mario_000 on 1/21/2017.
 */

public class CoinBookBigImage_BACKUP extends Activity{



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coin_book_pager);

        Bundle extras = getIntent().getExtras();
        Intent intent = getIntent();
        int pos = extras.getInt("pos");
        Bundle args = intent.getBundleExtra("BUNDLE");
        List<Coin> object = (List<Coin>) args.getSerializable("ARRAYLIST");

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        CoinBookImageAdapter adapter = new CoinBookImageAdapter(getApplicationContext(), object);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(pos);


    }

}

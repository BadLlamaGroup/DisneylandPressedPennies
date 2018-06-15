package net.cox.mario_000.disneylandpressedpennies;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static net.cox.mario_000.disneylandpressedpennies.MainActivity.img;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numArcCoinsCollected;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numCalCoinsCollected;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numCoins;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numDisneyCoinsCollected;
import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numDowntownCoinsCollected;

/**
 * Created by mario_000 on 6/25/2016.
 * Description: Fragment for displaying information for single coin
 */
public class singleCoin extends Fragment implements Data, View.OnClickListener {

    private Date collectedDate = null;
    private ImageView coinFront = null;
    private ImageView coinBack = null;
    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;
    private boolean mIsBackVisible = false;
    private final int mInterval = 2000; // Duration of spin animation
    private Handler mHandler;
    private Coin coin;
    private Machine machine;
    private final SharedPreference sharedPreference;
    private Context context;
    private TextView txtDate;
    private TextView txtMac;
    private CheckBox haveCoinBox;
    private CheckBox wantItBox;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
    private Tracker mTracker;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    Calendar myCalendar;

    public singleCoin() {
        sharedPreference = new SharedPreference();
    }


    public void showNoticeDialog() {
        final Dialog removeDialog = new Dialog(context, R.style.CustomDialog);
        // Set up dialog window
        removeDialog.setContentView(R.layout.coin_remove_dialog);
        removeDialog.setTitle("Title...");
        removeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        removeDialog.setCancelable(false);
        TextView coinName = (TextView) removeDialog.findViewById(R.id.txt_coin);
        coinName.setText(coin.getTitleCoin());
        // Link buttons
        Button noBtn = (Button) removeDialog.findViewById(R.id.btn_no);
        Button removeBtn = (Button) removeDialog.findViewById(R.id.btn_yes);

        // Listeners for buttons
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                haveCoinBox.setChecked(true);
                removeDialog.dismiss();
            }
        });

        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                haveCoinBox.setChecked(false);
                wantItBox.setEnabled(true);
                txtDate.setText("Not yet collected");
                sharedPreference.removeCoin(getActivity().getApplicationContext(), coin);
                updateCoinTotals(true);
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Coin Removed")
                        .setAction("Removed")
                        .setLabel(coin.getTitleCoin())
                        .setValue(1)
                        .build());
                removeDialog.dismiss();
            }
        });

        removeDialog.show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_single_coin, container, false);
        context = view.getContext();

        MainActivity application = (MainActivity) getActivity();
        mTracker = application.getDefaultTracker();

        //Link views
        TextView titleCoin = (TextView) view.findViewById(R.id.txt_title);
        TextView machineName = (TextView) view.findViewById(R.id.txt_land);
        txtDate = (TextView) view.findViewById(R.id.txtDateCollected);
        coinFront = (ImageView) view.findViewById(R.id.spinningCoinFront);
        coinBack = (ImageView) view.findViewById(R.id.spinningCoinBack);
        haveCoinBox = (CheckBox) view.findViewById(R.id.boxCollected);
        wantItBox = (CheckBox) view.findViewById(R.id.boxWant);
        txtMac = (TextView) view.findViewById(R.id.txtMachine);

        // Get calendar
        myCalendar = Calendar.getInstance();

        // Update coin count
        numCoins = sharedPreference.getCoins(context).size();

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                // Update coin date info
                sharedPreference.removeCoin(getActivity().getApplicationContext(), coin);
                if (haveCoinBox.isChecked()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Date Updated", Toast.LENGTH_SHORT).show();
                    coin.setDateCollected(myCalendar.getTime());
                    sharedPreference.addCoin(getActivity().getApplicationContext(), coin);
                    txtDate.setText(dateFormat.format(myCalendar.getTime()));
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Date Selected", Toast.LENGTH_SHORT).show();
                    haveCoinBox.setChecked(true);
                }
            }

        };

        txtDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Open calendar window
                new DatePickerDialog(getActivity(), onDateSetListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Bundle extras = getArguments();
        if (extras != null) {
            Gson gson = new Gson();
            String jsonCoin = extras.getString("selectedCoin");
            String jsonMac = extras.getString("selectedMachine");
            machine = gson.fromJson(jsonMac, Machine.class);
            coin = gson.fromJson(jsonCoin, Coin.class);

            // Display machine name in nav bar
            getActivity().setTitle(machine.getMachineName());
            // Display images
            int resId = view.getContext().getResources().getIdentifier(coin.getCoinFrontImg(), "drawable", view.getContext().getPackageName());
            int resId2;
            if(machine.getBackstampImg() == null){
                resId2 = view.getContext().getResources().getIdentifier(coin.getCoinFrontImg() + "_backstamp", "drawable", view.getContext().getPackageName());
            }else {
                resId2 = view.getContext().getResources().getIdentifier(machine.getBackstampImg(), "drawable", view.getContext().getPackageName());
            }
            img.loadBitmap(resId, getResources(), 800, 800, coinFront, 0);
            BitmapFactory.Options dimensions = new BitmapFactory.Options();
            dimensions.inJustDecodeBounds = true;
            Bitmap mBitmap = BitmapFactory.decodeResource(getActivity().getResources(), resId, dimensions);
            int height = dimensions.outHeight;
            int width = dimensions.outWidth;

            //Set orientation based on coin direction
            if (width > height) {
                Bitmap mBitmap2 = BitmapFactory.decodeResource(getActivity().getResources(), resId2);
                if(mBitmap2 == null){
                    int resId3 = view.getContext().getResources().getIdentifier("new_searching", "drawable", view.getContext().getPackageName());
                    mBitmap2 = BitmapFactory.decodeResource(getActivity().getResources(), resId3);
                }
                if (mBitmap2.getWidth() < mBitmap2.getHeight()) {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                    Bitmap bitmap = Bitmap.createBitmap(mBitmap2, 0, 0, mBitmap2.getWidth(), mBitmap2.getHeight(), matrix, true);
                    coinBack.setImageBitmap(bitmap);
                } else {
                    img.loadBitmap(resId2, getResources(), 800, 800, coinBack, resId);
                }

            } else {
                img.loadBitmap(resId2, getResources(), 800, 800, coinBack, resId);
            }


            // Set animations
            mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(view.getContext(), R.animator.flip1);
            mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(view.getContext(), R.animator.flip2);

            // Set camera distance
            int distance = 8000;
            float scale = getResources().getDisplayMetrics().density * distance;
            coinFront.setCameraDistance(scale);
            coinBack.setCameraDistance(scale);

            // Start rotation of coin
            mHandler = new Handler();

            // Set data
            titleCoin.setText(coin.getTitleCoin());
            machineName.setText(machine.getLand());
            txtMac.setText(machine.getMachineName());


            // Set listeners for coin
            coinFront.setOnClickListener(this);
            coinBack.setOnClickListener(this);

            // Set state of checkbox based on if coin is in collected
            if (checkCoin(coin)) {
                haveCoinBox.setChecked(true);
                wantItBox.setEnabled(false);
                txtDate.setText(dateFormat.format(collectedDate));
            } else {
                wantItBox.setEnabled(true);
                haveCoinBox.setChecked(false);
            }
            if (checkWant(coin)) {
                wantItBox.setChecked(true);
                txtDate.setText("Want It");
            } else {
                wantItBox.setChecked(false);
            }

            wantItBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        // Add coin to want list
                        if (!checkCoin(coin)) { // Check if coin is already collected
                            txtDate.setText("Want It");
                            sharedPreference.addWantedCoin(getActivity().getApplicationContext(), coin);
                        }
                    } else {
                        if (txtDate.getText().equals("Want It")) {
                            txtDate.setText("Not yet collected");
                        }
                        sharedPreference.removeWantedCoin(getActivity().getApplicationContext(), coin);
                    }
                }
            });

            haveCoinBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        //Check if it's already in list
                        if (!checkCoin(coin)) {
                            // Add coin to collected list
                            coin.setDateCollected(myCalendar.getTime());
                            txtDate.setText(dateFormat.format(myCalendar.getTime()));

                            if (wantItBox.isChecked()) {
                                wantItBox.setChecked(false);
                                wantItBox.setEnabled(false);
                                sharedPreference.removeWantedCoin(getActivity().getApplicationContext(), coin);
                            } else {
                                wantItBox.setEnabled(false);
                            }


                            sharedPreference.addCoin(getActivity().getApplicationContext(), coin);
                            updateCoinTotals(false);


                            mTracker.send(new HitBuilders.EventBuilder()
                                    .setCategory("Coin Collected")
                                    .setAction("Collected")
                                    .setLabel(coin.getTitleCoin())
                                    .setValue(1)
                                    .build());
                        }
                    } else {
                        showNoticeDialog();
                    }
                }
            });


            TextView txtParkBanner = (TextView) view.findViewById(R.id.parkBanner);
            txtParkBanner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = "http://www.parkpennies.com";
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Website")
                            .setAction("Clicked")
                            .setLabel(coin.getTitleCoin())
                            .setValue(1)
                            .build());
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);

                }
            });

        }
        return view;
    }

    private void updateCoinTotals(boolean remove) {
        for (Machine[] m : disneyMachines) {
//            List<Machine> a = Arrays.asList(m);
//            if(a.contains(machine)){
//
//            }
            for (Machine mac : m) {
                if (mac.getMachineName().equals(machine.getMachineName())) {
                    if (remove) {
                        numDisneyCoinsCollected--;
                    } else {
                        numDisneyCoinsCollected++;
                    }

                }
            }
        }

        for (Machine[] m : calMachines) {
            for (Machine mac : m) {
                if (mac.getMachineName().equals(machine.getMachineName())) {
                    if (remove) {
                        numCalCoinsCollected--;
                    } else {
                        numCalCoinsCollected++;
                    }
                }
            }
        }

        for (Machine[] m : downtownMachines) {
            for (Machine mac : m) {
                if (mac.getMachineName().equals(machine.getMachineName())) {
                    if (remove) {
                        numDowntownCoinsCollected--;
                    } else {
                        numDowntownCoinsCollected++;
                    }
                }
            }
        }
        for (Machine mac : retiredDisneylandMachines) {
            if (mac.getMachineName().equals(machine.getMachineName())) {
                if (remove) {
                    numArcCoinsCollected--;
                } else{
                    numArcCoinsCollected++;
                }
            }
        }
        numCoins = sharedPreference.getCoins(context).size();

    }


    private final Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                flipCard();
            } finally {
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };

    private void rotate() {
        mStatusChecker.run();
    }

    private void flipCard() {
        coinBack.setVisibility(View.VISIBLE);
        if (!mIsBackVisible) {
            mSetRightOut.setTarget(coinFront);
            mSetLeftIn.setTarget(coinBack);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = true;
        } else {
            mSetRightOut.setTarget(coinBack);
            mSetLeftIn.setTarget(coinFront);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = false;
        }
    }

    // Check if coin is in collected coins
    private boolean checkCoin(Coin checkCoin) {
        boolean check = false;
        // Get collected coins list
        List<Coin> coins = sharedPreference.getCoins(getActivity().getApplicationContext());
        if (coins != null) {
            for (Coin coin : coins) {
                // Check if coin matches coin already collected
                if (String.valueOf(coin.getTitleCoin()).equals(String.valueOf(checkCoin.getTitleCoin()))) {
                    check = true;
                    collectedDate = coin.getDateCollected();
                    break;
                }
            }
        }
        return check;
    }

    // Check if coin is in collected coins
    private boolean checkWant(Coin checkCoin) {
        boolean check = false;
        // Get collected coins list
        List<Coin> wantCoins = sharedPreference.getWantedCoins(getActivity().getApplicationContext());

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


    @Override
    public void onClick(View v) {
        //Get which row was clicked
        Intent i = new Intent(v.getContext(), bigImage.class);
        i.putExtra("frontImg", coin.getCoinFrontImg());
        i.putExtra("backImg", machine.getBackstampImg());
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        v.getContext().startActivity(i);
    }

    @Override
    public void onPause() {
        mHandler.removeCallbacks(mStatusChecker);
        super.onDestroy();
    }

    @Override
    public void onResume() {
        rotate();
        super.onResume();
        numCoins = sharedPreference.getCoins(context).size();
    }

}
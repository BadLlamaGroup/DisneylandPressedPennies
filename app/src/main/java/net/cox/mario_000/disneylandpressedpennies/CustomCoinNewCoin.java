//package net.cox.mario_000.disneylandpressedpennies;
//
//import android.animation.AnimatorInflater;
//import android.animation.AnimatorSet;
//import android.app.DatePickerDialog;
//import android.app.Fragment;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Matrix;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.DatePicker;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.analytics.HitBuilders;
//import com.google.android.gms.analytics.Tracker;
//import com.google.gson.Gson;
//
//import java.util.Calendar;
//
//import static net.cox.mario_000.disneylandpressedpennies.MainActivity.img;
//import static net.cox.mario_000.disneylandpressedpennies.MainActivity.numCoins;
//
///**
// * Created by mario_000 on 6/28/2017.
// */
//
//public class CustomCoinNewCoin extends Fragment implements View.OnClickListener{
//    Calendar myCalendar;
//    private Tracker mTracker;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        View view = inflater.inflate(R.layout.custom_coin_new_coin, container, false);
//
//        MainActivity application = (MainActivity) getActivity();
//        mTracker = application.getDefaultTracker();
//
//        //Link views
//        TextView txt1 = (TextView) view.findViewById(R.id.textView2);
//        TextView txt2 = (TextView) view.findViewById(R.id.textView3);
//        TextView txt3 = (TextView) view.findViewById(R.id.textView4);
//
//        // Get calendar
//        myCalendar = Calendar.getInstance();
//
//        // Update coin count
//        numCoins = sharedPreference.getCoins(context).size();
//
//        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, monthOfYear);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                // Update coin date info
//                sharedPreference.removeCoin(getActivity().getApplicationContext(), coin);
//                if (haveCoinBox.isChecked()) {
//                    Toast.makeText(getActivity().getApplicationContext(), "Date Updated", Toast.LENGTH_SHORT).show();
//                    coin.setDateCollected(myCalendar.getTime());
//                    sharedPreference.addCoin(getActivity().getApplicationContext(), coin);
//                    txtDate.setText(dateFormat.format(myCalendar.getTime()));
//                } else {
//                    Toast.makeText(getActivity().getApplicationContext(), "Date Selected", Toast.LENGTH_SHORT).show();
//                    haveCoinBox.setChecked(true);
//                }
//            }
//
//        };
//
//
//        return view;
//    }
//    @Override
//    public void onClick(View v) {
//        //Get which row was clicked
//        Intent i = new Intent(v.getContext(), bigImage.class);
//        i.putExtra("frontImg", coin.getCoinFrontImg());
//        i.putExtra("backImg", machine.getBackstampImg());
//        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        v.getContext().startActivity(i);
//    }
//}

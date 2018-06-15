//package net.cox.mario_000.disneylandpressedpennies;
//
//import android.app.Fragment;
//import android.app.FragmentManager;
//import android.app.FragmentTransaction;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ListView;
//
//import com.google.android.gms.analytics.HitBuilders;
//import com.google.android.gms.analytics.Tracker;
//
///**
// * Created by mario_000 on 6/28/2017.
// */
//
//public class CustomCoins extends Fragment implements View.OnClickListener{
//    private Tracker mTracker;
//    private ListView listCoins;
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        mTracker.setScreenName("Page - Custom Coins");
//        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View myFragmentView = inflater.inflate(R.layout.custom_coin, container, false);
//        getActivity().setTitle("Custom Coins");
//
//        MainActivity application = (MainActivity) getActivity();
//        mTracker = application.getDefaultTracker();
//
//
//        //Link views and set on click listener
//        listCoins = (ListView) myFragmentView.findViewById(R.id.customCoins);
//
//
//
//        return myFragmentView;
//    }
//    @Override
//    public void onClick(View v) {
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        Bundle bundle = new Bundle();
//        bundle.putInt("land", v.getId());
//        MachineDetail fragment = new MachineDetail();
//        fragment.setArguments(bundle);
//        fragmentTransaction.setCustomAnimations(
//                R.animator.fade_in,
//                R.animator.fade_out,
//                R.animator.fade_in,
//                R.animator.fade_out);
//        fragmentTransaction.replace(R.id.mainFrag, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//    }
//}

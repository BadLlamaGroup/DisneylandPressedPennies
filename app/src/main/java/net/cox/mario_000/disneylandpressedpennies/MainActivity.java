package net.cox.mario_000.disneylandpressedpennies;

import android.app.Application;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.kobakei.ratethisapp.RateThisApp;

import java.io.File;
import java.util.Arrays;
import java.util.List;


/**
 * Created by mario_000 on 6/25/2016.
 * Description: An app to keep track of souvenir pressed coins at Disneyland Resort
 */

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Data {
    public static FragmentManager fragmentManager;


    public static DisplayImage img;
    private int menuItemId;
    public static String PACKAGE_NAME;
    public static int numDisneyCoinsTotal;
    public static int numCalCoinsTotal;
    public static int numDowntownCoinsTotal;
    public static int numCurrentCoinsTotal;
    public static int numCoinsTotal;
    public static int numArcCoinsTotal;

    public static int numDisneyCoinsCollected;
    public static int numCalCoinsCollected;
    public static int numDowntownCoinsCollected;
    public static int numCurrentCoinsCollected;
    public static int numArcCoinsCollected;
    private static final String USER_ID = "User_ID";
    public static final String AUTO_ENABLED = "Auto_Enabled";
    public static final String PREFS_NAME = "Coin_App";
    private String uniqueID;
    public static boolean autoEnabled;
    public static int numCoins;
    NavigationView navigationView;
    SharedPreference sharedPreference;
    public static Context appContext;
    private static List<Coin> savedCoins;
    private static final String LAST_VERSION_CODE_KEY = "last_version_code";
    public static final String DISNEYLAND = "Disneyland";
    public static final String CALIFORNIA_ADVENTURE = "California Adventure";
    public static final String DOWNTOWN_DISNEY = "Downtown Disney";
    public static final String RETIRED = "Retired";

    public static File COIN_PATH;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO: 4/19/2017 Milestones users, coins collected
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        img = new DisplayImage();
        PACKAGE_NAME = getApplicationContext().getPackageName();

        // Set filepath
        String root = Environment.getExternalStorageDirectory().toString();
        COIN_PATH = new File( root + "/Pressed Coins at Disneyland/Coins" );


        sharedPreference = new SharedPreference();
        appContext = getApplication().getApplicationContext();
        mTracker = getDefaultTracker();

        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("UX")
                .setAction("User Sign In")
                .build());

        final String PREFS_NAME = "MyPrefsFile";

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        //SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        savedCoins = sharedPreference.getCoins(getApplicationContext());
        SharedPreferences.Editor editor = settings.edit();
        //autoEnabled = settings.getBoolean(AUTO_ENABLED, false);

        if (settings.getBoolean("my_first_time", true)) {
            //the app is being launched for first time, do something
            Toast.makeText(getApplicationContext(), "Welcome to Disneyland Pressed Coins", Toast.LENGTH_SHORT).show();

            fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            tutorial fragment = new tutorial();
            fragmentTransaction.replace(R.id.mainFrag, fragment, "main");
            fragmentTransaction.commit();


            // record the fact that the app has been started at least once
            settings.edit().putBoolean("my_first_time", false).apply();
        } else {
            fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            MainPage fragment = new MainPage();
            fragmentTransaction.replace(R.id.mainFrag, fragment, "main");
            fragmentTransaction.commit();
            new whatsNew(this).show();
//            updateImages();
//            updateCoinTotals();
//            updateTitles();
        }
        try {
            final PackageInfo packageInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.GET_ACTIVITIES);
            final long lastVersionCode = settings.getLong(LAST_VERSION_CODE_KEY, 0);
            if (packageInfo.versionCode != lastVersionCode) {
                editor.putLong(LAST_VERSION_CODE_KEY, packageInfo.versionCode);
                editor.apply();
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        updateImages();
        updateCoinTotals();
        updateTitles();


//        // TODO: 1/20/2017 Check if needed
//        // Check for image updates


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        navigationView.setCheckedItem(R.id.nav_main_page);
        menuItemId = R.id.nav_main_page;
        getFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment f = getFragmentManager().findFragmentById(R.id.mainFrag);
                if (f instanceof MainPage) {
                    menuItemId = R.id.nav_main_page;
                    navigationView.setCheckedItem(R.id.nav_main_page);
                } else if (f instanceof MapsActivity) {
                    menuItemId = R.id.nav_maps;
                    navigationView.setCheckedItem(R.id.nav_maps);
                } else if (f instanceof CoinBookDetail) {
                    menuItemId = R.id.nav_coin_book;
                    navigationView.setCheckedItem(R.id.nav_coin_book);
                } else if (f instanceof DisneyPage) {
                    navigationView.setCheckedItem(R.id.nav_disneyland);
                    menuItemId = R.id.nav_disneyland;
                } else if (f instanceof CaliforniaPage) {
                    navigationView.setCheckedItem(R.id.nav_california);
                    menuItemId = R.id.nav_california;
                } else if (f instanceof allCoins) {
                    menuItemId = R.id.nav_all_coins;
                    navigationView.setCheckedItem(R.id.nav_all_coins);
                } else if (f instanceof DowntownPage) {
                    navigationView.setCheckedItem(R.id.nav_downtown);
                    menuItemId = R.id.nav_downtown;
                } else if (f instanceof about) {
                    navigationView.setCheckedItem(R.id.nav_about);
                    menuItemId = R.id.nav_about;
                } else if (f instanceof tutorial) {
                    navigationView.setCheckedItem(R.id.nav_tutorial);
                    menuItemId = R.id.nav_tutorial;
                } else if (f instanceof search) {
                    navigationView.setCheckedItem(R.id.search_key);
                    menuItemId = R.id.nav_search;
                } else if (f instanceof wantList) {
                    //Do Nothing
                }
                else if(f instanceof Backup){
                    navigationView.setCheckedItem(R.id.nav_backup);
                    menuItemId = R.id.nav_backup;
                }
                else if(f instanceof CustomCoinList){
                    navigationView.setCheckedItem(R.id.nav_custom_coin);
                    menuItemId = R.id.nav_custom_coin;
                }
                else {
                    navigationView.setCheckedItem(menuItemId);
                }

            }
        });

        drawer.addDrawerListener(toggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toggle.syncState();

    }

    public void updateImages() {
        for (Coin coin : savedCoins) {
            //outerloop:
            for (Machine[] m : disneyMachines) {
                for (Machine mach : m) {
                    for (Coin c : mach.getCoins()) {
                        if (c.getTitleCoin().equals(coin.getTitleCoin())) {
                            if (!coin.getCoinFrontImg().equals(c.getCoinFrontImg())) {
                                coin.setCoinFrontImg(c.getCoinFrontImg());
            //                    break outerloop;
                            }
                        }
                    }
                }
            }
            //outerloop:
            for (Machine[] m : calMachines) {
                for (Machine mach : m) {
                    for (Coin c : mach.getCoins()) {
                        if (c.getTitleCoin().equals(coin.getTitleCoin())) {
                            if (!coin.getCoinFrontImg().equals(c.getCoinFrontImg())) {
                                coin.setCoinFrontImg(c.getCoinFrontImg());
              //                  break outerloop;
                            }
                        }
                    }
                }
            }
            //outerloop:
            for (Machine[] m : downtownMachines) {
                for (Machine mach : m) {
                    for (Coin c : mach.getCoins()) {
                        if (c.getTitleCoin().equals(coin.getTitleCoin())) {
                            if (!coin.getCoinFrontImg().equals(c.getCoinFrontImg())) {
                                coin.setCoinFrontImg(c.getCoinFrontImg());
            //                    break outerloop;
                            }
                        }
                    }
                }
            }

            //outerloop:
            for (Machine[] m : retiredMachines) {
                for (Machine mach : m) {
                    for (Coin c : mach.getCoins()) {
                        if (c.getTitleCoin().equals(coin.getTitleCoin())) {
                            if (!coin.getCoinFrontImg().equals(c.getCoinFrontImg())) {
                                coin.setCoinFrontImg(c.getCoinFrontImg());
              //                  break outerloop;
                            }
                        }
                    }
                }
            }



            sharedPreference.saveCoins(getApplicationContext(), savedCoins);
        }
    }


    public void updateTitles() {
        for (Coin coin : savedCoins) {
            //outerloop:
            for (Machine[] m : disneyMachines) {
                for (Machine mach : m) {
                    for (Coin c : mach.getCoins()) {
                        if (!c.getTitleCoin().equals(coin.getTitleCoin())) {
                            if (coin.getCoinFrontImg().equals(c.getCoinFrontImg())) {
                                coin.setTitleCoin(c.getTitleCoin());
                                //                    break outerloop;
                            }
                        }
                    }
                }
            }
            //outerloop:
            for (Machine[] m : calMachines) {
                for (Machine mach : m) {
                    for (Coin c : mach.getCoins()) {
                        if (!c.getTitleCoin().equals(coin.getTitleCoin())) {
                            if (coin.getCoinFrontImg().equals(c.getCoinFrontImg())) {
                                coin.setTitleCoin(c.getTitleCoin());
                                //                  break outerloop;
                            }
                        }
                    }
                }
            }
            //outerloop:
            for (Machine[] m : downtownMachines) {
                for (Machine mach : m) {
                    for (Coin c : mach.getCoins()) {
                        if (!c.getTitleCoin().equals(coin.getTitleCoin())) {
                            if (coin.getCoinFrontImg().equals(c.getCoinFrontImg())) {
                                coin.setTitleCoin(c.getTitleCoin());
                                //                    break outerloop;
                            }
                        }
                    }
                }
            }

            //outerloop:
            for (Machine[] m : retiredMachines) {
                for (Machine mach : m) {
                    for (Coin c : mach.getCoins()) {
                        if (!c.getTitleCoin().equals(coin.getTitleCoin())) {
                            if (coin.getCoinFrontImg().equals(c.getCoinFrontImg())) {
                                coin.setTitleCoin(c.getTitleCoin());
                                //                  break outerloop;
                            }
                        }
                    }
                }
            }



            sharedPreference.saveCoins(getApplicationContext(), savedCoins);
        }
    }





    public void updateCoinTotals() {
        //SharedPreference sharedPreference = new SharedPreference();
        savedCoins = sharedPreference.getCoins(appContext);
        numDisneyCoinsTotal = 0;
        numCalCoinsTotal = 0;
        numDowntownCoinsTotal = 0;
        numCurrentCoinsTotal = 0;
        numCoinsTotal = 0;
        numArcCoinsTotal = 0;
        numDisneyCoinsCollected = 0;
        numCalCoinsCollected = 0;
        numDowntownCoinsCollected = 0;
        numArcCoinsCollected = 0;
        numCoins = 0;
        for (Machine[] m : disneyMachines) {
            for (Machine mach : m) {
                numDisneyCoinsTotal += mach.getCoins().length;
                for (Coin c : mach.getCoins()) {
                    if (savedCoins.contains(c)) {
                        numDisneyCoinsCollected++;
                    }
                }
            }
        }

        for (Machine[] m : calMachines) {
            for (Machine mach : m) {
                numCalCoinsTotal += mach.getCoins().length;
                for (Coin c : mach.getCoins()) {
                    if (savedCoins.contains(c)) {
                        numCalCoinsCollected++;
                    }
                }
            }

        }

        for (Machine[] m : downtownMachines) {
            for (Machine mach : m) {
                numDowntownCoinsTotal += mach.getCoins().length;
                for (Coin c : mach.getCoins()) {
                    if (savedCoins.contains(c)) {
                        numDowntownCoinsCollected++;
                    }
                }
            }

        }

        for (Machine mach : retiredDisneylandMachines) {
            numArcCoinsTotal += mach.getCoins().length;
            for (Coin c : mach.getCoins()) {
                if (savedCoins.contains(c)) {
                    numArcCoinsCollected++;
                }
            }
        }

        for (Machine mach : retiredCaliforniaMachines) {
            numArcCoinsTotal += mach.getCoins().length;
            for (Coin c : mach.getCoins()) {
                if (savedCoins.contains(c)) {
                    numArcCoinsCollected++;
                }
            }
        }

        for (Machine mach : retiredDowntownMachines) {
            numArcCoinsTotal += mach.getCoins().length;
            for (Coin c : mach.getCoins()) {
                if (savedCoins.contains(c)) {
                    numArcCoinsCollected++;
                }
            }
        }

        numCoins = savedCoins.size();
        numCurrentCoinsCollected = savedCoins.size() - numArcCoinsCollected;
        numCurrentCoinsTotal += numDisneyCoinsTotal + numCalCoinsTotal + numDowntownCoinsTotal;
        numCoinsTotal += numCurrentCoinsTotal + numArcCoinsTotal;
    }

    void alert(String message) {
        final Dialog b = new Dialog(MainActivity.this, R.style.CustomDialog);
        b.setContentView(R.layout.exit_dialog);
        b.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // set the custom dialog components - text, image and button

        Button dialogButton = (Button) b.findViewById(R.id.btn_no);
        Button dialogButton2 = (Button) b.findViewById(R.id.btn_yes);
        // if button is clicked, close the custom dialog

        navigationView.setCheckedItem(menuItemId);

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });

        dialogButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                //super.onBackPressed();
                b.dismiss();
                finish();
            }
        });


        b.show();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Fragment f = getFragmentManager().findFragmentById(R.id.mainFrag);

        // Close drawer if open
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (f instanceof MainPage) {
            // Exit app if on Main Page
            alert("Exit?");


        } else if (f instanceof DisneyPage || f instanceof CaliforniaPage || f instanceof DowntownPage || f instanceof tutorial) {
            // Exit to Main Page
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment main = new MainPage();
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.setCustomAnimations(
                    R.animator.fade_in,
                    R.animator.fade_out,
                    R.animator.fade_in,
                    R.animator.fade_out);
            fragmentTransaction.replace(R.id.mainFrag, main);
            fragmentTransaction.commit();
        }
//        }else if(f instanceof MapsActivity){
//
//        }

        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest2.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        int id = item.getItemId();
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = null;

        if (id == R.id.nav_main_page) {
            fragment = new MainPage();
            menuItemId = R.id.nav_main_page;
        } else if (id == R.id.nav_exit) {
            navigationView.setCheckedItem(menuItemId);
            alert("Exit?");
        } else if (id == R.id.nav_maps) {
            menuItemId = R.id.nav_maps;
            fragment = new MapsActivity();
        } else if (id == R.id.nav_coin_book) {
            menuItemId = R.id.nav_coin_book;
            fragment = new CoinBookDetail();
        } else if (id == R.id.nav_disneyland) {
            menuItemId = R.id.nav_disneyland;
            fragment = new DisneyPage();
        } else if (id == R.id.nav_california) {
            menuItemId = R.id.nav_california;
            fragment = new CaliforniaPage();
        } else if (id == R.id.nav_all_coins) {
            menuItemId = R.id.nav_all_coins;
            fragment = new allCoins();
            Bundle args = new Bundle();
            args.putString("land", "Disneyland");
            fragment.setArguments(args);
        } else if (id == R.id.nav_downtown) {
            menuItemId = R.id.nav_downtown;
            fragment = new DowntownPage();
        } else if (id == R.id.nav_email) {
            DialogFragment frag = new EmailPage();
            fragmentTransaction.add(frag, "email");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_about) {
            menuItemId = R.id.nav_about;
            fragment = new about();
        } else if (id == R.id.nav_tutorial) {
            menuItemId = R.id.nav_tutorial;
            fragment = new tutorial();
        } else if (id == R.id.nav_search) {
            menuItemId = R.id.nav_search;
            fragment = new search();
        } else if (id == R.id.nav_rate) {
            RateThisApp.showRateDialog(this);
            menuItemId = R.id.nav_rate;
            fragment = new MainPage();
        } else if (id == R.id.nav_want_list) {
            fragment = new wantList();
            menuItemId = R.id.nav_want_list;
        }
        else if (id == R.id.nav_backup){
            fragment = new Backup();
            menuItemId = R.id.nav_backup;
        }
        else if (id == R.id.nav_custom_coin) {
            fragment = new CustomCoinList();
            menuItemId = R.id.nav_custom_coin;
        }

        if (fragment != null) {
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.remove(getFragmentManager().findFragmentById(R.id.mainFrag));
            fragmentTransaction.replace(R.id.mainFrag, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        return true;
    }

    @Nullable
// Find machine that coin belongs to
    public static Machine find(Coin o1) {
        for (Machine[] macs : disneyMachines) {
            for (Machine mac : macs) {
                List a = Arrays.asList(mac.getCoins());
                if (a.contains(o1)) {
                    return mac;
                }
            }
        }
        for (Machine[] macs : calMachines) {
            for (Machine mac : macs) {
                List a = Arrays.asList(mac.getCoins());
                if (a.contains(o1)) {
                    return mac;
                }
            }
        }
        for (Machine[] macs : downtownMachines) {
            for (Machine mac : macs) {
                List a = Arrays.asList(mac.getCoins());
                if (a.contains(o1)) {
                    return mac;
                }
            }
        }

        for (Machine[] macs : retiredMachines) {
            for (Machine mac : macs) {
                List a = Arrays.asList(mac.getCoins());
                if (a.contains(o1)) {
                    return mac;
                }
            }
        }
        return defaultMac[0];
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // very important:
//        if (mBroadcastReceiver != null) {
//            unregisterReceiver(mBroadcastReceiver);
//        }
//
//        // very important:
//        if (mHelper != null) {
//            mHelper.disposeWhenFinished();
//            mHelper = null;
//        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Monitor launch times and interval from installation
        RateThisApp.onStart(this);
        // If the criteria is satisfied, "Rate this app" dialog will be shown
        RateThisApp.showRateDialogIfNeeded(this);
    }

    //public class AnalyticsApplication extends Application {
    public Tracker mTracker;

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     *
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.global_tracker);
            mTracker.enableAdvertisingIdCollection(true);

        }
        return mTracker;
        //  }

    }


}
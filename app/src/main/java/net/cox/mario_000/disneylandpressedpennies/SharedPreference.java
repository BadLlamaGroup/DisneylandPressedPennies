package net.cox.mario_000.disneylandpressedpennies;

/**
 * Created by mario_000 on 7/4/2016.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPreference {

    private static final String PREFS_NAME = "Coin_App";
    private static final String OWNED_COINS = "Have_Coin";
    private static final String WANT_COINS = "Want_Coin";

    public SharedPreference() {
        super();
    }

    // Save collected coins
    public void saveCoins(Context context, List<Coin> coins) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        Editor editor = settings.edit();
        Gson gson = new Gson();
        String jsonCoins = gson.toJson(coins);
        editor.putString(OWNED_COINS, jsonCoins);
        editor.apply();
    }

    // Add coin to list
    public void addCoin(Context context, Coin coin) {
        List<Coin> coins = getCoins(context);
        coins.add(coin);
        saveCoins(context, coins);
    }

    // Remove coin from list
    public void removeCoin(Context context, Coin coin) {
        List<Coin> coins = getCoins(context);
        if (coins != null) {
            for (int i = coins.size() - 1; i >= 0; i--) {
                if (coins.get(i).getTitleCoin().equals(coin.getTitleCoin())) {
                    coins.remove(i);
                }
            }
            saveCoins(context, coins);
        }
    }

    public List<Coin> getCoins(Context context) {
        List<Coin> coins;
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(OWNED_COINS)) {
            String jsonFavorites = settings.getString(OWNED_COINS, null);
            Gson gson = new Gson();
            Coin[] collectedCoins = gson.fromJson(jsonFavorites, Coin[].class);
            coins = Arrays.asList(collectedCoins);
            coins = new ArrayList<>(coins);
        } else
            return new ArrayList<>();

        return coins;
    }


    // Save collected coins
    public void saveWantedCoins(Context context, List<Coin> coins) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        Editor editor = settings.edit();
        Gson gson = new Gson();
        String jsonCoins = gson.toJson(coins);
        editor.putString(WANT_COINS, jsonCoins);
        editor.apply();
    }

    // Add coin to list
    public void addWantedCoin(Context context, Coin coin) {
        List<Coin> wantedCoins = getWantedCoins(context);
        wantedCoins.add(coin);
        saveWantedCoins(context, wantedCoins);
    }

    // Remove coin from list
    public void removeWantedCoin(Context context, Coin coin) {
        List<Coin> wantedCoins = getWantedCoins(context);
        if (wantedCoins != null) {
            for (int i = wantedCoins.size() - 1; i >= 0; i--) {
                if (wantedCoins.get(i).getTitleCoin().equals(coin.getTitleCoin())) {
                    wantedCoins.remove(i);
                }
            }
            saveWantedCoins(context, wantedCoins);
        }
    }

    public List<Coin> getWantedCoins(Context context) {
        List<Coin> coins;
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(WANT_COINS)) {
            String jsonFavorites = settings.getString(WANT_COINS, null);
            Gson gson = new Gson();
            Coin[] collectedCoins = gson.fromJson(jsonFavorites, Coin[].class);
            coins = Arrays.asList(collectedCoins);
            coins = new ArrayList<>(coins);
        } else
            return new ArrayList<>();

        return coins;
    }

}
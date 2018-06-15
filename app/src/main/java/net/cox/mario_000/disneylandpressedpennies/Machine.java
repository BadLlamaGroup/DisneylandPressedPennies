package net.cox.mario_000.disneylandpressedpennies;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by mario_000 on 6/25/2016.
 * Description: Class to create a machine
 */
public class Machine {
    private final String land;
    private final String machineName;
    private final String typeCoin;
    private final String machineImg;
    private final String backstampImg;
    private final String coinPreviewImg;
    private final Coin[] coins;
    private final LatLng position;

    protected Machine(String land, String machineName, String typeCoin, String machineImg, String backstampImg, String coinPreview, Coin[] coins, LatLng position) {
        this.land = land;
        this.machineName = machineName;
        this.typeCoin = typeCoin;
        this.machineImg = machineImg;
        this.backstampImg = backstampImg;
        this.coinPreviewImg = coinPreview;
        this.coins = coins;
        this.position = position;
    }

    public String getLand() {
        return land;
    }

    public String getMachineName() {
        return machineName;
    }

    public String getTypeCoin() {
        return typeCoin;
    }

    public String getMachineImg() {
        return machineImg;
    }

    public String getBackstampImg() {
        return backstampImg;
    }

    public String getCoinPreviewImg() {
        return coinPreviewImg;
    }

    public Coin[] getCoins() {
        return coins;
    }

    public LatLng getPosition() {
        return position;
    }
}
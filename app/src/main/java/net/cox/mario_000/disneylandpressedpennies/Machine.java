package net.cox.mario_000.disneylandpressedpennies;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by mario_000 on 6/25/2016.
 * Description: Class to create a machine
 */
public class Machine {
    private String land;
    private String machineName;
    private String typeCoin;
    private final String machineImg;
    private String backstampImg;
    private final String coinPreviewImg;
    private Coin[] coins;
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

    public void setLand( String newLand ) {
        land = newLand;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName( String newName ) {
        machineName = newName;
    }

    public String getTypeCoin() {
        return typeCoin;
    }

    public void setTypeCoin( String newType ) {
        typeCoin = newType;
    }

    public String getMachineImg() {
        return machineImg;
    }

    public String getBackstampImg() {
        return backstampImg;
    }

    public void setBackstampImg( String newBackstamp ) {
        backstampImg = newBackstamp;
    }

    public String getCoinPreviewImg() {
        return coinPreviewImg;
    }

    public Coin[] getCoins() {
        return coins;
    }

    public void setCoin( String newCoin ) {
    }

    public LatLng getPosition() {
        return position;
    }
}
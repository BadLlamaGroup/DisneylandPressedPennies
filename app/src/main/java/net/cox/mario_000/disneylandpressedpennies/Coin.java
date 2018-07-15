package net.cox.mario_000.disneylandpressedpennies;

import java.io.Serializable;
import java.util.Date;


/**
 * Created by mario_000 on 6/25/2016.
 * Description: Class to create a Coin
 */
public class Coin implements Serializable {
    private String titleCoin;
    private String coinFrontImg;
    private String coinBackImg;
    private String coinType;
    private String coinPark;
    private String notes;
    private Date dateCollected;

    protected Coin(String titleCoin, String front, Date dateCollected) {
        this.titleCoin = titleCoin;
        this.coinFrontImg = front;
        this.dateCollected = dateCollected;
    }

    protected Coin(String titleCoin, String front, String back, String type, String park, String notes, Date dateCollected) {
        this.titleCoin = titleCoin;
        coinFrontImg = front;
        coinBackImg = back;
        coinType = type;
        coinPark = park;
        this.notes = notes;
        this.dateCollected = dateCollected;
    }

    public String getCoinFrontImg() {
        return coinFrontImg;
    }

    public void setCoinFrontImg(String coinFrontImg) {
        this.coinFrontImg = coinFrontImg;
    }

    public String getCoinBackImg() {
        return coinBackImg;
    }

    public void setCoinBackImg(String coinBackImg) {
        this.coinBackImg = coinBackImg;
    }

    public String getTitleCoin() {
        return titleCoin;
    }

    public void setTitleCoin(String newTitle) {
        this.titleCoin = newTitle;
    }

    public String getCoinType() {
        return coinType;
    }

    public void setCoinType(String coinType) {
        this.coinType = coinType;
    }

    public String getCoinPark() {
        return coinPark;
    }

    public void setCoinPark(String coinPark) {
        this.coinPark = coinPark;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getDateCollected() {
        return dateCollected;
    }

    public void setDateCollected(Date dateCollected) {
        this.dateCollected = dateCollected;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Coin))
            return false;
        if (obj == this)
            return true;

        Coin rhs = (Coin) obj;
        return this.titleCoin.equals(rhs.titleCoin);
    }

}
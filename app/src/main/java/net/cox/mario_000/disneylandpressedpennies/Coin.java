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
    private Date dateCollected;

    protected Coin(String titleCoin, String front, Date dateCollected) {
        this.titleCoin = titleCoin;
        this.coinFrontImg = front;
        this.dateCollected = dateCollected;
    }

    public String getCoinFrontImg() {
        return coinFrontImg;
    }

    public void setCoinFrontImg(String coinFrontImg) {
        this.coinFrontImg = coinFrontImg;
    }

    public String getTitleCoin() {
        return titleCoin;
    }

    public void setTitleCoin(String newTitle) {
        this.titleCoin = newTitle;
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
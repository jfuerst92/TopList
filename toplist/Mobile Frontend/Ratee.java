package com.tl.joe.toplist;

/**
 * Created by Joe on 9/12/2016.
 */

import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.service.dreams.DreamService;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import java.lang.reflect.Field;

public class Ratee {
    private double rateRatio;
    private double numPosRates;
    private double numNegRates;
    private String fName = "";
    private String picUrl = "";

    public Ratee(int numPosRates, int numNugRates, String picUrl, String fName) {
        this.numPosRates = numPosRates;
        //this.rateRatio = rateRatio;
        this.numNegRates = numNugRates;
        this.picUrl = picUrl;
        this.fName = fName;
        updateRateRatio();
    }

    public String getRateRatio() {
        return "" + rateRatio;
    }

    public void updateRateRatio() {
        this.rateRatio = numPosRates / numNegRates;
    }

    public double getNumPosRates() {
        return numPosRates;
    }

    public void addPosRate() {
        this.numPosRates++;
        updateRateRatio();
    }

    public double getNumNegRates() {
        return numNegRates;
    }

    public void addNegRate() {
        this.numNegRates++;
        updateRateRatio();
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getImageName() {
        return picUrl;
    }

    public int getImageResourceId(Activity a) {

        int drawableId = 0;
        try {
            Class res = R.drawable.class;
            Field field = res.getField(getImageName());
            drawableId= field.getInt(null);
        }
        catch (Exception e) {
            Log.e("MyTag", "Failure to get drawable id.", e);
        }
        return drawableId;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}

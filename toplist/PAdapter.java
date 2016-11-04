package com.tl.joe.toplist;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class PAdapter {
    private Activity activity;
    private TextView name;
    private TextView description;
    private RatingBar ratingBar;
    private ImageView imageView;

    private Ratee t;

    public PAdapter(Activity aActivity, TextView aName, TextView aDescription, ImageView aImageView){
        this.activity = aActivity;
        this.name = aName;
        this.description = aDescription;
        this.imageView = aImageView;
    }

    public void set(Ratee t) {
        this.t = t;
        updateView();
    }

    public void yRate(){
        t.addPosRate();
    }

    public void nRate(){
        t.addNegRate();
    }

    public Ratee get() {
        return t;
    }

    private void updateView(){
        int resID = t.getImageResourceId(activity);
        imageView.setImageResource(resID);
        name.setText(t.getfName());
        description.setText(t.getRateRatio());
    }
}

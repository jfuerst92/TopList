package com.tl.joe.toplist;


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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    public TextView nameTextView = null;
    public TextView descriptionTextView = null;
    public ImageView portraitView = null;
    public Button yesButton = null;
    public Button noButton = null;

    public Ratee dogs[] = new Ratee[10];





    private int currentSelection = 0;
    private int userSize = 10 ;
    PAdapter RPerson1;

    public void addDogs(){
        dogs[0] = new Ratee(1, 1,"majesticfloof", "Majestic" );
        dogs[1] = new Ratee(1, 1,"angrydoggo", "AngryPup" );
        dogs[2] = new Ratee(1, 1,"goodwithkids", "KidFriendly" );
        dogs[3] = new Ratee(1, 1,"angrypuppers", "AngryPups" );
        dogs[4] = new Ratee(1, 1,"goofwoofers", "GoofDogs" );
        dogs[5] = new Ratee(1, 1,"huskairforce", "Pretty Fly" );
        dogs[6] = new Ratee(1, 1,"incredulouswoofer", "Incredulous Pooch" );
        dogs[7] = new Ratee(1, 1,"restfulhusk", "Sleepsy" );
        dogs[8] = new Ratee(1, 1,"smooch", "gib kith" );
        dogs[9] = new Ratee(1, 1,"sniffinflakes", "Cute Sniffer" );
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addDogs();
        nameTextView = (TextView) findViewById(R.id.nameTextView);
        descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
        portraitView = (ImageView) findViewById(R.id.portraitView);
        yesButton = (Button) findViewById(R.id.yesButton);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yesRate();
                showNext();
            }
        });
        noButton = (Button) findViewById(R.id.noButton);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noRate();
                showNext();
            }
        });



        RPerson1 = new PAdapter(this,nameTextView,descriptionTextView,portraitView);
        RPerson1.set(dogs[0]);
        //catAdapter = new CatAdapter(this,nameTextView,descriptionTextView,ratingView,portraitView);
        //catAdapter.set(AdoptData.mCatList.get(0));

    }



    public void yesRate(){
        Log.i("rate", "Rated up");
        RPerson1.yRate();
    }

    public void noRate(){
        Log.i("rate", "Rated down");
        RPerson1.nRate();

    }
    public void showNext(){
        int random = currentSelection;
        while(random == currentSelection){
            //avoid same selection twice.
            random = (int )(Math.random() * userSize);
        }
        currentSelection = random;
        Ratee c = dogs[random];
        RPerson1.set(c);
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.generics, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */


}

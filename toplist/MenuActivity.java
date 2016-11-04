package com.tl.joe.toplist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_activty);

        final TextView welcome = (TextView) findViewById(R.id.etWelcome);
        final TextView percentile = (TextView) findViewById(R.id.regHereText);
        final Button bRate = (Button) findViewById(R.id.goToRate);
        final Button bLead = (Button) findViewById(R.id.goToLeaderboards);
        final Button bPic = (Button) findViewById(R.id.goToPic);
        final Button bLogout = (Button) findViewById(R.id.bLogout);
    }
}

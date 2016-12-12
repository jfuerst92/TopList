package com.tl.joe.toplist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "MyPrefsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button bReg = (Button) findViewById(R.id.regB);
        Button bLog = (Button) findViewById(R.id.logB);

        bReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegister();
            }

        });

        bLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogin();
            }

        });

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String key = settings.getString("key",null);

        if (key != null){
            goToMenu();
        }
    }

    public void goToRegister(){

        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void goToLogin(){

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void goToMenu(){

        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}

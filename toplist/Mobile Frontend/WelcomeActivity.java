package com.tl.joe.toplist;

/***************************************************************
 * WelcomeActivity
 * Author: Joseph Fuerst
 * This is the entry point of the application. It allows the user to
 * decide to register or login manually, or login with facebook.
 * onCreate also checks whether the user is logged in or not. If so,
 * it switches to the menu activity
 *****************************************************************/

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class WelcomeActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "MyPrefsFile";
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_welcome);



        Button bReg = (Button) findViewById(R.id.regB);
        Button bLog = (Button) findViewById(R.id.logB);
        LoginButton logButton = (LoginButton) findViewById(R.id.fblog);
        logButton.setReadPermissions("public_profile", "email");


        logButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                goToMenu();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
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

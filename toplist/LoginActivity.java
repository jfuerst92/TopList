package com.tl.joe.toplist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etEmail = (EditText) findViewById(R.id.etlEmail);
        final EditText etPass = (EditText) findViewById(R.id.etLpassword);
        final Button bLogin = (Button) findViewById(R.id.lgBtn);
        final TextView regLink = (TextView) findViewById(R.id.regHereText);

        regLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class); //get page
                LoginActivity.this.startActivity(registerIntent); //open the page
            }
        });


    }
}

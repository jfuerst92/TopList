package com.tl.joe.toplist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "httprequest";
    public static final String PREFS_NAME = "MyPrefsFile";
    String curEmail;

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

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String emailAddr = etEmail.getText().toString();
                String password = etPass.getText().toString();
                curEmail = emailAddr;
                //new RetrieveFeedTask().execute();

                RequestParams params = new RequestParams();
                Log.i(TAG, "email is: " + emailAddr);
                Log.i(TAG, "pass is: " + password);
                params.put("email", emailAddr);
                params.put("password", password);
                //params.put("uprates", 0);
                //params.put("dnrates", 0);


                APIClient.post("usersearch", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        Log.i(TAG, response.toString());
                        Log.i(TAG, "Success");
                        String key = "empty";
                        try {
                            key = response.getString("keys");
                            Log.i(TAG, key);
                        } catch (JSONException e) {

                        }
                        key = key.substring(1, key.length()-1);
                        Log.i(TAG, "The actual key is: " + key);
                        if (key.length() < 15){
                            Toast.makeText(getApplicationContext(), "Account Not Found", Toast.LENGTH_LONG).show();
                        }
                        else{
                            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("key", key);
                            editor.commit();
                            Toast.makeText(getApplicationContext(), "Login Successful!", Toast.LENGTH_LONG).show();
                            changeActivity();
                        }
                        //Toast.makeText(getApplicationContext(), "Logged In", Toast.LENGTH_LONG).show();
                        /*
                        String resp = "None";
                        if (resp.equals(response.toString())){
                            Toast.makeText(getApplicationContext(), "Account not found", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Logged In", Toast.LENGTH_LONG).show();
                            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("key", response.toString());
                            editor.commit();
                            changeActivity();
                        }
                        */

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        // Pull out the first event on the public timeline
                        //JSONObject firstEvent = response.get(0);
                        //String tweetText = firstEvent.getString("text");

                        // Do something with the response
                        //System.out.println(tweetText);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Log.d("Failed: ", "" + statusCode);
                        Log.d("Error : ", "" + throwable);
                        Log.d("Response : ", "" + responseString);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.d("Failed: ", "" + statusCode);
                        Log.d("Error : ", "" + throwable);
                        Log.d("Response : ", "" + errorResponse);
                    }
                });
            }


        });


    }

    public void changeActivity(){

        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }


}

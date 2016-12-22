package com.tl.joe.toplist;


import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


import android.widget.ProgressBar;
import java.io.BufferedOutputStream;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import android.os.AsyncTask;
import android.content.Intent;
import android.preference.PreferenceActivity;
import android.widget.Toast;

import org.json.*;



public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    public static final String PREFS_NAME = "MyPrefsFile";
    public RequestParams signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //final EditText etAge = (EditText) findViewById(R.id.etAge);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPass = (EditText) findViewById(R.id.etPass);
        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etGender = (EditText) findViewById(R.id.etGender);
        final EditText etCity = (EditText) findViewById(R.id.etCity);
        final EditText etState = (EditText) findViewById(R.id.etState);
        /*
        String fname = etName.getText().toString();
        String age = etAge.getText().toString();
        String emailAddr = etEmail.getText().toString();
        String gender = etGender.getText().toString();
        String city = etCity.getText().toString();
        String state = etState.getText().toString();
        */
        Button bRegister = (Button) findViewById(R.id.regBtn);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fname = etName.getText().toString();
                Log.i(TAG, fname);
                //String sage = etAge.getText().toString();
                //int age = Integer.parseInt(sage);
                String emailAddr = etEmail.getText().toString();
                String password = etPass.getText().toString();
                String gender = etGender.getText().toString();
                String city = etCity.getText().toString();
                String state = etState.getText().toString();
                //new RetrieveFeedTask().execute();

                RequestParams params = new RequestParams();
                params.put("email", emailAddr);

                RequestParams params2 = new RequestParams();
                params2.put("fName", fname);
                //params.put("age", age);
                params2.put("email", emailAddr);
                params2.put("password", password);
                params2.put("gender", gender);
                params2.put("city", city);
                params2.put("state", state);
                //params.put("uprates", 0);
                //params.put("dnrates", 0);
                signup = params2;

                APIClient.post("usersearch", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Log.i(TAG, response.toString());
                        Log.i(TAG, "Success");
                        String key = "empty";
                        try {
                            key = response.getString("keys");
                            Log.i(TAG, response.toString());
                        } catch (JSONException e) {

                        }

                        if (key.length() < 15){
                            register();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Email already Exists", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        Log.i(TAG, "SuccessArray");
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

    public void register(){
        APIClient.post("user", signup, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i(TAG, response.toString());
                String key = "";
                try {
                    key = response.getString("key");
                    Log.i(TAG, "NOW the key is" + key);
                } catch (JSONException e) {

                }
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                Log.i(TAG, "The user key is:" + key);
                editor.putString("key", key);
                editor.commit();
                Toast.makeText(getApplicationContext(), "Registration Successful!", Toast.LENGTH_LONG).show();
                changeActivity();
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
}


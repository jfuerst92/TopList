package com.tl.joe.toplist;


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
import org.json.*;



public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "httprequest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etAge = (EditText) findViewById(R.id.etAge);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
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
                String sage = etAge.getText().toString();
                int age = Integer.parseInt(sage);
                String emailAddr = etEmail.getText().toString();
                String gender = etGender.getText().toString();
                String city = etCity.getText().toString();
                String state = etState.getText().toString();
                //new RetrieveFeedTask().execute();

                RequestParams params = new RequestParams();
                params.put("fName", fname);
                //params.put("age", age);
                params.put("email", emailAddr);
                params.put("gender", gender);
                params.put("city", city);
                params.put("state", state);
                //params.put("uprates", 0);
                //params.put("dnrates", 0);


                APIClient.post("user", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Log.i(TAG, response.toString());
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
                /*
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                params.put("fname", fname);
                params.put("age", age);
                params.put("email", emailAddr);
                params.put("gender", gender);
                params.put("city", city);
                params.put("state", state);
                client.post("https://toplist-148122.appspot.com/user", params, new JsonHttpResponseHandler(){
                    @Override
                    public void onStart(){
                        Log.i(TAG, "About to send request");
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response){

                        Log.i(TAG, response.toString());
                    }



                });
                */
            }
        });
    }
}
    /*
    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

        private static final String TAG = "RetrieveFeedTask";

        BufferedReader reader=null;
        String text = "";

        final EditText etAge = (EditText) findViewById(R.id.etAge);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etGender = (EditText) findViewById(R.id.etGender);
        final EditText etCity = (EditText) findViewById(R.id.etCity);
        final EditText etState = (EditText) findViewById(R.id.etState);
        //final Button bRegister = (Button) findViewById(R.id.regBtn);

        String fname = etName.getText().toString();
        String age = etAge.getText().toString();
        String emailAddr = etEmail.getText().toString();
        String gender = etGender.getText().toString();
        String city = etCity.getText().toString();
        String state = etState.getText().toString();
        private Exception exception;

        String data;

        protected void onPreExecute() {
            //progressBar.setVisibility(View.VISIBLE);
            //responseView.setText("");
            try {
                data = URLEncoder.encode("name", "UTF-8")
                        + "=" + URLEncoder.encode(fname, "UTF-8");

                data += "&" + URLEncoder.encode("email", "UTF-8") + "="
                        + URLEncoder.encode(emailAddr, "UTF-8");

                data += "&" + URLEncoder.encode("gender", "UTF-8")
                        + "=" + URLEncoder.encode(gender, "UTF-8");

                data += "&" + URLEncoder.encode("age", "UTF-8")
                        + "=" + URLEncoder.encode(age, "UTF-8");

                data += "&" + URLEncoder.encode("city", "UTF-8")
                        + "=" + URLEncoder.encode(city, "UTF-8");

                data += "&" + URLEncoder.encode("state", "UTF-8")
                        + "=" + URLEncoder.encode(state, "UTF-8");

                Log.i(TAG, data);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        protected String doInBackground(Void... urls) {
            //String email = emailText.getText().toString();
            // Do some validation here

            try {
                URL url = new URL("https://toplist-148122.appspot.com/user");
                //HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                try {

                    //urlConnection.setRequestMethod("POST");

                    urlConnection.setRequestProperty("fName", fname);
                    urlConnection.setRequestProperty("age", age);
                    urlConnection.setRequestProperty("email", emailAddr);
                    urlConnection.setRequestProperty("gender", gender);
                    urlConnection.setRequestProperty("city", city);
                    urlConnection.setRequestProperty("state", state);

                    conn.setDoOutput(true);
                    //urlConnection.setChunkedStreamingMode(0);
                    OutputStream os = conn.getOutputStream();
                    Log.i(TAG, "got output stream");

                    OutputStreamWriter wr = new OutputStreamWriter(os);
                    wr.write( data );
                    Log.i(TAG, "sent to server");
                    wr.flush();
                    Log.i(TAG, "flushed");

                    InputStream is = conn.getInputStream();
                    Log.i(TAG, "got input stream");

                    reader = new BufferedReader(new InputStreamReader(is));
                    Log.i(TAG, "new buffered reader sent");
                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while((line = reader.readLine()) != null)
                    {
                        Log.i(TAG, "reading response line");
                        // Append server response in string
                        sb.append(line + "\n");
                    }


                    text = sb.toString();
                    return sb.toString();
                    /*
                    OutputStream outputPost = new BufferedOutputStream(urlConnection.getOutputStream());
                    writeStream(outputPost);
                    outputPost.flush();
                    outputPost.close();
                    */
                    /*
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                    */
/*
                } finally {
                    //urlConnection.disconnect();
                    //reader.close();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if (response == null) {
                response = "THERE WAS AN ERROR";
            }

            //Intent myIntent = new Intent(this, MenuActivity);
            //startActivity(myIntent);
            //progressBar.setVisibility(View.GONE);
            Log.i("INFO", text);
            //responseView.setText(response);
            // TODO: check this.exception
// TODO: do something with the feed
        }
    }
    */

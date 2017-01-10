package com.tl.joe.toplist;

/***************************************************************
 * MenurActivity
 * Author: Joseph Fuerst
 * The main hub of the app, allows the user to navigate to any part
 * of the app.
 *
 *
 *****************************************************************/

import android.content.Intent;
import android.content.SharedPreferences;
import java.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;

import java.io.File;
import java.io.IOException;

import java.util.Date;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static android.R.attr.bitmap;

public class MenuActivity extends AppCompatActivity {
    File img;
    String imgFName;
    private static final String TAG = "Menu";
    public static final String PREFS_NAME = "MyPrefsFile";

    String usrKey;
    String imgKey;
    String fName;
    String upRates;
    String dnRates;

    public TextView etWelcome;
    public TextView etLikes;
    public TextView dislikes;

    AmazonS3 s3;
    TransferUtility transferUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_activty);

        etWelcome = (TextView) findViewById(R.id.etWelcome);
        etLikes = (TextView) findViewById(R.id.etLikes);
        dislikes = (TextView) findViewById(R.id.etDis);
        final Button bRate = (Button) findViewById(R.id.goToRate);
        final Button bDel = (Button) findViewById(R.id.etDel);

        final Button bPic = (Button) findViewById(R.id.goToPic);
        final Button bLogout = (Button) findViewById(R.id.bLogout);
        final Button viewPic = (Button) findViewById(R.id.viewImg);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                "us-west-2:95a08035-c549-4a19-8018-31571e045f67", // Identity Pool ID
                Regions.US_WEST_2 // Region
        );

        s3 = new AmazonS3Client(credentialsProvider);
        transferUtility = new TransferUtility(s3, getApplicationContext());

        String key = settings.getString("key", null);
        String url = "user/" + key;
        Log.i(TAG, "url is:" + url);
        APIClient.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.i(TAG, "The response is:" + response.toString());
                Log.i(TAG, "Success for get");
                String key = "empty";
                String pic = "";

                try {
                    fName = response.getString("fName");
                    upRates = response.getString("upRates");
                    dnRates = response.getString("dnRates");
                    pic = response.getString("image");
                    Log.i(TAG, key);
                } catch (JSONException e) {

                }
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("curImg", pic);
                editor.commit();
                update();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

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

        bRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRate();
            }

        });

        bDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAccount();
            }

        });

        bPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPicUpload();
            }

        });

        viewPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToViewPic();
            }

        });

        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }

        });


    }

    public void update(){
        String welcome = "Welcome, " + fName;
        String likes = "The amount of people who think you're hot stuff: " + upRates;
        String dis = "The amount of people who don't: " + dnRates;

        etWelcome.setText(welcome);
        etLikes.setText(likes);
        dislikes.setText(dis);




    }
    public void goToRate(){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToViewPic(){

        Intent intent = new Intent(this, ImgViewActivity.class);
        startActivity(intent);
    }
    public void goToPicUpload(){

       dispatchTakePictureIntent();

    }

    public void logout(){

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("key", null);
        editor.commit();
        Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);


    }

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;


    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

                Log.e("ERROR", ex.getMessage(), ex);

            }

            if (photoFile != null) {
                img = photoFile;
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            upload();
        }
    }


    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        Log.i(TAG, "Creating image file");
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        imgFName = imageFileName;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        //img = image;
        Log.i(TAG, "Image created and returned");
        return image;
    }

    public void upload() {
        Log.i(TAG, "Begin upload");
        Date curDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy_hh:mm:ss");
        String DateToStr = format.format(curDate);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String key = settings.getString("key", null);
        Log.i(TAG, "Key is:");
        Log.i(TAG, key);
        usrKey = key;

        String imgName = key + DateToStr;
        Log.i(TAG, "image key is:");
        Log.i(TAG, imgName);
        imgKey = imgName;
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("curImg", imgName);
        editor.commit();
        if (img == null){
            Log.i("ERROR", "The file is empty");
        }
        else {

            TransferObserver observer = transferUtility.upload(
                    "496demobucket",     // The bucket to upload to
                    //"toplistbucket",
                    imgName,    // The key for the uploaded object
                    img,        // The file where the data to upload exists
                    CannedAccessControlList.PublicReadWrite
            );
            observer.setTransferListener(new TransferListener() {
                public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                    // update progress bar
                    //progressBar.setProgress(bytesCurrent);
                    Log.i(TAG, "progress changed");
                }

                public void onStateChanged(int id, TransferState state) {
                    Log.i(TAG, "state changed");
                    attachImgKey();

                }

                public void onError(int id, Exception ex) {
                    Log.e("ERROR", ex.getMessage(), ex);
                }
            });

        }

    }

    public void attachImgKey(){
        RequestParams params = new RequestParams();

        params.put("image", imgKey);
        String url = "userImg/" + usrKey;
        APIClient.put(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i(TAG, response.toString());
                //Toast.makeText(getApplicationContext(), "Logged In", Toast.LENGTH_LONG).show();
                String resp = "None";
                if (resp.equals(response.toString())){
                    Toast.makeText(getApplicationContext(), "Account not found", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Image Uploaded", Toast.LENGTH_LONG).show();
                    //SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                    //SharedPreferences.Editor editor = settings.edit();
                    //editor.putString("key", response.toString());
                    //editor.commit();

                }


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

    public void deleteAccount(){
        //RequestParams params = new RequestParams();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String key = settings.getString("key", null);
        //params.put("key", key);
        String url = "userDel/" + key;
        APIClient.delete(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i(TAG, response.toString());


                    Toast.makeText(getApplicationContext(), "Account deleted", Toast.LENGTH_LONG).show();
                    logout();


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

                Log.d("Response : ", "" + responseString);
                Toast.makeText(getApplicationContext(), "Account deleted", Toast.LENGTH_LONG).show();
                logout();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                Log.d("Response : ", "" + errorResponse);
                Toast.makeText(getApplicationContext(), "Account deleted", Toast.LENGTH_LONG).show();
                logout();
            }
        });
    }
}

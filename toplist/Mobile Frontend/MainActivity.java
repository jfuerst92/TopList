package com.tl.joe.toplist;


import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
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
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends Activity {

    public TextView nameTextView = null;
    public TextView descriptionTextView = null;
    public ImageView portraitView = null;
    public Button yesButton = null;
    public Button noButton = null;

    public String key;
    public String fName;
    public String image;
    public int upRates;
    public int dnRates;
    Bitmap bmp;


    public Ratee dogs[] = new Ratee[10];

    String imgFName;
    File iFile = null;

    private static final String TAG = "image view";
    public static final String PREFS_NAME = "MyPrefsFile";
    AmazonS3 s3;
    TransferUtility transferUtility;

    private int currentSelection = 0;
    private int userSize = 10 ;
    PAdapter RPerson1;
    /*
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
    */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //addDogs();
        nameTextView = (TextView) findViewById(R.id.nameTextView);
        descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
        portraitView = (ImageView) findViewById(R.id.portraitView);
        yesButton = (Button) findViewById(R.id.yesButton);

        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                "us-west-2:95a08035-c549-4a19-8018-31571e045f67", // Identity Pool ID
                Regions.US_WEST_2 // Region
        );

        s3 = new AmazonS3Client(credentialsProvider);
        transferUtility = new TransferUtility(s3, getApplicationContext());

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        //String img = prefs.getString("curImg", null);

        getRandomUser();
       // if (image != null) {
        //    getImage();
       // }
        //update();

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yesRate();
                getRandomUser();
                //if (image != null) {
                //    getImage();
               // }
               // update();
            }
        });
        noButton = (Button) findViewById(R.id.noButton);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noRate();
                getRandomUser();
                //if (image != null) {
                //    getImage();
               // }
               // update();

            }
        });



        //RPerson1 = new PAdapter(this,nameTextView,descriptionTextView,portraitView);
        //RPerson1.set(dogs[0]);
        //catAdapter = new CatAdapter(this,nameTextView,descriptionTextView,ratingView,portraitView);
        //catAdapter.set(AdoptData.mCatList.get(0));

    }

    public void getImage(){
        try {
            iFile = createImageFile();
        } catch (IOException ex) {
            // Error occurred while creating the File
            Log.e("ERROR", ex.getMessage(), ex);
            Log.i("ERROR", "163");
        }
        Toast.makeText(getApplicationContext(), "Loading Image", Toast.LENGTH_LONG).show();
        TransferObserver observer = transferUtility.download(
                "496demobucket",     /* The bucket to download from */
                image,    /* The key for the object to download */
                iFile        /* The file to download the object to */
        );
        observer.setTransferListener(new TransferListener() {
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                // update progress bar
                //progressBar.setProgress(bytesCurrent);
                Log.i(TAG, "progress changed");

            }

            public void onStateChanged(int id, TransferState state) {
                Bitmap myBitmap = BitmapFactory.decodeFile(iFile.getAbsolutePath());
                //Bitmap fin = rotateBitmap(myBitmap, 90);
                //portraitView.setImageBitmap(myBitmap);
                bmp = myBitmap;
                update();
            }

            public void onError(int id, Exception ex) {
                Log.e("ERROR", ex.getMessage(), ex);
                Log.i("ERROR", "189");
                Log.i("ERROR", "image is:" + image);
                Log.i("ERROR", "iFile is:" + iFile);
                update();
            }
        });
    }

    public void yesRate(){
        Log.i("rate", "Rated up");
        upRates++;
        RequestParams params = new RequestParams();
        String uprt =Integer.toString(upRates);
        params.put("upRates", uprt);
        String url = "uprate/" + key;
        APIClient.put(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i(TAG, response.toString());
                Log.i(TAG, "success uprate");

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
    }

    public void noRate(){
        Log.i("rate", "Rated down");
        dnRates++;
        RequestParams params = new RequestParams();
        String dnrt =Integer.toString(dnRates);
        params.put("dnRates", dnrt);
        String url = "dnrate/" + key;
        APIClient.put(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i(TAG, response.toString());
                Log.i(TAG, "success dnrate");
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

    public void getRandomUser(){
        APIClient.get("user", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i(TAG, response.toString());

                try {
                    key = response.getString("key");
                    upRates = response.getInt("upRates");
                    dnRates = response.getInt("dnRates");
                    fName = response.getString("fName");
                    image = response.getString("image");



                    Log.i(TAG, key);
                } catch (JSONException e) {

                }
                Log.i(TAG, "The stuff:");
                Log.i(TAG, key);
                Log.i(TAG, Integer.toString(upRates));
                Log.i(TAG, Integer.toString(dnRates));
                Log.i(TAG, fName);
                Log.i(TAG, image);

                if (image != null) {
                    getImage();
                }
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

    public void update(){
        nameTextView.setText(fName);
        String desc = "Likes: " + upRates + "Dislikes: " + dnRates;
        descriptionTextView.setText(desc);
        if (image != null) {
            portraitView.setImageBitmap(bmp);
        }

    }

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
        //mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        //img = image;
        Log.i(TAG, "Image created and returned");
        return image;
    }
}

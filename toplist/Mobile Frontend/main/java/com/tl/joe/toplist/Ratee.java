package com.tl.joe.toplist;

/**
 * Created by Joe on 9/12/2016.
 */

import android.content.Context;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

import cz.msebera.android.httpclient.Header;

import static android.content.Context.MODE_PRIVATE;

public class Ratee {
    private double rateRatio;
    private double upRates;
    private double dnRates;
    private String fName = "";
    private String imgFName = "";
    private String imgKey = "";
    private String key;
    private Bitmap bmp;
    //private AmazonS3 S3;
    private TransferUtility transferUtility;
    private File iFile = null;
    Context context;

    private static final String TAG = "image view";
    public static final String PREFS_NAME = "MyPrefsFile";
    //AmazonS3 s3;


    public Ratee(Context context) {
        //this.upRates = upRates;
        //this.rateRatio = rateRatio;
        //this.dnRates = numNugRates;
        //this.imgKey = imgKey;
        //this.fName = fName;
        //updateRateRatio();
        this.context = context;
        this.transferUtility = initS3Client();

        getRandomUser();



        //SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    }
    public TransferUtility initS3Client(){
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                context,
                "us-west-2:95a08035-c549-4a19-8018-31571e045f67", // Identity Pool ID
                Regions.US_WEST_2 // Region
        );

        AmazonS3 s3 = new AmazonS3Client(credentialsProvider);
        transferUtility = new TransferUtility(s3, context);
        return transferUtility;
    }

    public void getRandomUser() {
        APIClient.get("user", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i(TAG, response.toString());

                try {
                    key = response.getString("key");
                    upRates = response.getInt("upRates");
                    dnRates = response.getInt("dnRates");
                    fName = response.getString("fName");
                    imgKey = response.getString("image");



                    Log.i(TAG, key);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                if (imgKey != null) {
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

    public void getImage() {
        try {
            iFile = createImageFile();
        } catch (IOException ex) {
            // Error occurred while creating the File
            Log.e("ERROR", ex.getMessage(), ex);
            Log.i("ERROR", "149");
        }
        Toast.makeText(context, "Loading Image", Toast.LENGTH_LONG).show();
        TransferObserver observer = transferUtility.download(
                "496demobucket",     /* The bucket to download from */
                imgKey,    /* The key for the object to download */
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
                Log.i(TAG, "Bitmap dl finished");

            }

            public void onError(int id, Exception ex) {
                Log.e("ERROR", ex.getMessage(), ex);
                Log.i("ERROR", "175");
                Log.i("ERROR", "image is:" + imgKey);
                Log.i("ERROR", "iFile is:" + iFile);

            }
        });
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        Log.i(TAG, "Creating image file");
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        imgFName = imageFileName;
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
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

    public void updateRateRatio() {
        this.rateRatio = upRates / dnRates;
    }

    public double getUpRates() {
        return upRates;
    }

    public void addPosRate() {
        this.upRates++;
        updateRateRatio();
    }

    public double getDnRates() {
        return dnRates;
    }

    public String getUsrKey() {
        return key;
    }

    public void addNegRate() {
        this.dnRates++;
        updateRateRatio();
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public Bitmap getBitmap() {
        return bmp;
    }

    public String getImageName() {
        return imgKey;
    }
    /*
    public int getImageResourceId(Activity a) {

        int drawableId = 0;
        try {
            Class res = R.drawable.class;
            Field field = res.getField(getImageName());
            drawableId= field.getInt(null);
        }
        catch (Exception e) {
            Log.e("MyTag", "Failure to get drawable id.", e);
        }
        return drawableId;
    }
    */
    public void setImgKey(String imgKey) {
        this.imgKey = imgKey;
    }

    public Queue<Ratee> createQueue(Context context){
        Queue<Ratee> q = new LinkedList<Ratee>();

        for(int i = 0; i <= 4; i++){
            Log.i(TAG, "Create Ratee: " + i);
            q.add(new Ratee(context));
        }

        //cur = q.remove();
        //update(cur);
        return q;

    }
}

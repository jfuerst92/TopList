package com.tl.joe.toplist;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.widget.ImageView;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImgViewActivity extends AppCompatActivity {

    String imgFName;
    File iFile = null;
    Bitmap bmp = null;
    ImageView view;

    private static final String TAG = "image view";
    public static final String PREFS_NAME = "MyPrefsFile";
    AmazonS3 s3;
    TransferUtility transferUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_view);

        final ImageView imgView = (ImageView) findViewById(R.id.imgView);
        view = imgView;
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                "us-west-2:95a08035-c549-4a19-8018-31571e045f67", // Identity Pool ID
                Regions.US_WEST_2 // Region
        );

        s3 = new AmazonS3Client(credentialsProvider);
        transferUtility = new TransferUtility(s3, getApplicationContext());

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String img = prefs.getString("curImg", null);
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
        String url = "http://d3iduo04y2kezb.cloudfront.net/" + img;
        SendfeedbackJob job = new SendfeedbackJob();
        job.execute(url);

 //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
        /*
        try {
            iFile = createImageFile();
        } catch (IOException ex) {
            // Error occurred while creating the File
            Log.e("ERROR", ex.getMessage(), ex);

        }
        Log.i(TAG, "&&&&&&&&&&THE KEY IS " + img);
        Toast.makeText(getApplicationContext(), "Loading", Toast.LENGTH_LONG).show();
        TransferObserver observer = transferUtility.download(
                "496demobucket",     // The bucket to download from
                img,    // The key for the object to download
                iFile        // The file to download the object to
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
                imgView.setImageBitmap(myBitmap);
            }

            public void onError(int id, Exception ex) {
                Log.e("ERROR", ex.getMessage(), ex);
            }
        });
        */
    }
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    String mCurrentPhotoPath;

    public static Bitmap rotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
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
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        //img = image;
        Log.i(TAG, "Image created and returned");
        return image;
    }

    public void setImage(Bitmap b){
        view.setImageBitmap(b);
    }

    private class SendfeedbackJob extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String[] params) {

            return getBitmapFromURL(params[0]);



        }

        @Override
        protected void onPostExecute(Bitmap bit) {
            setImage(bit);


        }

        public Bitmap getBitmapFromURL(String src) {
            try {
                java.net.URL url = new java.net.URL(src);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}

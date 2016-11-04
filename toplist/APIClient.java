package com.tl.joe.toplist;

/**
 * Created by Joe on 11/3/2016.
 */
import android.util.Log;

import com.loopj.android.http.*;

public class APIClient {
    private static final String TAG = "APIclient";
    private static final String BASE_URL = "https://toplist-148122.appspot.com/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        //client.addHeader("Accept", "application/json");
        client.addHeader("Content-Type", "application/x-www-form-urlencoded");
        client.post(getAbsoluteUrl(url), params, responseHandler);

    }

    private static String getAbsoluteUrl(String relativeUrl) {
        Log.i(TAG, BASE_URL + relativeUrl);
        return BASE_URL + relativeUrl;
    }
}
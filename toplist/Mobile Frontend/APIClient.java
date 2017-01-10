package com.tl.joe.toplist;

/***************************************************************
 * APIClinet
 * Author: Joseph Fuerst
 * Handles get, post, put, and delete requests to to my GAE webapp that manages the ndb user database
 *****************************************************************/
import android.util.Log;

import com.loopj.android.http.*;

public class APIClient {
    private static final String TAG = "APIclient";
    //private static final String BASE_URL = "https://toplist-148122.appspot.com/";
    private static final String BASE_URL = "https://cs496-145421.appspot.com/";

    private static AsyncHttpClient client = new AsyncHttpClient();
    /***************************************************************
     * get
     * Sends a get request to to the specified url with the provided parameters
     * and returns the result to the indicated AsyncHttpResponseHandler
     *****************************************************************/
    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }
    /***************************************************************
     * post
     * Sends a post request to to the specified url with the provided parameters
     * and returns the result to the indicated AsyncHttpResponseHandler
     *****************************************************************/
    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        //client.addHeader("Accept", "application/json");
        client.addHeader("Content-Type", "application/x-www-form-urlencoded");
        client.post(getAbsoluteUrl(url), params, responseHandler);

    }
    /***************************************************************
     * getAbsoluteUrl
     * Creates the absolute url from the base url and its relative url
     *****************************************************************/
    private static String getAbsoluteUrl(String relativeUrl) {
        Log.i(TAG, BASE_URL + relativeUrl);
        return BASE_URL + relativeUrl;
    }
    /***************************************************************
     * put
     * Sends a put request to to the specified url with the provided parameters
     * and returns the result to the indicated AsyncHttpResponseHandler
     *****************************************************************/
    public static void put(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        //client.addHeader("Accept", "application/json");
        client.addHeader("Content-Type", "application/x-www-form-urlencoded");
        client.put(getAbsoluteUrl(url), params, responseHandler);

    }
    /***************************************************************
     * delete
     * Sends a delete request to to the specified url with the provided parameters
     * and returns the result to the indicated AsyncHttpResponseHandler
     *****************************************************************/
    public static void delete(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        //client.addHeader("Accept", "application/json");
        client.addHeader("Content-Type", "application/x-www-form-urlencoded");
        client.delete(getAbsoluteUrl(url), params, responseHandler);

    }
}
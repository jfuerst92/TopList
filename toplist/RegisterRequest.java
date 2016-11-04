package com.tl.joe.toplist;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

/**
 * Created by Joe on 9/30/2016.
 */
public class RegisterRequest extends StringRequest{
    //make a request and get a response as a string, hence stringrequest
    private static final String REGISTER_REQUEST_URL = "http://people.oregonstate.edu/~fuerstj/user/register.php";
    private Map<String, String> params;

    public RegisterRequest(String email, String password, String firstName, int age,String city, String state, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null); //null is error listener
    }
}

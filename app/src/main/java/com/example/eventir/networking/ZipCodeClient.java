package com.example.eventir.networking;


import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.eventir.BuildConfig;

public class ZipCodeClient  {
    public static final String REST_CONSUMER_KEY = BuildConfig.ZIP_KEY;
    private AsyncHttpClient client;

    public ZipCodeClient (){
        this.client = new AsyncHttpClient();
    }
    //change to also take in second argument the zipcode.
    public void getLat(JsonHttpResponseHandler handler) {
        String apiUrl = String.format("https://www.zipcodeapi.com/rest/%s/info.json/%s/degrees",REST_CONSUMER_KEY,"07732");
        RequestParams params = new RequestParams();
        client.get(apiUrl, handler);
    }
}
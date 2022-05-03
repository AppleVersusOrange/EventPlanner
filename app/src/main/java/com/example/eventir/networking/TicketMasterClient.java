package com.example.eventir.networking;


import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.eventir.BuildConfig;

public class TicketMasterClient  {
    public static final String BASE_URL = "https://app.ticketmaster.com/discovery/v2/";
    public static final String REST_CONSUMER_KEY = BuildConfig.CONSUMER_KEY;
    public static final String REST_CONSUMER_SECRET = BuildConfig.CONSUMER_SECRET;
    private AsyncHttpClient client;

    public TicketMasterClient (){
        this.client = new AsyncHttpClient();
    }
    //change to also take in second argument the zipcode.
    public void getEvents(JsonHttpResponseHandler handler, String latLong, String genre) {
        String genreID;
        String apiUrl = BASE_URL + "events.json";
        RequestParams params = new RequestParams();
        if(genre.equals("Sports")) {
            genreID = "KZFzniwnSyZfZ7v7nE";
            params.put("classificationId",genreID);
        }
        else if(genre.equals("Music")) {
            genreID = "KZFzniwnSyZfZ7v7nJ";
            params.put("classificationId",genreID);
        }
        else if(genre.equals("Arts")) {
            genreID = "KZFzniwnSyZfZ7v7na";
            params.put("classificationId",genreID);
        }
        else if(genre.equals("Film")) {
            genreID = "KZFzniwnSyZfZ7v7nn";
            params.put("classificationId",genreID);
        }
        //change this to get the user's zipcode by from DB.
        params.put("apikey", REST_CONSUMER_KEY);
        params.put("latlong", latLong);
        params.put("radius", "15");
        client.get(apiUrl, params, handler);
    }
}

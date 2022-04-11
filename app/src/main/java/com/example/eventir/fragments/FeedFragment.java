package com.example.eventir.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.eventir.R;
import com.example.eventir.adapters.EventFeedAdapter;
import com.example.eventir.models.Events;
import com.example.eventir.networking.TicketMasterClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class FeedFragment extends Fragment {
    public static final String TAG = "FeedFragment";
    private TicketMasterClient client;
    private List<Events> events;
    private RecyclerView rvEventFeed;
    private EventFeedAdapter eventFeedAdapter;



    public FeedFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvEventFeed = view.findViewById(R.id.rvEventFeed);

        client = new TicketMasterClient();
        events = new ArrayList<>();
        eventFeedAdapter = new EventFeedAdapter(getContext(), events);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvEventFeed.setLayoutManager(linearLayoutManager);
        rvEventFeed.setAdapter(eventFeedAdapter);
        retrieveEventFeed();
    }

    private void retrieveEventFeed(){
        client.getEvents(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.i(TAG, "Events were retrieved.");
                JSONObject jsonObject = json.jsonObject;
                try {
                    jsonObject = jsonObject.getJSONObject("_embedded");
                    JSONArray eventsRequest = jsonObject.getJSONArray("events");
                    eventFeedAdapter.clear();
                    eventFeedAdapter.addAll(Events.fromJsonArray(eventsRequest));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("TAG", "Json exception on results");
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG, "Error in getting events." + response, throwable);
            }
        });
    }
}
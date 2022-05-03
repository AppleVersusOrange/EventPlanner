package com.example.eventir.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.eventir.R;
import com.example.eventir.adapters.EventFeedAdapter;
import com.example.eventir.models.Events;
import com.example.eventir.networking.TicketMasterClient;
import com.example.eventir.networking.ZipCodeClient;
import com.parse.ParseUser;
import org.parceler.Parcels;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class FeedFragment extends Fragment {
    public static final String TAG = "FeedFragment";
    private TicketMasterClient client;
    private ZipCodeClient ZipClient;
    private List<Events> events;
    private Spinner spGenre;
    private RecyclerView rvEventFeed;
    private EventFeedAdapter eventFeedAdapter;
    public String latLong;
    public String zipCode;
    private String[] genreList;
    private ArrayList<String> alGenre = new ArrayList<String>();
    private String curGenre;



    public FeedFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        alGenre.add("none");
        alGenre.add("Sports");
        alGenre.add("Music");
        alGenre.add("Arts");
        alGenre.add("Film");

        rvEventFeed = view.findViewById(R.id.rvEventFeed);
        spGenre = view.findViewById(R.id.spGenre);

        genreList = getResources().getStringArray(R.array.genres);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.dropdown_item, genreList);

        spGenre.setAdapter(arrayAdapter);


        client = new TicketMasterClient();
        ZipClient = new ZipCodeClient();
        events = new ArrayList<>();
        eventFeedAdapter = new EventFeedAdapter(getContext(), events);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvEventFeed.setLayoutManager(linearLayoutManager);
        rvEventFeed.setAdapter(eventFeedAdapter);



        spGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                alGenre.get(i);
                curGenre = alGenre.get(i);
                retrieveEventFeed();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        eventFeedAdapter.setOnItemClickListener(new EventFeedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {

                Fragment fragment = new EventDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("Event",  Parcels.wrap(events.get(position)));
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.flContainer, fragment);
                transaction.addToBackStack(null);
                transaction.commit();



            }
        });
    }

    private void retrieveEventFeed(){
        zipCode = ParseUser.getCurrentUser().getString("ZIPcode");
        ZipClient.getLat(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.i(TAG, "Events were retrieved.");
                JSONObject jsonObject = json.jsonObject;
                try {
                    String lat = jsonObject.getString("lat");
                    String lon = jsonObject.getString("lng");
                    latLong = lat + "," + lon;
                }catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("TAG", "Json exception on results");
                }

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
                }, latLong, curGenre);
            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.i(TAG, "Error in getting LatLong." + response, throwable);
                latLong = "40.40179,-73.98728";

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
                }, latLong, curGenre);
            }
        }, zipCode);

    }
}

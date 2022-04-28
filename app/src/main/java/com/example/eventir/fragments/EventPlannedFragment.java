package com.example.eventir.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.eventir.R;
import com.example.eventir.adapters.EventsPlannedAdapter;
import com.example.eventir.models.EventsPlanned;
import com.example.eventir.models.ScheduleList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class EventPlannedFragment extends Fragment {
    public static final String TAG = "EventPlannedFragment";

    protected EventsPlannedAdapter adapter;

    private RecyclerView rvEventsPlanned;

    protected List<EventsPlanned> listofEventsplannedlists;

    SwipeRefreshLayout swipeContainer;

    private Button btnScheduleLists;
    private Button btnComposeList;
    private FloatingActionButton fab;

    public EventPlannedFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        return inflater.inflate(R.layout.fragment_events_planned, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        swipeContainer = view.findViewById(R.id.SwiperContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

            public void onRefresh(){
                Log.i(TAG, "REFRESHING NOW");
                queryEventLists();
            }
        });

        rvEventsPlanned = view.findViewById(R.id.rvEventsPlanned);
        listofEventsplannedlists = new ArrayList<>();
        adapter = new EventsPlannedAdapter(getContext(), listofEventsplannedlists);

        rvEventsPlanned.setAdapter(adapter);
        rvEventsPlanned.setLayoutManager(new LinearLayoutManager(getContext()));
        queryEventLists();

        btnScheduleLists = view.findViewById(R.id.SListsBtn);
        //following comment stops the app from working: work on floating button implementation
        //btnComposeList = view.findViewById(R.id.floatingActionButton1);
        btnScheduleLists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ScheduleFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.flContainer,fragment);     //main->eventplanned->schedulefragment
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        fab = view.findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new EventPlannedComposeFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.flContainer,fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        adapter.setOnItemLongClickListener(new EventsPlannedAdapter.OnItemLongClickListener(){
            @Override
            public void onItemLongClick(View itemView, int position){
                listofEventsplannedlists.remove(position);
                adapter.notifyItemRemoved(position);
            }
        });


    }

    private void queryEventLists() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        ParseQuery<EventsPlanned> query = ParseQuery.getQuery(EventsPlanned.class);
        query.whereEqualTo("ownerID", currentUser);
        query.include(EventsPlanned.KEY_OWNERID);
        query.include(EventsPlanned.KEY_EVENT_TITLE);
        query.include(EventsPlanned.KEY_EVENT_DATE);
        query.include(EventsPlanned.KEY_GENRE);
        query.include(EventsPlanned.KEY_EVENT_LOC);
        query.setLimit(20);
        query.addDescendingOrder(EventsPlanned.KEY_CREATED_DATE);
        query.findInBackground(new FindCallback<EventsPlanned>() {
            @Override
            public void done(List<EventsPlanned> events, ParseException e) {
                if (e != null){
                    Log.e(TAG, "Issue with getting events", e);
                    return;
                }

                for(EventsPlanned event : events){
                    Log.i(TAG, "EVENT: " + event.getAttraction() + "LOCATION: " + event.getLocation() + ", GENRE:  " + event.getGenre() + ", DATE: " + event.getUserDate() + ", username: " + event.getUser().getUsername());
                    //Log.i(TAG, "EVENT: " + event.getAttraction() + " username: " + event.getUser().getUsername());
                }


                adapter.clear();

                listofEventsplannedlists.addAll(events);

                adapter.notifyDataSetChanged();

                swipeContainer.setRefreshing(false);

            }
        });

    }

}

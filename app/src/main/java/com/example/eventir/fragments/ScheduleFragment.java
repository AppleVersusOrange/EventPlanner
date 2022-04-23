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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.eventir.R;
import com.example.eventir.models.ScheduleList;
import com.example.eventir.adapters.ScheduleListsAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ScheduleFragment extends Fragment {

    public static final String TAG = "ScheduleList";

    protected ScheduleListsAdapter scheduleListAdapter;

    private RecyclerView rvScheduleLists;

    protected List<ScheduleList> listofScheduleLists;

    SwipeRefreshLayout swipeContainer;

    public ScheduleFragment() {

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View eventsplanned = inflater.inflate(R.layout.fragment_schedule, container, false);

        Button btnEventPlanned = (Button) eventsplanned.findViewById(R.id.button2);
        btnEventPlanned.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                Fragment fragment = new EventPlannedFragment();

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.flContainer, fragment);  //main -> schedule -> eventplanned
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        return eventsplanned;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeContainer = view.findViewById(R.id.SwiperContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG,"REFRESHING NOW");
                queryScheduleLists();
            }
        });

        rvScheduleLists = view.findViewById(R.id.rvScheduleLists);

        listofScheduleLists = new ArrayList<>();
        scheduleListAdapter = new ScheduleListsAdapter(getContext(), listofScheduleLists);

        rvScheduleLists.setAdapter(scheduleListAdapter);
        rvScheduleLists.setLayoutManager(new LinearLayoutManager(getContext()));
        queryScheduleLists();

    }
    protected void queryScheduleLists() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        ParseQuery<ScheduleList> query = ParseQuery.getQuery(ScheduleList.class);
        query.whereEqualTo("ownerID", currentUser);
        query.include(ScheduleList.KEY_OWNERID);
        query.setLimit(20);
        query.addDescendingOrder(ScheduleList.KEY_CREATED_DATE);
        query.findInBackground(new FindCallback<ScheduleList>() {
            @Override
            public void done(List<ScheduleList> lists, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts",e);
                    return;
                }

                for(ScheduleList list : lists) {
                    Log.i(TAG, "POST: " + list.getDescription() + ", username: " + list.getUser().getUsername());
                }

                scheduleListAdapter.clear();

                listofScheduleLists.addAll(lists);

                scheduleListAdapter.notifyDataSetChanged();

                swipeContainer.setRefreshing(false);
            }
        });
    }

}


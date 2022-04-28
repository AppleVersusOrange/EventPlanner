package com.example.eventir.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eventir.R;
import com.example.eventir.activities.LoginActivity;
import com.example.eventir.models.EventsPlanned;
import com.example.eventir.models.ScheduleList;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class EventPlannedComposeFragment extends Fragment {

    public static final String TAG = "EventPlannedCompose";

    public EditText etEventTitle;
    public EditText etEventDate;
    public EditText etGenre;
    public EditText etEventLocation;
    private Button btnSubmit;
    private Button btnLogout;

    public EventPlannedComposeFragment() {
        // Required empty public constructor
    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_eventplanned_compose, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);

        super.onViewCreated(view, savedInstanceState);

        etEventTitle = view.findViewById(R.id.etEventTitle);
        etEventDate = view.findViewById(R.id.etEventDate);
        etGenre = view.findViewById(R.id.etGenre);
        etEventLocation = view.findViewById(R.id.etEventLocation);

        btnLogout = view.findViewById(R.id.btnLogOut);
        btnSubmit = view.findViewById(R.id.btnSubmit);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String event_title = etEventTitle.getText().toString();
                String event_date = etEventDate.getText().toString();
                String event_genre= etGenre.getText().toString();
                String event_location = etEventLocation.getText().toString();

                /*
                if (event_title.isEmpty()) {
                    Toast.makeText(getContext(), "Event title required", Toast.LENGTH_SHORT).show();
                    return;
                }
                */
                if (event_location.isEmpty()) {
                    Toast.makeText(getContext(), "Event location required", Toast.LENGTH_SHORT).show();
                    return;
                }
                /*
                if (event_date.isEmpty()) {
                    Toast.makeText(getContext(), "Event date required", Toast.LENGTH_SHORT).show();
                    return;
                }
                */
                 /*
                if (event_genre.isEmpty()) {
                    Toast.makeText(getContext(), "Event genre required", Toast.LENGTH_SHORT).show();
                    return;
                }
                */
                ParseUser currentUser = ParseUser.getCurrentUser();
                saveEvent(event_title, event_location, event_date, event_genre, currentUser);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
                gotoLoginActivity();
            }
        });
    }


    private void saveEvent(String event_title, String event_location, String event_date, String event_genre ,ParseUser currentUser) {                  //private initially
        EventsPlanned event = new EventsPlanned();
        event.setAttraction(event_title);
        event.setLocation(event_location);
        event.setUserDate(event_date);
        event.setGenre(event_genre);
        event.setUser(currentUser);

        event.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null){
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(getContext(), "Error while saving!", Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "event successful!");

                etEventTitle.setText("");
                etEventDate.setText("");
                etGenre.setText("");
                etEventLocation.setText("");

            }
        });
    }

    private void gotoLoginActivity() {
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
        //finish();
    }

}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventsPlannedFragment.
     */
    // TODO: Rename and change types and number of parameters
    /*
    public static EventPlannedComposeFragment newInstance(String param1, String param2) {
        EventPlannedComposeFragment fragment = new EventPlannedComposeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    */
    /*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    */

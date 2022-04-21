package com.example.eventir.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eventir.R;
import com.example.eventir.activities.LoginActivity;
import com.parse.ParseUser;

public class EventPlannedComposeFragment extends Fragment {

    public static final String TAG = "EventPlannedComposeFragment";

    public EditText etEventTitle;
    public EditText etEventDate;
    public EditText etGenre;
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

        btnLogout = view.findViewById(R.id.btnLogOut);
        btnSubmit = view.findViewById(R.id.btnSubmit);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String event_title = etEventTitle.getText().toString();
                String event_date = etEventDate.getText().toString();
                String event_genre= etGenre.getText().toString();

                if (event_title.isEmpty()) {
                    Toast.makeText(getContext(), "Event needs a title", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (event_date.isEmpty()) {
                    Toast.makeText(getContext(), "Event needs a date", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (event_genre.isEmpty()) {
                    Toast.makeText(getContext(), "Event Genre required", Toast.LENGTH_SHORT).show();
                    return;
                }
                ParseUser currentUser = ParseUser.getCurrentUser();
                saveEvent(event_title, event_date, event_genre);
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

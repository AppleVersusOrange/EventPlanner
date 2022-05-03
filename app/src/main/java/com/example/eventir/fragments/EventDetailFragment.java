package com.example.eventir.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.eventir.R;
import com.example.eventir.models.Events;
import com.example.eventir.models.EventsPlanned;
import com.example.eventir.models.ScheduleList;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class EventDetailFragment extends Fragment {
    private Events event;
    private TextView tvEventTitle;
    private TextView tvEventDate;
    private TextView tvGenre;
    private ImageView ivEventPic;
    private TextView tvVenueName;
    private TextView tvAddress;

    private Button btnAddEvent;

    public EventDetailFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        return inflater.inflate(R.layout.fragment_event_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvEventTitle = view.findViewById(R.id.tvEventTitle);
        tvVenueName = view.findViewById(R.id.tvVenueName);
        tvEventDate = view.findViewById(R.id.tvEventDate);
        tvGenre = view.findViewById(R.id.tvGenre);
        ivEventPic = view.findViewById(R.id.ivEventPic);
        tvAddress = view.findViewById(R.id.tvAddress);

        btnAddEvent = view.findViewById(R.id.btnAddEvent);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            event = Parcels.unwrap(bundle.getParcelable("Event"));
        }

        tvEventTitle.setText(event.attraction);
        tvVenueName.setText(event.venue);
        tvEventDate.setText(event.date);
        tvAddress.setText(event.address);
        tvGenre.setText("Genre: " + event.genre);

        Glide.with(getContext())
                .load(event.imageUrl)
                .transform(new CropCircleTransformation())
                .into(ivEventPic);

        tvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new MapFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("Event",  Parcels.wrap(event));
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.flContainer, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventsPlanned addevent = new EventsPlanned();
                addevent.setAttraction(event.attraction);
                addevent.setLocation(event.venue);
                addevent.setUserDate(event.date);
                addevent.setGenre(event.genre);
                addevent.setUser(ParseUser.getCurrentUser());

                addevent.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e != null){
                            Log.e("EventDetailFragment", "Error while saving", e);
                            Toast.makeText(getContext(), "Error while saving!", Toast.LENGTH_SHORT).show();
                        }
                        Log.i("EventDetailFragment", "event successful!");
                    }
                });
            }
        });
    }
}

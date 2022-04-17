package com.example.eventir.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.eventir.R;
import com.example.eventir.models.Events;

import org.parceler.Parcels;

public class EventDetailFragment extends Fragment {
    private Events event;
    private TextView tvEventTitle;
    private TextView tvEventDate;
    private TextView tvGenre;
    private ImageView ivEventPic;
    private TextView tvVenueName;

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

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            event = Parcels.unwrap(bundle.getParcelable("Event"));
        }

        tvEventTitle.setText(event.attraction);
        tvVenueName.setText(event.venue);
        tvEventDate.setText(event.date);
        tvGenre.setText("Genre: " + event.genre);

        Glide.with(getContext())
                .load(event.imageUrl)
                .into(ivEventPic);
    }
}

package com.example.eventir.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.eventir.R;
import com.example.eventir.models.Events;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private Events event;
    private TextView tvVenueNameM;
    private TextView tvAddressM;
    private ImageView ivVenuePic;
    private MapView mvMap;


    public MapFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvVenueNameM = view.findViewById(R.id.tvVenueNameM);
        tvAddressM = view.findViewById(R.id.tvAddressM);
        ivVenuePic = view.findViewById(R.id.ivVenuePic);
        mvMap = view.findViewById(R.id.mvMap);

        mvMap.onCreate(savedInstanceState);

        mvMap.getMapAsync(this);


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            event = Parcels.unwrap(bundle.getParcelable("Event"));
        }

        tvVenueNameM.setText(event.venue);
        tvAddressM.setText(event.address);

        Glide.with(getContext())
                .load(event.imageUrl)
                .transform(new CropCircleTransformation())
                .into(ivVenuePic);
    }

    @Override
    public void onResume() {
        super.onResume();
        mvMap.onResume();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        LatLng venueCoord = new LatLng(event.latitude, event.longitude);
        map.setMinZoomPreference(16);
        map.addMarker(new MarkerOptions().position(new LatLng(event.latitude, event.longitude)).title(event.venue));
        map.moveCamera(CameraUpdateFactory.newLatLng(venueCoord));
    }


    @Override
    public void onPause() {
        mvMap.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mvMap.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mvMap.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mvMap.onSaveInstanceState(outState);
    }
}
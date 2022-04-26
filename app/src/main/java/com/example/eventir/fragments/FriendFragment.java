package com.example.eventir.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.eventir.R;
import com.parse.Parse;
import com.parse.ParseUser;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.CropCircleTransformation;


public class FriendFragment extends Fragment {

    public static final String TAG = "FriendFragment";
    private ImageView ivFriendPicture;
    private TextView tvFriendUser;
    private Button btnAddFriend;
    private ParseUser friend;

    public FriendFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friend, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivFriendPicture = view.findViewById(R.id.ivFriendPicture);
        tvFriendUser = view.findViewById(R.id.tvFriendUser);
        btnAddFriend = view.findViewById(R.id.btnAddFriend);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            friend = Parcels.unwrap(bundle.getParcelable("Friend"));
        }
        Glide.with(getContext()).load(friend.getParseFile("profilePicture").getUrl()).transform(new CropCircleTransformation()).into(ivFriendPicture);
        tvFriendUser.setText(friend.getUsername());
        btnAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser user = ParseUser.getCurrentUser();
                user.addUnique("friendList", friend.getUsername());
                user.saveInBackground();
                Toast.makeText(getContext(), "Added Friend!",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
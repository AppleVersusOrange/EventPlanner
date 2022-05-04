package com.example.eventir.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.eventir.R;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.example.eventir.adapters.EventsPlannedAdapter;
import com.example.eventir.models.EventsPlanned;

import org.parceler.Parcels;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;


public class FriendFragment extends Fragment {

    public static final String TAG = "FriendFragment";
    protected EventsPlannedAdapter adapter;
    private RecyclerView rvFriendEvents;
    protected List<EventsPlanned> eventList;
    private TextView tvUpcoming;
    private ImageView ivFriendPicture;
    private TextView tvFriendUser;
    private Button btnAddFriend;
    private ParseUser friend;
    private Button btnRemoveFriend;
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
        rvFriendEvents = view.findViewById(R.id.rvFriendEvents);
        ivFriendPicture = view.findViewById(R.id.ivFriendPicture);
        tvFriendUser = view.findViewById(R.id.tvFriendUser);
        btnAddFriend = view.findViewById(R.id.btnAddFriend);
        btnRemoveFriend = view.findViewById(R.id.btnRemoveFriend);
        tvUpcoming = view.findViewById(R.id.tvUpcoming);
        eventList = new ArrayList<>();
        adapter = new EventsPlannedAdapter(getContext(), eventList);
        rvFriendEvents.setAdapter(adapter);
        rvFriendEvents.setLayoutManager(new LinearLayoutManager(getContext()));
        ParseUser user = ParseUser.getCurrentUser();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            friend = Parcels.unwrap(bundle.getParcelable("Friend"));
        }
        queryEvents();
        if(user.getList("friendList").contains(friend.getUsername())){
            btnAddFriend.setClickable(false);
            btnAddFriend.setVisibility(View.INVISIBLE);
            btnRemoveFriend.setClickable(true);
            btnRemoveFriend.setVisibility(View.VISIBLE);
            tvUpcoming.setText(friend.getUsername() + "'s Upcoming Events");
            rvFriendEvents.setVisibility(View.VISIBLE);
        }
        else{
            btnRemoveFriend.setClickable(false);
            btnRemoveFriend.setVisibility(View.INVISIBLE);
            btnAddFriend.setClickable(true);
            btnAddFriend.setVisibility(View.VISIBLE);
            tvUpcoming.setText("");
            rvFriendEvents.setVisibility(View.INVISIBLE);
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
                btnAddFriend.setClickable(false);
                btnAddFriend.setVisibility(View.INVISIBLE);
                btnRemoveFriend.setClickable(true);
                btnRemoveFriend.setVisibility(View.VISIBLE);
                tvUpcoming.setText(friend.getUsername() + "'s Upcoming Events");
                rvFriendEvents.setVisibility(View.VISIBLE);
            }
        });
        btnRemoveFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser user = ParseUser.getCurrentUser();
                List<String> friendList = user.getList("friendList");
                if(friendList.contains(friend.getUsername())){
                    friendList.remove(friend.getUsername());
                    user.put("friendList", friendList);
                    user.saveInBackground();
                    btnRemoveFriend.setClickable(false);
                    btnRemoveFriend.setVisibility(View.INVISIBLE);
                    btnAddFriend.setClickable(true);
                    btnAddFriend.setVisibility(View.VISIBLE);
                    tvUpcoming.setText("");
                    rvFriendEvents.setVisibility(View.INVISIBLE);
                }
            }
        });
        /*adapter.setOnItemClickListener(new EventsPlannedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {

            }
        });*/
    }

    private void queryEvents(){
        ParseQuery<EventsPlanned> query = ParseQuery.getQuery(EventsPlanned.class);
        query.whereEqualTo("ownerID", friend);
        query.include(EventsPlanned.KEY_OWNERID);
        query.include(EventsPlanned.KEY_EVENT_TITLE);
        query.setLimit(2);
        query.addDescendingOrder(EventsPlanned.KEY_CREATED_DATE);
        query.findInBackground(new FindCallback<EventsPlanned>() {
            @Override
            public void done(List<EventsPlanned> events, ParseException e) {
                if (e != null){
                    Log.e(TAG, "Issue with getting events", e);
                    return;
                }
                Log.i(TAG, "Query Successful");
                for(EventsPlanned event : events){
                    //Log.i(TAG, "EVENT: " + event.getAttraction() + ", GENRE:  " + event.getGenre() + ", DATE: " + event.getUserDate() + ", username: " + event.getUser().getUsername());
                    Log.i(TAG, "EVENT: " + event.getAttraction() + " username: " + event.getUser().getUsername());
                }


                adapter.clear();

                eventList.addAll(events);

                adapter.notifyDataSetChanged();

            }
        });
    }
}
package com.example.eventir.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.eventir.R;
import com.example.eventir.adapters.FriendAdapter;
import com.example.eventir.models.Friend;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class FriendListFragment extends Fragment {

    public static final String TAG = "FriendListFragment";
    private RecyclerView rvFriendList;
    private FriendAdapter adapter;
    private List<ParseUser> allFriends;

    public FriendListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friend_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvFriendList = view.findViewById(R.id.rvFriendList);
        allFriends = new ArrayList<>();
        adapter = new FriendAdapter(getContext(), allFriends);
        rvFriendList.setAdapter(adapter);
        rvFriendList.setLayoutManager(new LinearLayoutManager(getContext()));
        queryFriends();

        adapter.setOnItemClickListener(new FriendAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Fragment fragment = new FriendFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("Friend", Parcels.wrap(allFriends.get(position)));
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.flContainer, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    protected void queryFriends(){
        Log.i(TAG, "queryFriends called");
        ParseQuery<ParseUser> query = ParseQuery.getQuery(ParseUser.class);
        //query.include(Friend.KEY_USERNAME);
        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        //query.setLimit(20);
        //query.whereEqualTo("visibility", true);
        query.addDescendingOrder(Friend.KEY_CREATED);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> friends, ParseException e) {
                if(e!=null){
                    Log.e(TAG, "Issue with retrieving users", e);
                    return;
                }
                for(ParseUser friend : friends){
                    Log.i(TAG, "User: " + friend.getUsername());
                }
                allFriends.addAll(friends);
                adapter.notifyDataSetChanged();
                Log.i(TAG, "Query Successful");
            }
        });
    }
}
package com.example.eventir.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventir.R;
import com.example.eventir.models.Friend;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {

    private Context context;
    private List<ParseUser> friends;
    private OnItemClickListener listener;

    public FriendAdapter(Context context, List<ParseUser> friends){
        this.context = context;
        this.friends = friends;
    }
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    @NonNull
    @Override
    public FriendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_friend, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendAdapter.ViewHolder holder, int position) {
        ParseUser friend = friends.get(position);
        holder.bind(friend);
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public void setOnItemClickListener(FriendAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivFriendPic;
        private TextView tvFriendName;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            ivFriendPic = itemView.findViewById(R.id.ivFriendPic);
            tvFriendName = itemView.findViewById(R.id.tvFriendName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }

        public void bind(ParseUser friend){
            tvFriendName.setText(friend.getUsername());
            ParseFile image = friend.getParseFile("profilePicture");
            if(image != null){
                Glide.with(context).load(friend.getParseFile("profilePicture").getUrl()).into(ivFriendPic);
            }
        }
    }
}

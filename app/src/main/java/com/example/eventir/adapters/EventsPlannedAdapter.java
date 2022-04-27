package com.example.eventir.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventir.R;
import com.example.eventir.models.EventsPlanned;

import java.util.List;

public class EventsPlannedAdapter extends RecyclerView.Adapter<EventsPlannedAdapter.ViewHolder>{
    private Context context;
    private List<EventsPlanned>lists;

    public EventsPlannedAdapter(Context context, List<EventsPlanned> lists){
        this.context = context;
        this.lists = lists;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.event_planned, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EventsPlanned list = lists.get(position);


        holder.bind(list);
    }

    @Override
    public int getItemCount(){ return lists.size(); }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvEventTitle;
        private TextView tvLocation;
        private TextView tvEventDate;
        private TextView tvGenre;
        private TextView tvUsername;

        public ViewHolder(View itemView) {
            super(itemView);

            tvEventTitle = itemView.findViewById(R.id.tvEventTitle);

            tvLocation = itemView.findViewById(R.id.tvLocation);

            tvEventDate= itemView.findViewById(R.id.tvEventDate);

            tvGenre = itemView.findViewById(R.id.tvGenre);

            tvUsername = itemView.findViewById(R.id.tvUserName);



        }

        public void bind(EventsPlanned event){
            //Bind event data to view elements
            tvUsername.setText(event.getUser().getUsername());
            tvEventTitle.setText(event.getAttraction());
            tvLocation.setText(event.getLocation());
            tvEventDate.setText(event.getUserDate());
            tvGenre.setText(event.getGenre());
        }
    }

    public void clear() {
        lists.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<EventsPlanned> list) {
        lists.addAll(list);
        notifyDataSetChanged();
    }



}

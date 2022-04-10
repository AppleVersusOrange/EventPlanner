package com.example.eventir.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventir.R;
import com.example.eventir.models.Events;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class EventFeedAdapter extends RecyclerView.Adapter<EventFeedAdapter.ViewHolder>{
    Context context;
    List<Events> events;

    public EventFeedAdapter(Context context, List<Events> events) {
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View timelineView = LayoutInflater.from(context).inflate(R.layout.item_events, parent, false);
        return new ViewHolder(timelineView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventFeedAdapter.ViewHolder holder, int position) {
        Events event = events.get(position);
        holder.bind(event);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void clear() {
        events.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Events> eventList) {
        events.addAll(eventList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvEventTitle;
        //private TextView tvEventDate;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEventTitle = itemView.findViewById(R.id.tvEventTitle);
            //tvEventDate = itemView.findViewById(R.id.tvEventDate);
        }

        public void bind(Events events) {
            tvEventTitle.setText(events.title);
            //tvEventDate.setText(events.date);
        }
    }
}

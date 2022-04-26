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

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public EventFeedAdapter(Context context, List<Events> events) {
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View timelineView = LayoutInflater.from(context).inflate(R.layout.item_events, parent, false);
        return new ViewHolder(timelineView, listener);
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
        private TextView tvEventDate;
        private TextView tvGenre;
        private ImageView ivEventPic;


        public ViewHolder(@NonNull View itemView, final  OnItemClickListener listener) {
            super(itemView);
            tvEventTitle = itemView.findViewById(R.id.tvEventTitle);
            tvEventDate = itemView.findViewById(R.id.tvEventDate);
            tvGenre = itemView.findViewById(R.id.tvGenre);
            ivEventPic = itemView.findViewById(R.id.ivEventPic);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }

        public void bind(Events events) {
            tvEventTitle.setText(events.attraction);
            tvEventDate.setText(events.date);
            tvGenre.setText("Genre: " + events.genre);

            Glide.with(context)
                    .load(events.imageUrl)
                    .transform(new CropCircleTransformation())
                    .into(ivEventPic);
        }
    }
}

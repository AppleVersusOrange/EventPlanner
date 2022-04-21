package com.example.eventir.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventir.R;
import com.example.eventir.models.EventsPlanned;

import java.util.List;

public class EventsPlannedAdapter extends RecyclerView.Adapter<EventsPlannedAdapter.ViewHolder>{
    private Context context;
    private List<EventsPlanned> lists;

    public EventsPlannedAdapter(Context context, List<EventsPlanned> lists){
        this.context = context;
        this.lists = lists;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_events_planned, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventFeedAdapter.ViewHolder holder, int position) {
        EventsPlanned list = lists.get(position);

        holder.bind(list);
    }

    @Override
    public int getItemCount(){ return list.size(); }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv

    }



}

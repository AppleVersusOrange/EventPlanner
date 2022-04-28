package com.example.eventir.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventir.R;
import com.example.eventir.models.ScheduleList;

import java.util.List;

public class ScheduleListsAdapter extends RecyclerView.Adapter<ScheduleListsAdapter.ViewHolder> {
    private Context context;
    private List<ScheduleList> lists;

    public interface OnItemLongClickListener{
        void onItemLongClick(View itemView, int position);
    }

    private OnItemLongClickListener lclistener;

    public void setOnItemLongClickListener(OnItemLongClickListener lclistener){
        this.lclistener = lclistener;
    }

    public ScheduleListsAdapter(Context context, List<ScheduleList> lists) {
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.schedule_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ScheduleList list = lists.get(position);

        holder.bind(list);

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvUsername;
        private TextView tvDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUserName);

            tvDescription = itemView.findViewById(R.id.tvDescription);
        }

        public void bind(ScheduleList post) {
            //Bind post data to view elements
            tvUsername.setText(post.getUser().getUsername());
            tvDescription.setText(post.getDescription());

            tvDescription.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    lclistener.onItemLongClick(itemView, getAdapterPosition());
                    return true;
                }
            });
        }
    }

    // Clean all elements of the recycler
    public void clear() {
        lists.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<ScheduleList> list) {
        lists.addAll(list);
        notifyDataSetChanged();
    }
}
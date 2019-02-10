package com.example.sample_android;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.sample_android.action.Action;

import java.util.Collections;
import java.util.List;

class ActionListAdapter extends RecyclerView.Adapter<ActionItemViewHolder> {

    MainActivity mainActivity;
    List<Action> actions = Collections.emptyList();

    public ActionListAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setState(List<Action> actions) {
        this.actions = actions;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ActionItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ActionItemViewHolder(this, LayoutInflater.from(parent.getContext()).inflate(R.layout.action_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ActionItemViewHolder holder, int position) {
        holder.bind(position, actions.get(position));
    }

    @Override
    public int getItemCount() {
        return actions.size();
    }

}

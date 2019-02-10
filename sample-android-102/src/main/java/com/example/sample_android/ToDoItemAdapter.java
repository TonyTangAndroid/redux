package com.example.sample_android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sample_android.state.TodoItem;
import com.example.sample_android.state.TodoList;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

class ToDoItemAdapter extends RecyclerView.Adapter<ToDoItemViewHolder> {

    MainActivity mainActivity;
    private List<TodoItem> items = Collections.emptyList();

    public ToDoItemAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setState(final TodoList list) {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return items.size();
            }

            @Override
            public int getNewListSize() {
                return list.items().size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return items.get(oldItemPosition).id() == list.items().get(newItemPosition).id();
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return items.get(oldItemPosition).equals(list.items().get(newItemPosition));
            }
        });
        this.items = list.items();
        result.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public ToDoItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        return new ToDoItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoItemViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}

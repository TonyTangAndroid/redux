package com.example.sample_android;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import com.example.sample_android.action.Check;
import com.example.sample_android.action.Remove;
import com.example.sample_android.state.TodoItem;
import com.example.sample_android.store.MainStore;

class ToDoItemViewHolder extends RecyclerView.ViewHolder {
    private ToDoItemAdapter adapter;
    final MainStore store;
    final CheckBox checkBox;
    final ImageButton edit;
    final ImageButton delete;

    TodoItem item;

    ToDoItemViewHolder(ToDoItemAdapter adapter, final MainStore store, View itemView) {
        super(itemView);
        this.adapter = adapter;
        checkBox = itemView.findViewById(R.id.item);
        edit = itemView.findViewById(R.id.edit);
        delete = itemView.findViewById(R.id.delete);
        this.store = store;

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (item.done() != isChecked) {
                    store.dispatch(Check.create(item.id(), isChecked));
                }
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TodoItemDialogFragment.newInstance(item.id()).show(adapter.mainActivity.getSupportFragmentManager(), "dialog");
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                store.dispatch(Remove.create(item.id()));
            }
        });
    }

    void bind(TodoItem item) {
        this.item = item;
        checkBox.setText(item.text());
        checkBox.setChecked(item.done());
    }
}

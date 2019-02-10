package com.example.sample_android;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;

import com.example.sample_android.action.Check;
import com.example.sample_android.action.Remove;
import com.example.sample_android.state.TodoItem;
import com.example.sample_android.store.MainStore;

class ToDoItemViewHolder extends RecyclerView.ViewHolder {
    final MainStore store;
    final CheckBox checkBox;
    final ImageButton edit;
    final ImageButton delete;

    TodoItem item;

    ToDoItemViewHolder(final MainStore store, View itemView, FragmentManager supportFragmentManager) {
        super(itemView);
        checkBox = itemView.findViewById(R.id.item);
        edit = itemView.findViewById(R.id.edit);
        delete = itemView.findViewById(R.id.delete);
        this.store = store;

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (item.done() != isChecked) {
                store.dispatch(Check.create(item.id(), isChecked));
            }
        });

        edit.setOnClickListener(v -> edit(supportFragmentManager));

        delete.setOnClickListener(v -> edit(store));
    }

    private void edit(MainStore store) {
        store.dispatch(Remove.create(item.id()));
    }

    private void edit(FragmentManager supportFragmentManager) {
        TodoItemDialogFragment.newInstance(item.id()).show(supportFragmentManager, "dialog");
    }

    void bind(TodoItem item) {
        this.item = item;
        checkBox.setText(item.text());
        checkBox.setChecked(item.done());
    }
}

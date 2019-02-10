package com.example.sample_android;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;

import com.example.sample_android.state.TodoItem;

import androidx.recyclerview.widget.RecyclerView;

class ToDoItemViewHolder extends RecyclerView.ViewHolder {
    final CheckBox checkBox;
    final ImageButton edit;
    final ImageButton delete;

    TodoItem item;

    ToDoItemViewHolder(View itemView) {
        super(itemView);
        checkBox = itemView.findViewById(R.id.item);
        edit = itemView.findViewById(R.id.edit);
        delete = itemView.findViewById(R.id.delete);
        checkBox.setEnabled(false);
        edit.setVisibility(View.GONE);
        delete.setVisibility(View.GONE);

    }


    void bind(TodoItem item) {
        this.item = item;
        checkBox.setText(item.text());
        checkBox.setChecked(item.done());
    }
}

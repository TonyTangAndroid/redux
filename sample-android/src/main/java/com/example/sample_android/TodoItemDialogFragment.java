package com.example.sample_android;

import android.annotation.SuppressLint;
import android.app.Dialog;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.sample_android.action.Add;
import com.example.sample_android.action.Edit;
import com.example.sample_android.state.TodoItem;
import com.example.sample_android.store.MainStore;

import java.util.Objects;

public class TodoItemDialogFragment extends DialogFragment {

    MainStore store;
    int id;

    public static TodoItemDialogFragment newInstance() {
        return newInstance(-1);
    }

    public static TodoItemDialogFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt("id", id);
        TodoItemDialogFragment fragment = new TodoItemDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        store = ViewModelProviders.of(requireActivity()).get(TodoViewModel.class).getStore();
        assert getArguments() != null;
        id = getArguments().getInt("id", -1);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        @SuppressLint("InflateParams") final View view = LayoutInflater.from(getContext()).inflate(R.layout.item_dialog, null);
        final TextView textView = view.findViewById(R.id.text);
        String text = null;
        for (TodoItem todoItem : store.getState().items()) {
            if (todoItem.id() == id) {
                text = todoItem.text();
                break;
            }
        }
        textView.setText(text);

        return new AlertDialog.Builder(Objects.requireNonNull(getContext()), getTheme())
                .setTitle(id >= 0 ? "Edit" : "New")
                .setView(view)
                .setPositiveButton("Ok", (dialog, which) -> onClick(textView))
                .create();
    }

    private void onClick(TextView textView) {
        String text1 = TextUtils.isEmpty(textView.getText()) ? "Item" : textView.getText().toString();
        if (id >= 0) {
            store.dispatch(Edit.create(id, text1));
        } else {
            store.dispatch(Add.create(text1));
        }
    }
}

package com.example.sample_android;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.sample_android.action.Action;
import com.example.sample_android.state.TodoList;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import me.tatarka.redux.ReplayMiddleware;

public class EditActionDialogFragment extends DialogFragment {

    private static final String TAG = "EditActionDialogFragment";

    private static final UnsafeAllocator unsafe = UnsafeAllocator.create();

    public static EditActionDialogFragment newInstance(int index) {
        Bundle args = new Bundle();
        args.putInt("index", index);
        EditActionDialogFragment fragment = new EditActionDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    ReplayMiddleware<TodoList, Action, Action> middleware;
    int index;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        middleware = ViewModelProviders.of(requireActivity()).get(TodoViewModel.class).getStore().getReplayMiddleware();
        assert getArguments() != null;
        index = getArguments().getInt("index", -1);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Action action = middleware.actions().get(index);
        final LinearLayout layout = new LinearLayout(getContext());
        int padding = getResources().getDimensionPixelOffset(R.dimen.padding);
        layout.setPadding(padding, padding, padding, padding);
        layout.setOrientation(LinearLayout.VERTICAL);
        for (Field field : action.getClass().getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers()) && !Modifier.isTransient(field.getModifiers())) {
                layout.addView(createInput(getContext(), field, action), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
        }
        return new AlertDialog.Builder(requireContext(), getTheme())
                .setTitle("Edit " + action.getClass().getSimpleName())
                .setView(layout)
                .setPositiveButton("Ok", (dialog, which) -> onClick(action, layout))
                .setNegativeButton("Delete", (dialog, which) -> middleware.remove(index))
                .create();
    }

    private void onClick(Action action, LinearLayout layout) {
        try {
            Action newAction = unsafe.newInstance(action.getClass());
            for (int i = 0; i < layout.getChildCount(); i++) {
                TextInputLayout input = (TextInputLayout) layout.getChildAt(i);
                EditText editText = input.getEditText();
                Field field = (Field) input.getTag();
                assert editText != null;
                field.set(newAction, toType(field, editText.getText().toString()));
            }
            middleware.replace(index, newAction);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            Toast.makeText(getContext(), "Error changing action " + action.getClass().getSimpleName(), Toast.LENGTH_SHORT).show();
        }
    }

    private static TextInputLayout createInput(Context context, Field field, Object action) {
        try {
            field.setAccessible(true);
            String name = field.getName();
            Object value = field.get(action);
            String valueStr = value != null ? value.toString() : null;

            TextInputLayout layout = new TextInputLayout(context);
            layout.setHint(name);
            TextInputEditText editText = new TextInputEditText(context);
            editText.setText(valueStr);
            layout.setTag(field);
            layout.addView(editText, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return layout;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object toType(Field field, String value) {
        if (field.getType() == String.class) {
            return value;
        }
        if (field.getType() == int.class || field.getType() == Integer.class) {
            return Integer.parseInt(value);
        }
        if (field.getType() == boolean.class || field.getType() == Boolean.class) {
            return Boolean.parseBoolean(value);
        }
        throw new RuntimeException("Cannot covert string to type: " + field.getType());
    }
}

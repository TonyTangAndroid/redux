package com.example.sample_android;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.widget.TextView;

import com.example.sample_android.action.Action;
import com.example.sample_android.state.TodoList;

import me.tatarka.redux.ReplayMiddleware;

class ActionItemViewHolder extends RecyclerView.ViewHolder {

    final TextView textView;
    int index;
    private ReplayMiddleware<TodoList, Action, Action> replayMiddleware;

    ActionItemViewHolder(View itemView,
                         FragmentManager fragmentManager,
                         ReplayMiddleware<TodoList, Action, Action> replayMiddleware) {
        super(itemView);
        this.replayMiddleware = replayMiddleware;
        textView = itemView.findViewById(R.id.action);
        textView.setOnClickListener(v -> onClick());
        textView.setOnLongClickListener(v -> onItemLongClick(fragmentManager));
    }

    private boolean onItemLongClick(FragmentManager fragmentManager) {
        EditActionDialogFragment.newInstance(index).show(fragmentManager, "dialog");
        return true;
    }

    private void onClick() {
        if (this.replayMiddleware.isDisabled(index)) {
            this.replayMiddleware.enable(index);
        } else {
            this.replayMiddleware.disable(index);
        }
    }

    void bind(int index, Object action) {
        this.index = index;
        if (replayMiddleware.isDisabled(index)) {
            SpannableString str = new SpannableString(action.toString());
            str.setSpan(new StrikethroughSpan(), 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(str);
        } else {
            textView.setText(action.toString());
        }
    }
}

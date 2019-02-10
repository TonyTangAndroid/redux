package com.example.sample_android;

import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.widget.TextView;

class ActionItemViewHolder extends RecyclerView.ViewHolder {

    private ActionListAdapter actionListAdapter;
    final TextView textView;
    int index;

    ActionItemViewHolder(ActionListAdapter actionListAdapter, View itemView) {
        super(itemView);
        this.actionListAdapter = actionListAdapter;
        textView = itemView.findViewById(R.id.action);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actionListAdapter.mainActivity.replayMiddleware.isDisabled(index)) {
                    actionListAdapter.mainActivity.replayMiddleware.enable(index);
                } else {
                    actionListAdapter.mainActivity.replayMiddleware.disable(index);
                }
            }
        });
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                EditActionDialogFragment.newInstance(index).show(actionListAdapter.mainActivity.getSupportFragmentManager(), "dialog");
                return true;
            }
        });
    }

    void bind(int index, Object action) {
        this.index = index;
        if (actionListAdapter.mainActivity.replayMiddleware.isDisabled(index)) {
            SpannableString str = new SpannableString(action.toString());
            str.setSpan(new StrikethroughSpan(), 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(str);
        } else {
            textView.setText(action.toString());
        }
    }
}

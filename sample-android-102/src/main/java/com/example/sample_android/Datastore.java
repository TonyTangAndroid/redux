package com.example.sample_android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.sample_android.state.TodoItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Datastore {

    // poor-man's persistence
    private SharedPreferences prefs;

    public Datastore(Context context) {
        prefs = context.getSharedPreferences("datastore", Context.MODE_PRIVATE);
    }

    @SuppressLint("StaticFieldLeak")
    public void get(final Callback callback) {
        // Yeah you can get them pretty fast, but let's pretend it's slow.
        new AsyncTask<Void, Void, List<TodoItem>>() {
            @Override
            protected List<TodoItem> doInBackground(Void... params) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return null;
                }
                String[] data = Objects.requireNonNull(prefs.getString("data", "")).split(",");
                List<TodoItem> items = new ArrayList<>(data.length);
                for (String s : data) {
                    String[] fields = s.split(":");
                    if (fields.length == 3) {
                        int id = Integer.parseInt(fields[0]);
                        String text = fields[1];
                        boolean done = Boolean.parseBoolean(fields[2]);
                        items.add(TodoItem.create(id, text, done));
                    }
                }
                return items;
            }

            @Override
            protected void onPostExecute(List<TodoItem> todoItems) {
                callback.onList(todoItems);
            }
        }.execute();
    }

    public interface Callback {
        void onList(List<TodoItem> items);
    }
}

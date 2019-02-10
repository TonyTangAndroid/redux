package com.example.sample_android;

import android.content.Context;
import android.content.SharedPreferences;

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


    public List<TodoItem> getTodoItems() {
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

}

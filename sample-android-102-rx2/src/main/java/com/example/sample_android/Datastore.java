package com.example.sample_android;

import android.content.SharedPreferences;

import com.example.sample_android.state.TodoItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

@AppScope
public class Datastore {

    private final SharedPreferences prefs;

    @Inject
    public Datastore(SharedPreferences prefs) {
        this.prefs = prefs;
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
        return items.size() == 0 ? Collections.singletonList(hardCode()) : items;
    }

    private TodoItem hardCode() {
        return TodoItem.create(1, "123", false);
    }

}

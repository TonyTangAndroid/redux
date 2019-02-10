package com.example.sample_android.action;

import com.example.sample_android.Datastore;
import com.example.sample_android.state.TodoItem;

import java.util.List;

import me.tatarka.redux.Dispatcher;
import me.tatarka.redux.Thunk;

public class LoadActionCreator {

    private final Datastore datastore;

    public LoadActionCreator(Datastore datastore) {
        this.datastore = datastore;
    }

    public Thunk<Action, Action> load() {
        return this::execute;
    }

    private void execute(Dispatcher<Action, Action> dispatcher) {
        datastore.get(items -> dispatch(dispatcher, items));
    }

    private void dispatch(Dispatcher<Action, Action> dispatcher, List<TodoItem> items) {
        dispatcher.dispatch(Load.create(items));
    }
}

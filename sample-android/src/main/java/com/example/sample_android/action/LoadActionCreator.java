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
        return new Thunk<Action, Action>() {
            @Override
            public void run(final Dispatcher<Action, Action> dispatcher) {
                datastore.get(new Datastore.Callback() {
                    @Override
                    public void onList(List<TodoItem> items) {
                        dispatcher.dispatch(Load.create(items));
                    }
                });
            }
        };
    }
}

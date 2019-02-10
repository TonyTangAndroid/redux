package com.example.sample_android.store;

import android.content.Context;

import com.example.sample_android.Datastore;
import com.example.sample_android.action.Action;
import com.example.sample_android.middleware.PersistenceMiddleware;
import com.example.sample_android.reducer.TodoListReducers;
import com.example.sample_android.state.TodoList;

import me.tatarka.redux.Dispatcher;
import me.tatarka.redux.Reducer;
import me.tatarka.redux.SimpleStore;
import me.tatarka.redux.Thunk;
import me.tatarka.redux.ThunkDispatcher;
import me.tatarka.redux.android.LogMiddleware;

/**
 * To keep everything together, you can subclass {@link SimpleStore} and add your own {@code dispatch()} methods to it.
 */
public class MainStore extends SimpleStore<TodoList> {

    private final Dispatcher<Action, Action> dispatcher;
    private final Dispatcher<Thunk<Action, Action>, Void> thunkDispatcher;

    public MainStore(Context context) {
        super(TodoList.initial());
        Reducer<TodoList, Action> reducer = TodoListReducers.reducer();
        dispatcher = Dispatcher.forStore(this, reducer)
                .chain(new LogMiddleware<>("ACTION"),
                        new PersistenceMiddleware<>(this, new Datastore(context)));
        thunkDispatcher = new ThunkDispatcher<>(dispatcher)
                .chain(new LogMiddleware<>("THUNK_ACTION"));
    }

    public void dispatch(Action action) {
        dispatcher.dispatch(action);
    }

    public void dispatch(Thunk<Action, Action> thunk) {
        thunkDispatcher.dispatch(thunk);
    }

}

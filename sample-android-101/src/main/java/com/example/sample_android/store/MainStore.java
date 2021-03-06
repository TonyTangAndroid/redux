package com.example.sample_android.store;

import com.example.sample_android.action.Action;
import com.example.sample_android.reducer.TodoListReducers;
import com.example.sample_android.state.TodoList;

import me.tatarka.redux.Dispatcher;
import me.tatarka.redux.Reducer;
import me.tatarka.redux.SimpleStore;
import me.tatarka.redux.Thunk;
import me.tatarka.redux.ThunkDispatcher;

/**
 * To keep everything together, you can subclass {@link SimpleStore} and add your own {@code dispatch()} methods to it.
 */
public class MainStore extends SimpleStore<TodoList> {

    private final Dispatcher<Thunk<Action, Action>, Void> thunkDispatcher;

    public MainStore() {
        super(TodoList.initial());
        Reducer<TodoList, Action> reducer = TodoListReducers.reducer();
        Dispatcher<Action, Action> dispatcher = Dispatcher.forStore(this, reducer);
        thunkDispatcher = new ThunkDispatcher<>(dispatcher);
    }

    public void dispatch(Thunk<Action, Action> thunk) {
        thunkDispatcher.dispatch(thunk);
    }

}

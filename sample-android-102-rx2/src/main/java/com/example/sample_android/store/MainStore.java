package com.example.sample_android.store;

import com.example.sample_android.action.Action;
import com.example.sample_android.reducer.TodoListReducers;
import com.example.sample_android.state.TodoList;

import io.reactivex.Single;
import me.tatarka.redux.Dispatcher;
import me.tatarka.redux.Reducer;
import me.tatarka.redux.SimpleStore;
import me.tatarka.redux.rx2.SingleDispatcher;

public class MainStore extends SimpleStore<TodoList> {

    private final SingleDispatcher<Action> singleDispatcher;

    public MainStore() {
        super(TodoList.initial());
        Reducer<TodoList, Action> reducer = TodoListReducers.reducer();
        Dispatcher<Action, Action> dispatcher = Dispatcher.forStore(this, reducer);
        singleDispatcher = new SingleDispatcher<>(dispatcher);
    }

    public void dispatch(Single<Action> single) {
        singleDispatcher.dispatch(single);
    }

}

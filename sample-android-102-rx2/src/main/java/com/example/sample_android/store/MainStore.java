package com.example.sample_android.store;

import com.example.sample_android.action.Action;
import com.example.sample_android.state.TodoList;

import javax.inject.Inject;

import io.reactivex.Single;
import me.tatarka.redux.Dispatcher;
import me.tatarka.redux.Reducer;
import me.tatarka.redux.SimpleStore;
import me.tatarka.redux.rx2.SingleDispatcher;

public class MainStore extends SimpleStore<TodoList> {

    private final SingleDispatcher<Action> dispatcher;

    @Inject
    public MainStore(TodoList initialState, Reducer<TodoList, Action> reducer) {
        super(initialState);
        Dispatcher<Action, Action> dispatcher = Dispatcher.forStore(this, reducer);
        this.dispatcher = new SingleDispatcher<>(dispatcher);
    }

    public void dispatch(Single<Action> single) {
        dispatcher.dispatch(single);
    }

}

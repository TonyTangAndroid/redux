package com.example.sample_android;

import android.app.Application;

import com.example.sample_android.state.TodoList;
import com.example.sample_android.store.MainStore;

import javax.inject.Inject;

import me.tatarka.redux.android.lifecycle.StoreAndroidViewModel;

public class TodoViewModel extends StoreAndroidViewModel<TodoList, MainStore> {

    @Inject
    public TodoViewModel(Application application, MainStore store) {
        super(application, store);
    }
}

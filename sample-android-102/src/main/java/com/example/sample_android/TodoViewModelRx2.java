package com.example.sample_android;

import android.app.Application;

import com.example.sample_android.state.TodoList;
import com.example.sample_android.store.MainStoreRx2;

import me.tatarka.redux.android.lifecycle.StoreAndroidViewModel;

public class TodoViewModelRx2 extends StoreAndroidViewModel<TodoList, MainStoreRx2> {
    public TodoViewModelRx2(Application application) {
        super(application, new MainStoreRx2());
    }
}

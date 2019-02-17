package com.example.sample_android;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.sample_android.action.Action;
import com.example.sample_android.reducer.TodoListReducers;
import com.example.sample_android.state.TodoList;

import dagger.Module;
import dagger.Provides;
import me.tatarka.redux.Reducer;

@Module
public abstract class ContextModule {

    @Provides
    @AppScope
    public static Context context(Application application) {
        return application;
    }

    @Provides
    @AppScope
    public static SharedPreferences sharedPreferences(Context context) {
        return context.getSharedPreferences("datastore", Context.MODE_PRIVATE);
    }

    @AppScope
    @Provides
    public static TodoList initState() {
        return TodoList.initial();
    }

    @AppScope
    @Provides
    public static Reducer<TodoList, Action> reducer() {
        return TodoListReducers.reducer();
    }
}

package com.example.sample_android.reducer;

import com.example.sample_android.action.Action;
import com.example.sample_android.action.Load;
import com.example.sample_android.state.TodoList;

import me.tatarka.redux.Reducer;
import me.tatarka.redux.Reducers;

public class TodoListReducers {

    public static Reducer<TodoList, Action> reducer() {
        return Reducers.<TodoList, Action>matchClass()
                .when(Load.class, new LoadReducer());
    }

}

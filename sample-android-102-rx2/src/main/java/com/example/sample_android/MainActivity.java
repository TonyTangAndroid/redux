package com.example.sample_android;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.sample_android.action.LoadActionCreator;
import com.example.sample_android.state.TodoList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity {

    @Inject
    TodoViewModel viewModel;
    @Inject
    LoadActionCreator loadActionCreator;

    ProgressBar loading;
    FloatingActionButton fab;
    ToDoItemAdapter adapter = new ToDoItemAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        RecyclerView list = findViewById(R.id.list);
        list.setAdapter(adapter);

        loading = findViewById(R.id.loading);
        if (savedInstanceState == null) {
            viewModel.getStore().dispatch(loadActionCreator.load());
        }
        viewModel.getState().observe(this, this::render);
    }


    private void render(TodoList data) {
        loading.setVisibility(data.loading() ? View.VISIBLE : View.GONE);
        adapter.setState(data);
    }


    @Module
    static class MainActivityModule {

        @ActivityScope
        @Provides
        TodoViewModel viewModel(MainActivity mainActivity, ViewModelFactory factory) {
            return ViewModelProviders.of(mainActivity, factory).get(TodoViewModel.class);
        }


    }


}

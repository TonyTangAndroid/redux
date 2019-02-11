package com.example.sample_android;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.sample_android.action.Action;
import com.example.sample_android.action.LoadActionCreator;
import com.example.sample_android.state.TodoList;
import com.example.sample_android.store.MainStore;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Single;

public class MainActivity extends AppCompatActivity {

    MainStore store;
    ToDoItemAdapter adapter = new ToDoItemAdapter(this);
    ProgressBar loading;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        RecyclerView list = findViewById(R.id.list);
        list.setAdapter(adapter);

        loading = findViewById(R.id.loading);

        TodoViewModel viewModel = ViewModelProviders.of(this).get(TodoViewModel.class);
        store = viewModel.getStore();

        if (savedInstanceState == null) {
            final Datastore datastore = new Datastore(this);
            LoadActionCreator loadActionCreator = new LoadActionCreator(datastore);
            Single<Action> single = loadActionCreator.load();
            store.dispatch(single);
        }
        viewModel.getState().observe(this, this::render);
    }


    private void render(TodoList data) {
        loading.setVisibility(data.loading() ? View.VISIBLE : View.GONE);
        adapter.setState(data);
    }


}

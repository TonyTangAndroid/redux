package com.example.sample_android;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.example.sample_android.action.Action;
import com.example.sample_android.action.LoadActionCreator;
import com.example.sample_android.state.TodoList;
import com.example.sample_android.store.MainStore;

import me.tatarka.redux.ReplayMiddleware;

public class MainActivity extends AppCompatActivity {

    MainStore store;
    ReplayMiddleware<TodoList, Action, Action> replayMiddleware;
    ToDoItemAdapter adapter = new ToDoItemAdapter(this);
    ActionListAdapter actionListAdapter = new ActionListAdapter(this);
    ProgressBar loading;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> add());

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        RecyclerView list = findViewById(R.id.list);
        list.setAdapter(adapter);

        RecyclerView actionList = findViewById(R.id.action_list);
        actionList.setAdapter(actionListAdapter);

        loading = findViewById(R.id.loading);

        TodoViewModel viewModel = ViewModelProviders.of(this).get(TodoViewModel.class);
        store = viewModel.getStore();
        replayMiddleware = store.getReplayMiddleware();

        if (savedInstanceState == null) {
            store.dispatch(new LoadActionCreator(new Datastore(this)).load());
        }

        viewModel.getState().observe(this, this::render);
    }

    private void render(TodoList data) {
        loading.setVisibility(data.loading() ? View.VISIBLE : View.GONE);
        if (data.loading()) {
            fab.hide();
        } else {
            fab.show();
        }
        adapter.setState(data);
        actionListAdapter.setState(replayMiddleware.actions());
    }

    private void add() {
        TodoItemDialogFragment.newInstance().show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}

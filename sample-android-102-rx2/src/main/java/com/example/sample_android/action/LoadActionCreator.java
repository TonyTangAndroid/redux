package com.example.sample_android.action;

import com.example.sample_android.Datastore;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import hugo.weaving.DebugLog;
import io.reactivex.Single;

public class LoadActionCreator {

    private final Datastore datastore;

    @Inject
    public LoadActionCreator(Datastore datastore) {
        this.datastore = datastore;
    }

    public Single<Action> load() {
        return Single.fromCallable(this::create).delay(1000, TimeUnit.MILLISECONDS);
    }

    @DebugLog
    private Action create() {
        return Load.create(datastore.getTodoItems());
    }
}

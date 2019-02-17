package com.example.sample_android.action;

import com.example.sample_android.LoadDataUseCase;

import javax.inject.Inject;

import io.reactivex.Single;

public class LoadActionCreator {

    private final LoadDataUseCase datastore;

    @Inject
    public LoadActionCreator(LoadDataUseCase datastore) {
        this.datastore = datastore;
    }

    public Single<Action> load() {
        return datastore.build();
    }
}

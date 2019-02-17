package com.example.sample_android;

import com.example.sample_android.action.Action;
import com.example.sample_android.action.Load;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Single;

public class LoadDataUseCase extends SingleUseCase<Action> {

    private final Datastore datastore;

    @Inject
    public LoadDataUseCase(ThreadExecutor threadExecutor, UIThread UIThread, Datastore datastore) {
        super(threadExecutor, UIThread);
        this.datastore = datastore;
    }

    @Override
    public Single<Action> build() {
        return Single.just(1).delay(10000, TimeUnit.MILLISECONDS).map(this::create);
    }

    private Action create(Integer id) {
        return Load.create(datastore.getTodoItems());
    }

}

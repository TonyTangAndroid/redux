package com.example.sample_android;

import android.app.Application;

import com.example.sample_android.store.MainStore;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import androidx.lifecycle.ViewModel;
import dagger.MapKey;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import hugo.weaving.DebugLog;

@DebugLog
@Module
public class ViewModelModule {

    @Provides
    @IntoMap
    @ViewModelKey(TodoViewModel.class)
    ViewModel noteBeanAndroidViewModel(Application application, MainStore store) {
        return new TodoViewModel(application, store);
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @MapKey
    @interface ViewModelKey {
        Class<? extends ViewModel> value();
    }
}
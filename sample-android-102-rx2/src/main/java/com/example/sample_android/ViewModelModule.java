package com.example.sample_android;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Provider;

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
    ViewModel noteBeanAndroidViewModel(Provider<TodoViewModel> provider) {
        return provider.get();
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @MapKey
    @interface ViewModelKey {
        Class<? extends ViewModel> value();
    }
}
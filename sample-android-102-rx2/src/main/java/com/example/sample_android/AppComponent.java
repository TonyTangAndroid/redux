package com.example.sample_android;

import android.app.Application;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@AppScope
@Component(modules = {AndroidInjectionModule.class,
        ViewModelModule.class,
        ContextModule.class,
        ThreadModule.class,
        ActivityModule.class
})
public interface AppComponent {
    void inject(App app);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application app);

        AppComponent build();
    }
}

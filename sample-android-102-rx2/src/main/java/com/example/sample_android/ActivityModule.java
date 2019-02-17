package com.example.sample_android;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ActivityModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = MainActivity.MainActivityModule.class)
    abstract MainActivity mainActivity();
}

package com.example.sample_android;

import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Provider;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final Map<Class<? extends ViewModel>, Provider<ViewModel>> mProviderMap;

    @Inject
    public ViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> providerMap) {
        mProviderMap = providerMap;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        Provider<ViewModel> obj = mProviderMap.get(modelClass);
        return (T) Objects.requireNonNull(obj).get();
    }
}

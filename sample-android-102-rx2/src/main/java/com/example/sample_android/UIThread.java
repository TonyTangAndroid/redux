package com.example.sample_android;

import io.reactivex.Scheduler;

public interface UIThread {
    public Scheduler getScheduler();
}

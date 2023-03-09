package com.example.benchmarks;

import androidx.annotation.NonNull;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainThreadRule extends TestWatcher {

    @Override
    protected void starting(@NonNull Description description) {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxJavaPlugins.setComputationSchedulerHandler(scheduler -> Schedulers.trampoline());
        super.starting(description);
    }

    @Override
    protected void finished(@NonNull Description description) {
        RxAndroidPlugins.reset();
        RxJavaPlugins.reset();
        super.finished(description);
    }
}

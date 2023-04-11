package com.example.benchmarks

import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.util.concurrent.Callable


open class MainThreadRule : TestWatcher() {

    override fun starting(description: Description) {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler: Callable<Scheduler?>? -> Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        super.starting(description)
    }

    override fun finished(description: Description) {
        RxAndroidPlugins.reset()
        RxJavaPlugins.reset()
        super.finished(description)
    }

}
package com.example.benchmarks.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

open class DispatchersHolderImpl @Inject constructor() : DispatchersHolder {

    override fun getMain(): CoroutineDispatcher {
        return Dispatchers.Main
    }

    override fun getIO(): CoroutineDispatcher {
        return Dispatchers.IO
    }

}
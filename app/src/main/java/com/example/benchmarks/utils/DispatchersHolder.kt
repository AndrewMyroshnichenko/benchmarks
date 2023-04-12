package com.example.benchmarks.utils

import kotlinx.coroutines.CoroutineDispatcher

interface DispatchersHolder {
    fun getMain(): CoroutineDispatcher

    fun getIO(): CoroutineDispatcher
}
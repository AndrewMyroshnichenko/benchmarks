package com.example.benchmarks.utils

import java.util.*

class Pair<F, S>(val first: F, val second: S) {
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val pair = o as Pair<*, *>
        return first == pair.first && second == pair.second
    }

    override fun hashCode(): Int {
        return Objects.hash(first, second)
    }

    override fun toString(): String {
        return "Pair{" +
                "first=" + first +
                ", second=" + second +
                '}'
    }

    companion object {
        fun <A, B> create(a: A, b: B): Pair<A, B> {
            return Pair(a, b)
        }
    }
}
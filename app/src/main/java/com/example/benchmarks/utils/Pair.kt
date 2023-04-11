package com.example.benchmarks.utils

import java.util.*

class Pair<F, S>(val first: F, val second: S) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val pair = other as Pair<*, *>
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
}

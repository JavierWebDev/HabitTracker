package com.example.model

import kotlin.math.abs

class SimpleHashMap<K,V> (private val capacity : Int = 10) {

    private val table = Array(capacity) { mutableListOf<Pair<K, V>>() }

    private fun hash(key : K) : Int = abs(key.hashCode() % capacity)

    fun put(key: K, value: V) {
        val index = hash(key)

        val exists = table[index].find { it.first == key }

        exists?.let { table[index].remove(it) }

        table[index].add(Pair(key, value))
    }

    fun get(key: K) : V? {
        val index = hash(key)

        val exists = table[index].find { it.first == key }?.second

        return exists
    }

    fun remove(key: K) {
        val index = hash(key)

        val exists = table[index].find { it.first == key }

        exists?.let { table[index].remove(it) }
    }

    fun values() : List<V> {
        val flatten = table.flatMap { it.map { pair -> pair.second } }

        return flatten
    }
}
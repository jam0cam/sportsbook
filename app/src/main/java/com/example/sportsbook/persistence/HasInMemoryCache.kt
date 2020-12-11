package com.example.sportsbook.persistence

private typealias CachedMillis = Long

abstract class HasInMemoryCache<K, V>(
    private val canExpire: Boolean = false,
    private val cacheDurationInMillis: Long = DURATION_CACHE
) {

    private val cache = mutableMapOf<K, Pair<CachedMillis, V>>()

    open fun save(key: K, value: V) {
        cache[key] = System.currentTimeMillis() to value
    }

    open fun get(key: K): V? {
        return cache[key]
            ?.takeIf { isNotExpired(it) }
            ?.second
    }

    open fun contains(key: K): Boolean {
        return cache[key] != null
    }

    open fun remove(key: K) {
        cache.remove(key)
    }

    open fun clear() {
        cache.clear()
    }

    open fun isEmpty(): Boolean = cache.isEmpty()

    open fun getAll(): List<V> = cache.values.asSequence()
        .filter { isNotExpired(it) }
        .map { it.second }
        .toList()

    private fun isNotExpired(cachePair: Pair<CachedMillis, V>) = !canExpire || (canExpire && System.currentTimeMillis() - cachePair.first < cacheDurationInMillis)

    companion object {
        private const val DURATION_CACHE = 300000L // 5 minutes
    }
}
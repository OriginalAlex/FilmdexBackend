package io.github.originalalex.filmdex.datastructures.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SimpleCache<K, V> implements Cache<K, V> {

    private long refreshDuration; // The amount of time items are cached for
    private Map<K, CacheItem<V>> cache;

    public SimpleCache() {
        this(TimeUnit.DAYS.toMillis(1));
    }

    public SimpleCache(long refreshDuration) {
        this.refreshDuration = TimeUnit.DAYS.toMillis(1);
        this.cache = new HashMap<>();
    }

    public boolean contains(K key) {
        return cache.containsKey(key);
    }

    public boolean needsRefresh(K key) {
        return (System.currentTimeMillis() - cache.get(key).getInsertionTime()) > this.refreshDuration;
    }

    public void put(K key, V value) {
        cache.put(key, new CacheItem<>(value));
    }

    public V get(K key) {
        return cache.get(key).getValue();
    }

}

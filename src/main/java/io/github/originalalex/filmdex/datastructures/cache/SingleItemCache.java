package io.github.originalalex.filmdex.datastructures.cache;

import java.util.concurrent.TimeUnit;

public class SingleItemCache<V> {

    private Cache.CacheItem<V> item;
    private long refreshDuration;

    public SingleItemCache() {
        this(TimeUnit.DAYS.toMillis(1));
    }

    public SingleItemCache(long refreshDuration) {
        this.refreshDuration = refreshDuration;
    }

    public boolean needsRefresh() {
        return (item == null) || ((System.currentTimeMillis() - item.getInsertionTime()) > refreshDuration);
    }

    public V getValue() {
        return item.getValue();
    }

    public void setValue(V value) {
        item = new Cache.CacheItem<>(value);
    }

}

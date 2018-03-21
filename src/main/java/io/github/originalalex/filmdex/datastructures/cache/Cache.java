package io.github.originalalex.filmdex.datastructures.cache;

public interface Cache<K, V> {

    class CacheItem<V> {
        private long insertionTime;
        private V value;

        protected CacheItem(V value) {
            this.value = value;
            this.insertionTime = System.currentTimeMillis();
        }

        protected V getValue() {
            return this.value;
        }

        public long getInsertionTime() {
            return this.insertionTime;
        }
    }

    // Simple contains method
    boolean contains(K key);

    // Returns true if the last value for the specific key was set over a specified duration of time
    boolean needsRefresh(K key);

    // Add the key with a value
    void put(K key, V value);

    // Return the value associated with a specific key in the cache.
    V get(K key);

}

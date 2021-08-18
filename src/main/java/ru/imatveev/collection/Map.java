package ru.imatveev.collection;

import java.util.Set;

public interface Map<K, V> {
    int size();

    boolean isEmpty();

    boolean containsKey(Object key);

    boolean containsValue(Object value);

    V get(Object key);

    V put(K key, V value);

    V remove(Object key);

    void putAll(Map<? extends K, ? extends V> map);

    void clear();

    Set<K> keySet();

    Collection<V> values();

    Set<Entry<K, V>> entrySet();

    default V getOrDefault(Object key, V defaultValue) {
        Object v = this.get(key);
        return v == null && !this.containsKey(key) ? defaultValue : (V) v;
    }

    interface Entry<K, V> {
        K getKey();

        V getValue();

        V setValue(V value);

        boolean equals(Object value);

        int hashCode();
    }
}

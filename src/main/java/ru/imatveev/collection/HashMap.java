package ru.imatveev.collection;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings("unchecked")
public class HashMap<K, V> implements Map<K, V> {
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private int size;
    private Node<K, V>[] nodes;

    public HashMap(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        } else {
            nodes = new Node[initialCapacity];
        }
    }

    public HashMap() {
        nodes = new Node[DEFAULT_INITIAL_CAPACITY];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        if (size != 0) {
            int index = getIndex(key);
            Node<K, V> node = nodes[index];

            if (node != null) {
                if (Objects.equals(node.getKey(), key)) {
                    return true;
                }

                Node<K, V> next = node.next;
                while (next != null) {
                    if (Objects.equals(next.getKey(), key)) {
                        return true;
                    }
                    next = next.next;
                }
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        for (Entry<K, V> entry : entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        int index = getIndex(key);
        Node<K, V> node = nodes[index];
        while (node != null && !Objects.equals(node.getKey(), key)) {
            node = node.next;
        }
        return (node == null) ? null : node.getValue();
    }

    @Override
    public V put(K key, V value) {
        int hash = hash(key);
        if (nodes.length == 0 || nodes.length == size) {
            resize();
        }

        int index = (nodes.length - 1) & hash;
        Node<K, V> node = new Node<>(hash, key, value, null);

        if (nodes[index] == null) {
            nodes[index] = node;
        } else {
            Node<K, V> parentNode = nodes[index];
            while (parentNode.next != null) {
                parentNode = parentNode.next;
            }
            parentNode.next = node;
        }
        size++;
        return value;
    }

    @Override
    public V remove(Object key) {
        int index = getIndex(key);
        Node<K, V> node = nodes[index];
        Node<K, V> parentNode = null;

        while (node != null && !Objects.equals(node.getKey(), key)) {
            parentNode = node;
            node = node.next;
        }

        if (parentNode == null) {
            nodes[index] = null;
        } else {
            parentNode.next = (node == null) ? null : node.next;
        }

        return (node == null) ? null : node.getValue();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        for (Entry<? extends K, ? extends V> entry : map.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        nodes = new Node[DEFAULT_INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>(); //todo - replace by ru.imatveev.collection.Set
        for (Entry<K, V> entry : entrySet()) {
            keySet.add(entry.getKey());
        }
        return keySet;
    }

    @Override
    public Collection<V> values() {
        Collection<V> values = new ArrayList<>();
        for (Entry<K, V> entry : entrySet()) {
            values.add(entry.getValue());
        }
        return values;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> entrySet = new HashSet<>(); //todo - replace by ru.imatveev.collection.Set

        for (Node<K, V> node : nodes) {
            if (node != null) {
                entrySet.add(node);

                Node<K, V> next = node.next;
                while (next != null) {
                    entrySet.add(next);
                    next = next.next;
                }
            }
        }

        return entrySet;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("HashMap{");

        Set<Entry<K, V>> entries = entrySet();
        if (!entries.isEmpty()) {
            for (Entry<K, V> entry : entries) {
                str.append(entry).append(";");
            }
            str.deleteCharAt(str.length() - 1);
        }

        return str.append("}").toString();
    }

    private static class Node<K, V> implements Entry<K, V> {

        int hash;
        K key;
        V value;
        HashMap.Node<K, V> next;

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public String toString() {
            return key.toString() + "=" + value.toString();
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

    }

    private static int hash(Object key) {
        int h;
        return key == null ? 0 : (h = key.hashCode()) ^ h >>> 16;
    }

    private void resize() {
        int oldLength = nodes.length;
        int newLength = (oldLength == 0) ? 2 : oldLength * 2;
        Node<K, V>[] newNodes = new Node[newLength];

        for (Node<K, V> node : nodes) {
            if (node != null) {
                int newIndex = (newLength - 1) & node.hash;
                newNodes[newIndex] = node;

                Node<K, V> next = node.next;
                while (next != null) {
                    int newNextIndex = (newLength - 1) & next.hash;
                    newNodes[newNextIndex] = next;
                    next = next.next;
                }
            }
        }

        nodes = newNodes;
    }

    private int getIndex(Object key) {
        return (nodes.length - 1) & hash(key);
    }
}

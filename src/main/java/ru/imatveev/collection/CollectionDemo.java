package ru.imatveev.collection;

public interface CollectionDemo<E> {
    int size();

    boolean isEmpty();

    boolean contains(Object element);

    boolean add(E var1);

    boolean remove(Object element);

    boolean containsAll(CollectionDemo<?> var1);

    boolean addAll(CollectionDemo<? extends E> var1);

    boolean removeAll(CollectionDemo<?> var1);

    boolean retainAll(CollectionDemo<?> var1);

    void clear();

    Object[] toArray();

    <T> T[] toArray(T[] newData);

    boolean equals(Object collection);

    int hashCode();
}

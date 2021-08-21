package ru.imatveev.collection;

public interface Collection<E> {
    int size();

    boolean isEmpty();

    boolean contains(Object element);

    boolean add(E element);

    boolean add(int index, E element);

    boolean remove(Object element);

    boolean containsAll(Collection<?> var1);

    boolean addAll(Collection<? extends E> var1);

    boolean removeAll(Collection<?> var1);

    boolean retainAll(Collection<?> var1);

    void clear();

    Object[] toArray();

    <T> T[] toArray(T[] newData);

    boolean equals(Object collection);

    int hashCode();
}

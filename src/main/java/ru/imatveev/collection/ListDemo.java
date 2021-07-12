package ru.imatveev.collection;

public interface ListDemo<E> extends CollectionDemo<E> {

    boolean addAll(int index, CollectionDemo<? extends E> c);

    E set(int index, E element);

    E get(int index);

    E remove(int index);

    int indexOf(Object element);

    int lastIndexOf(Object element);
}

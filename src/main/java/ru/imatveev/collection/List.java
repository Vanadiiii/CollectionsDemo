package ru.imatveev.collection;

public interface List<E> extends Collection<E> {

    boolean addAll(int index, Collection<? extends E> c);

    E set(int index, E element);

    E get(int index);

    E remove(int index);

    int indexOf(Object element);

    int lastIndexOf(Object element);
}

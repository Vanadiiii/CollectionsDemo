package ru.imatveev.collection;

import java.util.Arrays;
import java.util.Objects;

@SuppressWarnings("unchecked")
public class LinkedListDemo<E> implements ListDemo<E> {
    private int size;
    private Node<E> first;
    private Node<E> last;

    public LinkedListDemo() {
        first = new Node<>(null, null, last);
        last = new Node<>(first, null, null);
        this.size = 0;
    }

    public LinkedListDemo(CollectionDemo<E> collection) {
        this();
        this.addAll(collection);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object element) {
        return indexOf(element) >= 0;
    }

    @Override
    public boolean add(E element) {
        Node<E> node = new Node<>(element);
        Node<E> penult = last.prev; // предпоследний

        last.prev = node;
        penult.next = node;
        node.prev = penult;
        node.next = last;

        ++size;
        return true;
    }

    @Override
    public boolean remove(Object element) {
        return false;
    }

    @Override
    public boolean containsAll(CollectionDemo<?> collection) {
        Object[] objects = collection.toArray();
        for (Object object : objects) {
            if (!contains(object)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(CollectionDemo<? extends E> collection) {
        if (collection.isEmpty()) {
            return false;
        }
        E[] elements = (E[]) collection.toArray();
        for (int i = 0; i < collection.size(); i++) {
            add(elements[i]);
        }
        return true;
    }

    @Override
    public boolean removeAll(CollectionDemo<?> collection) {
        return false;
    }

    @Override
    public boolean retainAll(CollectionDemo<?> collection) {
        return false;
    }

    @Override
    public void clear() {
        Node<E> node = first;
        while (node.next != null) {
            node.prev = null;
            node.element = null;

            Node<E> next = node.next;
            node.next = null;
            next.prev = null;

            node = next;

        }
        size = 0;
        first = new Node<>(null, null, last);
        last = new Node<>(first, null, null);
    }

    @Override
    public Object[] toArray() {
        if (size == 0) {
            return new Object[]{};
        } else {
            Object[] array = new Object[size];
            Node<E> node = first;
            for (int i = 0; i < size; i++) {
                array[i] = node.next.element;
                node = node.next;
            }
            return array;
        }
    }

    @Override
    public <T> T[] toArray(T[] newData) {
        T[] array = (T[]) toArray();
        if (newData.length < size) {
            return array;
        } else {
            System.arraycopy(array, 0, newData, 0, size);
            return newData;
        }
    }

    @Override
    public boolean addAll(int index, CollectionDemo<? extends E> collection) {
        LinkedListDemo<E> list = new LinkedListDemo<>((CollectionDemo<E>) collection);
        Node<E> node = getNode(index);
        Node<E> beforeNode = node.prev;
        Node<E> last = list.last.prev;
        Node<E> first = list.first.next;

        beforeNode.next = first;
        first.prev = beforeNode;

        node.prev = last;
        last.next = node;

        size = size + collection.size();

        return true;
    }

    @Override
    public E set(int index, E element) {
        Node<E> node = getNode(index);
        E value = node.element;
        node.element = element;
        return value;
    }

    private Node<E> getNode(int index) {
        checkIndex(index);

        int count;
        Node<E> node;
        if (size - index > index) {
            count = 0;
            node = first.next;
            while (count != index) {
                node = node.next;
                ++count;
            }
        } else {
            count = size - 1;
            node = last.prev;
            while (count != index) {
                node = node.prev;
                --count;
            }
        }

        return node;
    }

    @Override
    public E get(int index) {
        return getNode(index).element;
    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object element) {
        Node<E> node = first;
        int index = 0;

        for (int i = 0; i < size; i++) {
            if (Objects.equals(node.next.element, element)) {
                return index;
            }
            ++index;
            node = node.next;
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object element) {
        Node<E> node = last;
        int index = size;
        while (node.prev != null) {
            if (Objects.equals(node.element, element)) {
                return index;
            }
            --index;
            node = node.prev;
        }
        return -1;
    }

    @Override
    public String toString() {
        return "LinkedListDemo" + Arrays.toString(toArray());
    }

    private static class Node<E> {
        private E element;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }

        Node(E element) {
            this.element = element;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new RuntimeException("Index " + index + " out of bounds");
        }
    }
}

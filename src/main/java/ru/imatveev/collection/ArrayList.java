package ru.imatveev.collection;

import ru.imatveev.collection.annotation.Complex;

import java.util.Arrays;

import static ru.imatveev.collection.annotation.Complex.Complexity.CONSTANT;
import static ru.imatveev.collection.annotation.Complex.Complexity.LINEAR;

@SuppressWarnings("unchecked")
public class ArrayList<E> implements List<E> {
    private Object[] data;
    private int size;

    private final Object[] INITIAL_ARRAY_DATA = new Object[10];

    public ArrayList() {
        this.data = INITIAL_ARRAY_DATA;
        this.size = 0;
    }

    ArrayList(int size) {
        if (size < 0) {
            throw new RuntimeException("Size must be positive number");
        }
        this.data = new Object[size];
        this.size = 0;
    }

    ArrayList(Collection<E> collection) {
        this.size = collection.size();
        if (size != 0) {
            for (int i = 0; i < size; i++) {
                this.data[i] = (E) collection.toArray()[i];
            }
        } else {
            this.data = INITIAL_ARRAY_DATA;
        }
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
    @Complex(CONSTANT)
    public boolean add(E element) {
        if (size == 0 && data.length == 0) {
            data = new Object[10];
        }
        if (size == data.length) {
            grow((int) (1.5 * data.length));
        }

        data[size] = element;
        ++size;
        return true;
    }

    @Override
    @Complex(LINEAR)
    public boolean add(int index, E element) {
        if (size == 0 && data.length == 0) {
            data = new Object[10];
        }
        if (size == data.length) {
            grow((int) (1.5 * data.length));
        }

        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = element;
        ++size;

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        if (collection.isEmpty()) {
            return false;
        }

        int size = collection.size();
        int emptyNum = data.length - this.size;
        if (size > emptyNum) {
            grow(data.length + (size - emptyNum + 1));
        }

        System.arraycopy(collection.toArray(), 0, this.data, this.size, size);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> collection) {
        int collectionSize = collection.size();
        if (collectionSize == 0) {
            return false;
        }

        checkIndex(index);

        int size = this.size + collectionSize;
        Object[] newData = new Object[size];

        System.arraycopy(data, 0, newData, 0, index);
        System.arraycopy(collection.toArray(), 0, newData, index, collectionSize);
        System.arraycopy(data, index, newData, index + collectionSize, this.size - index);

        this.data = newData;
        this.size = size;
        return true;
    }

    @Override
    public boolean remove(Object element) {
        int index = indexOf(element);
        if (index >= 0) {
            remove(index);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        if (collection.isEmpty()) {
            return false;
        }

        boolean result = false;
        Object[] newData = collection.toArray();
        for (int i = 0; i < collection.size(); i++) {
            if (contains(newData[i])) {
                result |= remove(newData[i]);
            }
        }
        return result;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        if (collection.isEmpty()) {
            return false;
        }

        boolean result = false;
        Object[] newData = collection.toArray();
        for (int i = 0; i < collection.size(); i++) {
            if (!contains(newData[i])) {
                result |= remove(newData[i]);
            }
        }
        return result;
    }

    @Override
    public E set(int index, E element) {
        checkIndex(index);
        E oldValue = (E) data[index];
        data[index] = element;
        return oldValue;
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return (E) data[index];
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        E value = (E) data[index];

        System.arraycopy(data, index + 1, data, index, size - 1 - index);

        data[size - 1] = null;
        --size;

        return value;
    }

    @Override
    @Complex(LINEAR)
    public int indexOf(Object element) {
        if (size == 0) {
            return -1;
        }
        for (int i = 0; i < size; i++) {
            if (element.equals(data[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    @Complex(LINEAR)
    public int lastIndexOf(Object element) {
        if (size == 0) {
            return -1;
        }
        for (int i = size - 1; i >= 0; i--) {
            if (element.equals(data[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void clear() {
        data = INITIAL_ARRAY_DATA;
        size = 0;
    }

    @Override
    public Object[] toArray() {
        if (size == 0) {
            return new Object[]{};
        } else {
            Object[] newData = new Object[size];
            System.arraycopy(data, 0, newData, 0, size);
            return newData;
        }
    }

    @Override
    public <T> T[] toArray(T[] newData) {
        if (newData.length < this.size) {
            return (T[]) Arrays.copyOf(this.data, this.size, newData.getClass());
        } else {
            System.arraycopy(this.data, 0, newData, 0, this.size);
            return newData;
        }
    }

    @Override
    @Complex(LINEAR)
    public boolean contains(Object element) {
        return indexOf(element) >= 0;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        Object[] data = collection.toArray();
        for (int i = 0; i < collection.size(); i++) {
            if (!contains(data[i])) {
                return false;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "ArrayListDemo" + Arrays.toString(toArray());
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new RuntimeException("Index " + index + " out of bounds");
        }
    }

    /**
     * @param minCapacity new array size
     * @return new array capacity
     */
    private int grow(int minCapacity) {
        Object[] newData = new Object[minCapacity + 1];
        System.arraycopy(data, 0, newData, 0, data.length);
        data = newData;

        return newData.length;
    }
}

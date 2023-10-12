package ru.clevertec.mypriorityqueue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

@SuppressWarnings("unchecked")
public class MyPriorityQueueImpl<E> implements MyPriorityQueue<E> {

    private Object[] queue;
    private int size;
    private static final int DEFAULT_CAPACITY = 8;
    private Comparator<? super E> comparator;

    public MyPriorityQueueImpl() {
        queue = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    public MyPriorityQueueImpl(Comparator<? super E> comparator) {
        queue = new Object[DEFAULT_CAPACITY];
        size = 0;
        this.comparator = comparator;
    }

    public MyPriorityQueueImpl(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0 !");
        }
        queue = new Object[capacity];
        size = 0;
    }

    public MyPriorityQueueImpl(int capacity, Comparator<? super E> comparator) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0 !");
        }
        queue = new Object[capacity];
        size = 0;
        this.comparator = comparator;
    }

    @Override
    public boolean add(E element) {
        if (element == null) {
            throw new NullPointerException("Can't store null elements!");
        }
        if (size == queue.length) {
            queue = Arrays.copyOf(queue, queue.length * 2);
        }
        queue[size] = element;
        size++;
        siftUp(size - 1);
        return true;
    }

    @Override
    public E poll() {
        if (size == 0) {
            return null;
        }
        E result = (E) queue[0];
        queue[0] = queue[size - 1];
        queue[size - 1] = null;
        size--;
        siftDown();
        return result;
    }

    @Override
    public E peek() {
        if (size == 0) {
            return null;
        }
        return (E) queue[0];
    }

    private void siftUp(int i) {
        while (i > 0 && compare(i, parent(i)) < 0) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    private void siftDown() {
        int i = 0;
        while (left(i) < size) {
            int min = left(i);
            if (right(i) < size && compare(right(i), left(i)) < 0) {
                min = right(i);
            }
            if (compare(i, min) <= 0) {
                break;
            }
            swap(i, min);
            i = min;
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

    private int compare(int i, int j) {
        return comparator == null
                ? ((Comparable<? super E>) queue[i]).compareTo((E) queue[j])
                : comparator.compare((E) queue[i], (E) queue[j]);
    }

    private void swap(int i, int j) {
        Object temp = queue[i];
        queue[i] = queue[j];
        queue[j] = temp;
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }

    private int left(int i) {
        return i * 2 + 1;
    }

    private int right(int i) {
        return i * 2 + 2;
    }

    @Override
    public String toString() {
        return Arrays.stream(queue)
                .filter(Objects::nonNull)
                .toList()
                .toString();
    }

}

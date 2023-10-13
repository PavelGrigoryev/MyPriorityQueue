package ru.clevertec.mypriorityqueue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

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
        Optional.of(capacity)
                .filter(integer -> integer > 0)
                .ifPresentOrElse(integer -> {
                    queue = new Object[integer];
                    size = 0;
                }, () -> {
                    throw new IllegalArgumentException("Capacity must be greater than 0 !");
                });
    }

    public MyPriorityQueueImpl(int capacity, Comparator<? super E> comparator) {
        Optional.of(capacity)
                .filter(integer -> integer > 0)
                .ifPresentOrElse(integer -> {
                    queue = new Object[integer];
                    size = 0;
                    this.comparator = comparator;
                }, () -> {
                    throw new IllegalArgumentException("Capacity must be greater than 0 !");
                });
    }

    @Override
    public boolean add(E element) {
        Optional.ofNullable(element)
                .ifPresentOrElse(e -> {
                    if (size == queue.length) {
                        queue = Arrays.copyOf(queue, queue.length * 2);
                    }
                    queue[size] = e;
                    size++;
                    siftUp(size - 1);
                }, () -> {
                    throw new NullPointerException("Can't store null elements!");
                });
        return true;
    }

    @Override
    public E poll() {
        return Optional.ofNullable((E) queue[0])
                .map(element -> {
                    queue[0] = queue[size - 1];
                    queue[size - 1] = null;
                    size--;
                    siftDown();
                    return element;
                })
                .orElse(null);
    }

    @Override
    public E peek() {
        return (E) queue[0];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
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

    private int compare(int i, int j) {
        return Optional.ofNullable(comparator)
                .orElseGet(() -> (e1, e2) -> ((Comparable<? super E>) e1).compareTo((E) e2))
                .compare((E) queue[i], (E) queue[j]);
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

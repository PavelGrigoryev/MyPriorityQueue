package ru.clevertec.mypriorityqueue;

public interface MyPriorityQueue<E> {

    boolean add(E e);

    E poll();

    E peek();

    int size();

    boolean isEmpty();

}

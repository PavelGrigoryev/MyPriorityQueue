package ru.clevertec.mypriorityqueue;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Pain {

    public static void main(String[] args) {
        MyPriorityQueue<Integer> queue = new MyPriorityQueueImpl<>();
        queue.add(56);
        System.out.println(queue);
        queue.add(17);
        System.out.println(queue);
        queue.add(17);
        System.out.println(queue);
        queue.add(20);
        System.out.println(queue);
        queue.add(3);
        System.out.println(queue);
        queue.add(123);
        System.out.println(queue);
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(56);
        System.out.println(priorityQueue);
        priorityQueue.add(17);
        System.out.println(priorityQueue);
        priorityQueue.add(17);
        System.out.println(priorityQueue);
        priorityQueue.add(20);
        System.out.println(priorityQueue);
        priorityQueue.add(3);
        System.out.println(priorityQueue);
        priorityQueue.add(123);
        System.out.println(priorityQueue);
        System.out.println(queue.peek());
        System.out.println(queue);
        System.out.println("Size MyQueue: " + queue.size());
        System.out.println(priorityQueue.peek());
        System.out.println(priorityQueue);
        System.out.println("Size PriorityQueue: " + priorityQueue.size());

        MyPriorityQueue<Person> personMyPriorityQueue = new MyPriorityQueueImpl<>(Comparator.reverseOrder());
        personMyPriorityQueue.add(new Person(2L, "Vasya"));
        personMyPriorityQueue.add(new Person(1L, "Nastya"));
        System.out.println(personMyPriorityQueue.peek());
        System.out.println(personMyPriorityQueue);
    }

}

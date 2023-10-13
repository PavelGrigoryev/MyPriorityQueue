package ru.clevertec.mypriorityqueue.model;

public record PersonWithComparableById(Long id, String name) implements Comparable<PersonWithComparableById> {

    @Override
    public int compareTo(PersonWithComparableById o) {
        return id.compareTo(o.id());
    }

}

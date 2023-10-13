package ru.clevertec.mypriorityqueue.model;

public record PersonWithComparableByName(Long id, String name) implements Comparable<PersonWithComparableByName> {

    @Override
    public int compareTo(PersonWithComparableByName o) {
        return name.compareTo(o.name);
    }

}

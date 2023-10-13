package ru.clevertec.mypriorityqueue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.mypriorityqueue.model.PersonWithComparableById;
import ru.clevertec.mypriorityqueue.model.PersonWithComparableByName;
import ru.clevertec.mypriorityqueue.model.PersonWithoutComparable;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MyPriorityQueueImplNoArgsConstructorTest {

    private MyPriorityQueue<Object> queue;

    @BeforeEach
    void setUp() {
        queue = new MyPriorityQueueImpl<>();
    }

    @Test
    void testAddShouldReturnTrue() {
        boolean actual = queue.add(56);

        assertThat(actual).isTrue();
    }

    @Test
    void testAddShouldThrowNullPointerExceptionWithExpectedMessage() {
        String expectedMessage = "Can't store null elements!";

        Exception exception = assertThrows(NullPointerException.class, () -> queue.add(null));
        String actualMessage = exception.getMessage();

        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    @Test
    void testAddShouldExpandArray() {
        IntStream.range(0, 9)
                .forEach(queue::add);

        assertThat(queue.size()).isEqualTo(9);
    }

    @Test
    void testAddShouldThrowClassCastExceptionIfClassIsNotImplementsComparable() {
        PersonWithoutComparable person1 = new PersonWithoutComparable(1L, "Ivan");
        PersonWithoutComparable person2 = new PersonWithoutComparable(2L, "Sveta");

        queue.add(person1);

        assertThrows(ClassCastException.class, () -> queue.add(person2));
    }

    @Test
    void testPollShouldReturnExpectedValueAndRemoveItFromQueuesHead() {
        Stream.of(250, 23345, 11, 55, 0, 222, 13, 99, 16, 3, 1_000_000)
                .forEach(queue::add);

        assertAll(
                () -> assertThat(queue.poll()).isEqualTo(0),
                () -> assertThat(queue.poll()).isEqualTo(3),
                () -> assertThat(queue.poll()).isEqualTo(11),
                () -> assertThat(queue.poll()).isEqualTo(13),
                () -> assertThat(queue.poll()).isEqualTo(16),
                () -> assertThat(queue.poll()).isEqualTo(55),
                () -> assertThat(queue.poll()).isEqualTo(99),
                () -> assertThat(queue.poll()).isEqualTo(222),
                () -> assertThat(queue.poll()).isEqualTo(250),
                () -> assertThat(queue.poll()).isEqualTo(23345),
                () -> assertThat(queue.poll()).isEqualTo(1_000_000),
                () -> assertThat(queue.poll()).isNull()
        );
    }

    @Test
    void testPollShouldReturnExpectedValueComparableById() {
        PersonWithComparableById person = new PersonWithComparableById(5L, "Nastya");
        PersonWithComparableById expectedValue = new PersonWithComparableById(2L, "Vasya");
        queue.add(person);
        queue.add(expectedValue);

        assertThat(queue.poll()).isEqualTo(expectedValue);
    }

    @Test
    void testPollShouldReturnExpectedValueComparableByName() {
        PersonWithComparableByName person = new PersonWithComparableByName(5L, "Nastya");
        PersonWithComparableByName expectedValue = new PersonWithComparableByName(33L, "Anya");
        queue.add(person);
        queue.add(expectedValue);

        assertThat(queue.poll()).isEqualTo(expectedValue);
    }

    @Test
    void testPeekShouldReturnExpectedValueWithoutRemovingIt() {
        String expectedValue = "Anger";
        queue.add(expectedValue);

        assertAll(
                () -> assertThat(queue.peek()).isEqualTo(expectedValue),
                () -> assertThat(queue.peek()).isEqualTo(expectedValue),
                () -> assertThat(queue.peek()).isNotNull()
        );
    }

    @Test
    void testSizeShouldReturnZeroIfNoArgsConstructor() {
        assertThat(queue.size()).isZero();
    }

    @Test
    void testSizeShouldReturnThreeAfterAddingThreeElements() {
        int expectedSize = 3;
        Stream.of(250, 23345, 11)
                .forEach(queue::add);

        assertThat(queue.size()).isEqualTo(expectedSize);
    }

    @Test
    void testIsEmptyShouldReturnTrue() {
        assertThat(queue.isEmpty()).isTrue();
    }

    @Test
    void testIsEmptyShouldReturnFalse() {
        queue.add(1);

        assertThat(queue.isEmpty()).isFalse();
    }

    @Test
    void testToStringShouldReturnExpectedStringIfQueueIsEmpty() {
        String expectedString = "[]";

        assertThat(queue).hasToString(expectedString);
    }

    @Test
    void testToStringShouldReturnExpectedStringIfQueueContainsTwoElements() {
        Stream.of("Baby", "Auto", "Sinister")
                .forEach(queue::add);
        String expectedString = "[Auto, Baby, Sinister]";

        assertThat(queue).hasToString(expectedString);
    }

}

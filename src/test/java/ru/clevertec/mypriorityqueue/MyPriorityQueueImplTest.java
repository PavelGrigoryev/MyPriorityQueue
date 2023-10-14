package ru.clevertec.mypriorityqueue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.clevertec.mypriorityqueue.model.PersonWithComparableById;
import ru.clevertec.mypriorityqueue.model.PersonWithComparableByName;
import ru.clevertec.mypriorityqueue.model.PersonWithoutComparable;

import java.util.Comparator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MyPriorityQueueImplTest {

    private MyPriorityQueue<Object> noArgsQueue;
    private MyPriorityQueue<PersonWithoutComparable> withComparatorQueue;

    @BeforeEach
    void setUp() {
        noArgsQueue = new MyPriorityQueueImpl<>();
        withComparatorQueue = new MyPriorityQueueImpl<>(Comparator.comparing(PersonWithoutComparable::id)
                .thenComparing(PersonWithoutComparable::name).reversed());
    }

    @Nested
    class TestConstructorsWithCapacity {

        @Test
        void testConstructorWithCapacityShouldThrowIllegalArgumentExceptionWithExpectedMessage() {
            String expectedMessage = "Capacity must be greater than 0 !";

            Exception exception = assertThrows(IllegalArgumentException.class,
                    () -> new MyPriorityQueueImpl<>(-1));
            String actualMessage = exception.getMessage();

            assertThat(actualMessage).isEqualTo(expectedMessage);
        }

        @Test
        void testConstructorWithCapacityShouldCreateExpectedQueue() {
            MyPriorityQueue<String> queue = new MyPriorityQueueImpl<>(10);

            assertThat(queue).isNotNull();
        }

        @Test
        void testConstructorWithCapacityAndComparatorShouldThrowIllegalArgumentExceptionWithExpectedMessage() {
            String expectedMessage = "Capacity must be greater than 0 !";

            Exception exception = assertThrows(IllegalArgumentException.class,
                    () -> new MyPriorityQueueImpl<>(0, null));
            String actualMessage = exception.getMessage();

            assertThat(actualMessage).isEqualTo(expectedMessage);
        }

        @Test
        void testConstructorWithCapacityAndComparatorShouldCreateExpectedQueue() {
            MyPriorityQueue<String> queue = new MyPriorityQueueImpl<>(10, Comparator.reverseOrder());

            assertThat(queue).isNotNull();
        }

    }

    @Nested
    class TestAddMethod {

        @Test
        void testAddShouldReturnTrue() {
            boolean actual = noArgsQueue.add(56);

            assertThat(actual).isTrue();
        }

        @Test
        void testAddShouldThrowNullPointerExceptionWithExpectedMessage() {
            String expectedMessage = "Can't store null elements!";

            Exception exception = assertThrows(NullPointerException.class, () -> noArgsQueue.add(null));
            String actualMessage = exception.getMessage();

            assertThat(actualMessage).isEqualTo(expectedMessage);
        }

        @Test
        void testAddShouldExpandArray() {
            IntStream.range(0, 9)
                    .forEach(noArgsQueue::add);

            assertThat(noArgsQueue.size()).isEqualTo(9);
        }

        @Test
        void testAddShouldThrowClassCastExceptionWithExpectedMessageIfClassIsNotImplementsComparable() {
            PersonWithoutComparable person1 = new PersonWithoutComparable(1L, "Ivan");
            PersonWithoutComparable person2 = new PersonWithoutComparable(2L, "Sveta");
            String expectedMessage = "Object " + person2 + " does not implement Comparable interface";
            noArgsQueue.add(person1);

            Exception exception = assertThrows(ClassCastException.class, () -> noArgsQueue.add(person2));
            String actualMessage = exception.getMessage();

            assertThat(actualMessage).isEqualTo(expectedMessage);
        }

        @Test
        void testAddShouldNotThrowClassCastExceptionIfConstructorWitComparator() {
            PersonWithoutComparable person1 = new PersonWithoutComparable(1L, "Ivan");
            PersonWithoutComparable person2 = new PersonWithoutComparable(2L, "Sveta");

            withComparatorQueue.add(person1);
            withComparatorQueue.add(person2);

            assertDoesNotThrow(() -> new ClassCastException());
        }

    }

    @Nested
    class TestPollMethod {
        @Test
        void testPollShouldReturnExpectedValueAndRemoveItFromQueuesHead() {
            Stream.of(250, 23345, 11, 55, 0, 222, 13, 99, 16, 3, 1_000_000)
                    .forEach(noArgsQueue::add);

            assertAll(
                    () -> assertThat(noArgsQueue.poll()).isEqualTo(0),
                    () -> assertThat(noArgsQueue.poll()).isEqualTo(3),
                    () -> assertThat(noArgsQueue.poll()).isEqualTo(11),
                    () -> assertThat(noArgsQueue.poll()).isEqualTo(13),
                    () -> assertThat(noArgsQueue.poll()).isEqualTo(16),
                    () -> assertThat(noArgsQueue.poll()).isEqualTo(55),
                    () -> assertThat(noArgsQueue.poll()).isEqualTo(99),
                    () -> assertThat(noArgsQueue.poll()).isEqualTo(222),
                    () -> assertThat(noArgsQueue.poll()).isEqualTo(250),
                    () -> assertThat(noArgsQueue.poll()).isEqualTo(23345),
                    () -> assertThat(noArgsQueue.poll()).isEqualTo(1_000_000),
                    () -> assertThat(noArgsQueue.poll()).isNull()
            );
        }

        @Test
        void testPollShouldReturnExpectedValueComparableById() {
            PersonWithComparableById person = new PersonWithComparableById(5L, "Nastya");
            PersonWithComparableById expectedValue = new PersonWithComparableById(2L, "Vasya");
            noArgsQueue.add(person);
            noArgsQueue.add(expectedValue);

            assertThat(noArgsQueue.poll()).isEqualTo(expectedValue);
        }

        @Test
        void testPollShouldReturnExpectedValueComparableByName() {
            PersonWithComparableByName person = new PersonWithComparableByName(5L, "Nastya");
            PersonWithComparableByName expectedValue = new PersonWithComparableByName(33L, "Anya");
            noArgsQueue.add(person);
            noArgsQueue.add(expectedValue);

            assertThat(noArgsQueue.poll()).isEqualTo(expectedValue);
        }

        @Test
        void testPollShouldReturnExpectedValueAndRemoveItFromQueuesHeadWithComparator() {
            PersonWithoutComparable person1 = new PersonWithoutComparable(33L, "James");
            PersonWithoutComparable person2 = new PersonWithoutComparable(17L, "George");
            PersonWithoutComparable person3 = new PersonWithoutComparable(17L, "Abby");
            PersonWithoutComparable person4 = new PersonWithoutComparable(199L, "Sam");
            Stream.of(person1, person2, person3, person4)
                    .forEach(withComparatorQueue::add);

            assertAll(
                    () -> assertThat(withComparatorQueue.poll()).isEqualTo(person4),
                    () -> assertThat(withComparatorQueue.poll()).isEqualTo(person1),
                    () -> assertThat(withComparatorQueue.poll()).isEqualTo(person2),
                    () -> assertThat(withComparatorQueue.poll()).isEqualTo(person3),
                    () -> assertThat(withComparatorQueue.poll()).isNull()
            );
        }

    }

    @Nested
    class TestPeekMethod {

        @Test
        void testPeekShouldReturnExpectedValueWithoutRemovingIt() {
            String expectedValue = "Anger";
            noArgsQueue.add(expectedValue);

            assertAll(
                    () -> assertThat(noArgsQueue.peek()).isEqualTo(expectedValue),
                    () -> assertThat(noArgsQueue.peek()).isEqualTo(expectedValue),
                    () -> assertThat(noArgsQueue.peek()).isNotNull()
            );
        }

        @Test
        void testPeekShouldReturnNull() {
            assertThat(noArgsQueue.peek()).isNull();
        }

    }

    @Nested
    class TestSizeMethod {

        @Test
        void testSizeShouldReturnZero() {
            assertThat(noArgsQueue.size()).isZero();
        }

        @Test
        void testSizeShouldReturnThreeAfterAddingThreeElements() {
            int expectedSize = 3;
            Stream.of(250, 23345, 11)
                    .forEach(noArgsQueue::add);

            assertThat(noArgsQueue.size()).isEqualTo(expectedSize);
        }

    }

    @Nested
    class TestIsEmptyMethod {

        @Test
        void testIsEmptyShouldReturnTrue() {
            assertThat(noArgsQueue.isEmpty()).isTrue();
        }

        @Test
        void testIsEmptyShouldReturnFalse() {
            noArgsQueue.add(1);

            assertThat(noArgsQueue.isEmpty()).isFalse();
        }

    }

    @Nested
    class TestToStringMethod {

        @Test
        void testToStringShouldReturnExpectedStringIfQueueIsEmpty() {
            String expectedString = "[]";

            assertThat(noArgsQueue).hasToString(expectedString);
        }

        @Test
        void testToStringShouldReturnExpectedStringIfQueueContainsElements() {
            Stream.of("Baby", "Auto", "Sinister")
                    .forEach(noArgsQueue::add);
            String expectedString = "[Auto, Baby, Sinister]";

            assertThat(noArgsQueue).hasToString(expectedString);
        }

    }

}

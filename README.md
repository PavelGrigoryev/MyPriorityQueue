# MyPriorityQueue (собственная реализация java.util.PriorityQueue)

## Автор: [Grigoryev Pavel](https://pavelgrigoryev.github.io/GrigoryevPavel/)

***

### Unit тесты

1. Покрытие тестов 100%
2. Вы можете запустить тесты для этого проекта, выполнив в корне проекта: `./gradlew test`

***

### Пример работы с MyPriorityQueue:

`````java
import java.util.Comparator;

public class Pain {

    public static void main(String[] args) {
        MyPriorityQueue<Integer> noArgsQueue = new MyPriorityQueueImpl<>();
        MyPriorityQueue<Integer> withComparatorQueue = new MyPriorityQueueImpl<>(Comparator.reverseOrder());
        MyPriorityQueue<Integer> withCapacityQueue = new MyPriorityQueueImpl<>(12);
        MyPriorityQueue<Integer> withCapacityAndComparatorQueue = new MyPriorityQueueImpl<>(12, Comparator.reverseOrder());

        withComparatorQueue.add(10);
        withComparatorQueue.add(20);

        System.out.println(withComparatorQueue.poll()); // 20
        System.out.println(withComparatorQueue.peek()); // 10
        System.out.println(withComparatorQueue.size()); // 1
        System.out.println(withComparatorQueue.isEmpty()); // false
        System.out.println(withComparatorQueue.toString()); // [10]
    }

}
`````

***

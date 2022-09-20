import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {

    /**
     * - 데이터를 저장하는 컬렉션이 아님
     * - 데이터 소스(원본)를 변경하지 않음
     * - 스트림 파이프라인
     *      - 다수의 intermediate operation + 1개의 terminal operation
     *      - intermediate operation : 리턴 타입이 stream
     *      - terminal operation : 리턴 타입이 stream이 아님
     * - intermidiate operation은 lazy함
     *      - terminal operation이 와야 실행됨
     *      - 없으면 아무 일도 일어나지 않음
     * - 병렬 처리를 손쉽게 할 수 있음 (parallelStream)
     *      - 병렬처리를 한다고 반드시 빠른 것은 아니다!
     */

    @Test
    void basic() {
        ArrayList<String> words = new ArrayList<>();
        words.add("apple");
        words.add("banana");
        words.add("pineapple");
        words.add("peach");
        words.add("pizza");
        words.add("pen");
        words.add("pi");

        List<Integer> result = words.stream()
                .filter(s -> s.startsWith("p"))
                .map(s -> s.length())
                .limit(4)   // 4개까지만
                .skip(1)         // 맨 앞 1개 스킵
                .sorted(Comparator.comparingInt(o -> o))
                .collect(Collectors.toList());

        System.out.println("result = " + result);

        Optional<String> p = words.stream()
                .filter(s -> s.startsWith("p"))
                .findFirst();

        System.out.println(p.get());
    }

    @Test
    void flatMap() {
        ArrayList<Integer>[] adjList = new ArrayList[5];

        for (int i = 0; i < 5; i++) {
            adjList[i] = new ArrayList<>();

            for (int j = 0; j < i + 1; j++) {
                adjList[i].add(j + 1);
            }
        }

        Arrays.stream(adjList)
                .flatMap((list) -> list.stream())
                .forEach(System.out::println);
    }

    @Test
    void generate() {
        Stream<String> generated = Stream.generate(() -> "hi");

        generated
                .limit(10)
                .forEach(System.out::println);

        Stream.iterate("hi", s -> s + "everyone")
                .limit(5)       // 위의 동작을 5번 반복함
                .forEach(System.out::println);
    }

    @Test
    void parallel() {
        Stream<Integer> numbers = Stream.iterate(0, n -> n + 1)
                .limit(1000);

        long s = System.currentTimeMillis();
        // 천만가지 키웠는데도 단일 쓰레드가 더 빠름... 그럼 병렬처리는 언제 사용하는 걸까?
//        numbers.parallel()
//                .map(n -> n * 2)
//                .forEach(n -> System.out.println("thread name = " + Thread.currentThread().getName() + ", result = " + n));

        numbers
                .map(n -> n*2)
                .forEach(n -> System.out.println("thread name = " + Thread.currentThread().getName() + ", result = " + n));

        long e = System.currentTimeMillis();
        System.out.println("time : " + (e - s) + "mills");

    }
}

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

public class InterfaceDefaultMethod {

    @Test
    void iterable_default_메소드() {
        List<String> test = Arrays.asList(new String[]{"apple", "samsung", "google", "LG"});

        // 1. forEach
//        test.forEach(System.out::println);

        // 2. spliterator ->
        Spliterator<String> spliterator1 = test.spliterator();
        Spliterator<String> spliterator2 = spliterator1.trySplit();
        while (spliterator1.tryAdvance(System.out::println)) ;
        System.out.println("===========================");
        while (spliterator2.tryAdvance(System.out::println)) ;
    }

    @Test
    void Collection_default_메소드() {
        // 1. stream()

        //List<String> test = Arrays.asList(new String[]{"apple", "samsung", "google", "LG", "abc"});
        // Arrays.asList 리턴 타입 = List<String>
        // -> 추가, 삭제 시 UnsupportedOperationException 에러 발생

        List<String> test = new ArrayList<>();
        test.add("apple");
        test.add("samsung");
        test.add("google");
        test.add("LG");
        test.add("abc");

        List<String> result1 = test.stream()
                .filter(s -> s.startsWith("a"))
                .collect(Collectors.toList());

//        System.out.println("result = " + result1);

        // 2. parallelStream()

        // 3. removeIf(Predicate)
        test.removeIf(s -> s.startsWith("a"));
        System.out.println("test = " + test);
    }

    @Test
    void Comparator_default_메소드() {
        List<String> test = new ArrayList<>();
        test.add("apple");
        test.add("samsung");
        test.add("google");
        test.add("LG");
        test.add("bcd");
        test.add("abc");

        // reversed & thenComparing
        Comparator<String> compareToIgnoreCase = String::compareToIgnoreCase;
        test.sort(Comparator.comparingInt(String::length).reversed().thenComparing(String::compareToIgnoreCase));
        System.out.println("test = " + test);
    }
}

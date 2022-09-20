import functional_interface.MyFunctionalInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.*;

import static org.assertj.core.api.Assertions.assertThat;

public class FunctionalInterfaceAndLambdaTest {

    @Test
    void 익명_객체() {
        MyFunctionalInterface anonymousObject = new MyFunctionalInterface() {
            @Override
            public void hi() {
                System.out.println("hi");
            }
        };

        anonymousObject.hi();
    }

    @Test
    void 람다() {
        MyFunctionalInterface lambdaObject = () -> System.out.println("hi");

        lambdaObject.hi();
    }

    @Test
    void variable_capture() {
        /**
         * effective final
         * - 변경되지 않는 사실상 final인 변수
         * - 로컬 클래스, 익명 클래스, 람다에서 사용 가능
         * - 로컬, 익명 클래스에서는 쉐도윙 가능
         *      -> 클래스에서 해당 변수명으로 변수를 선언하면 무시됨
         * - 람다는 쉐도윙 불가
         *      -> 스코프가 같기 때문에
         */
        int number = 10;

        // 로컬 클래스
        class LocalClass {
            void method() {
                int number = 11;

                System.out.println("LocalClass.method");
                System.out.println("number = " + number);   // 11 출력
                // -> 메소드 내에서 number를 정의하지 않으면 클래스 밖의 number를 참조함
            }
        }
        new LocalClass().method();

        // 익명 클래스
        MyFunctionalInterface anonymousObject = new MyFunctionalInterface() {
            @Override
            public void hi() {  // 매개변수로 number를 받아도 쉐도윙 됨
                int number = 11;
                System.out.println("FunctionalInterfaceAndLambdaTest.hi");
                System.out.println("number = " + number);
            }
        };
        anonymousObject.hi();

        // 람다 -> 쉐도윙하지 않음
        MyFunctionalInterface lambdaObject = () -> {
            // 재정의 시도 시 컴파일 에러 -> 같은 스코프 내에서 동일한 변수명을 사용할 수 없으므로
            //int number = 11;
            System.out.println("number = " + number);
        };
        lambdaObject.hi();
    }

    @Test
    void function() {
//        Function<Integer, String> function = new Function<Integer, String>() {
//            @Override
//            public String apply(Integer number) {
//                return "hi " + number;
//            }
//        };

        Function<Integer, String> function = (number) -> "hi " + number;

        assertThat(function.apply(10)).isEqualTo("hi 10");
    }

    @ParameterizedTest
    @CsvSource({"1,2", "3,5"})
    void BiFunction(Integer param1, Integer param2) {     // Function의 입력값만 2개인 버전
//        BiFunction<Integer, Integer, String> biFunction = new BiFunction<Integer, Integer, String>() {
//            @Override
//            public String apply(Integer n1, Integer n2) {
//
//                return n1 + " and " + n2;
//            }
//        };

        BiFunction<Integer, Integer, String> biFunction = (n1, n2) -> n1 + " and " + n2;

        assertThat(biFunction.apply(param1, param2)).isEqualTo(param1 + " and " + param2);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void consumer(Integer param) {   // input만 있음
        Consumer<Integer> consumer = (number) -> {
            System.out.println(number + " + " + number + " = " + number * 2);
        };

        consumer.accept(param);
    }

    @Test
    void supplier() {   // output만 있음
        Supplier<Integer> supplier = () -> 1000;

        assertThat(supplier.get()).isEqualTo(1000);
    }

    @Test
    void predicate() {  // 입력값을 보고 boolean을 리턴
//        Predicate<Integer> predicate = new Predicate<Integer>() {
//            @Override
//            public boolean test(Integer number) {
//                return number > 5 ? true : false;
//            }
//        };

        Predicate<Integer> predicate = (number) -> number > 5;

        assertThat(predicate.test(3)).isFalse();
        assertThat(predicate.test(7)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void unaryOperator(Integer param) {
        UnaryOperator<Integer> unaryOperator = (number) -> number * 3;

        assertThat(unaryOperator.apply(param)).isEqualTo(param * 3);
    }

    @ParameterizedTest
    @CsvSource({"1,2", "2,4"})
    void binaryOperator(Integer param1, Integer param2) {
        BinaryOperator<Integer> binaryOperator = (n1, n2) -> n1 + n2;

        assertThat(binaryOperator.apply(param1, param2)).isEqualTo(param1 + param2);
    }

    /**
     * 메소드 레퍼런스
     * 람다가 하는 일이 메소드 호출, 생성자 호출인 경우 간단하게 표현할 수 있음
     * - 메소드 참조하는 방법
     * 1. static 메소드 참조
     * 2. 특정 객체의 인스턴스 메소드 참조
     * 3. 임의 객체의 인스턴스 메소드 참조
     * 4. 생성자 참조
     */
    @Test
    void static_메소드_참조() {
//        Function<Integer, String> function = (number) -> TestClass.staticMethod(number);

        Function<Integer, String> function = TestClass::staticMethod;

        System.out.println(function.apply(1));
    }

    @Test
    void instance_메소드_참조() {
        TestClass testClass = new TestClass();

//        Function<Integer, String> function = (number) -> testClass.instanceMethod(number);
        Function<Integer, String> function = testClass::instanceMethod;

        System.out.println(function.apply(1));
    }

    @Test
    void 임의_객체_메소드_참조() {
        String[] words = {"b", "a", "d", "c"};

//        Arrays.sort(words, new Comparator<String>() {
//            @Override
//            public int compare(String s1, String s2) {
//                return s1.compareToIgnoreCase(s2);
//            }
//        });

//        Arrays.sort(words, (s1, s2) -> s1.compareToIgnoreCase(s2));

        Arrays.sort(words, String::compareToIgnoreCase);

        Arrays.stream(words).forEach(System.out::println);
    }

    @Test
    void 생성자_참조() {
//        Supplier<TestClass> basicConstructor = new Supplier<TestClass>() {
//            @Override
//            public TestClass get() {
//                return new TestClass();
//            }
//        };

//        Supplier<TestClass> basicConstructor = () -> new TestClass();

        Supplier<TestClass> basicConstructor = TestClass::new;

//        Function<Integer, TestClass> paramConstructor = new Function<Integer, TestClass>() {
//            @Override
//            public TestClass apply(Integer param) {
//                return new TestClass(param);
//            }
//        };

//        Function<Integer, TestClass> paramConstructor = (param) -> new TestClass(param);

        Function<Integer, TestClass> paramConstructor = TestClass::new;

        // basicConstructor와 paramConstructor의 생성자는 서로 다름!

        TestClass obj1 = basicConstructor.get();
        TestClass obj2 = paramConstructor.apply(1);
    }
}

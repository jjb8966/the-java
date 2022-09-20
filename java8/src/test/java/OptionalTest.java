import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.*;

public class OptionalTest {

    /**
     * Optional
     * - 오직 1개의 값이 들어있을 수도 없을 수도 있는 컨테이너
     * - 리턴값으로만 쓰는 것을 권장 (매개변수, 필드 타입... X)
     * - primitive type용 Optional은 따로 있음
     * - OptionalInt, OptionalLong...
     * - 컨테이너 성격의 타입은 Optional로 감싸지 말 것 (Collection, Map, Stream, Optional...)
     * <p>
     * Optional을 사용하는 이유
     * - 메소드의 리턴값으로 null이 넘어올 수 있는 경우
     * - null 체크를 하지 않고 사용하면 NPE가 발생할 수 있음
     * - 개발자가 깜빡하기 쉬움
     * <p>
     * 메소드에서 값을 제대로 리턴할 수 없는 경우
     * 1. 예외를 던짐
     * - 스택 트레이스를 찍어야 해서 비용이 비쌈
     * 2. null 리턴
     * - 메소드를 사용하는 사용자가 NPE 안나도록 주의해서 사용해야 함
     * 3. Optional 리턴
     * - 명시적으로 빈 값일 수 있음을 알려줌
     * - 빈 값인 경우에 대한 처리를 강제할 수 있음
     */

    @Test
    void optional_사용x() {
        String result1 = noUseOptional(1);
        String result2 = noUseOptional(3);

//        result2.compareToIgnoreCase(result1);   // NPE 발생

        System.out.println("result1 = " + result1);
        System.out.println("result2 = " + result2);
    }

    @Test
    void optional_사용o() {
        Optional<String> optional1 = useOptional(1);
        Optional<String> optional2 = useOptional(3);

        String result1 = optional1.orElse("this is null");
        String result2 = optional2.orElse("this is null");

        result2.compareToIgnoreCase(result1);

        optional1.ifPresent(s -> System.out.println("s = " + s));
        optional2.ifPresentOrElse(s -> System.out.println("s = " + s), () -> System.out.println("아무것도 없음"));

        assertThat(optional1.isPresent()).isTrue();
        assertThat(optional2.isEmpty()).isTrue();
    }

    @Test
    void orElse_vs_orElseGet() {
        Optional<String> optional = useOptional(2);
//        Optional<String> optional = useOptional(3);

        // optional에 값이 있던지 없던지 메소드가 실행됨
        // -> 상수 리턴할 때 사용
        String result1 = optional.orElse(createString());

        // optional에 값이 있어야지만 메소드가 실행됨
        // -> 값이 없을 때 동적으로 무언가 만들어서 리턴할 때 사용
        String result2 = optional.orElseGet(() -> createString());

        System.out.println("result1 = " + result1);
        System.out.println("result2 = " + result2);
    }

    @Test
    void orElseThrow() {
        Optional<String> optional = useOptional(3);

        assertThatThrownBy(() -> optional.orElseThrow(() -> new RuntimeException()))
                .isInstanceOf(RuntimeException.class);
    }

    private String createString() {
        System.out.println("OptionalTest.createString");
        return "hi";
    }

    private String noUseOptional(int i) {
        if (i == 1) {
            return "good";
        }

        if (i == 2) {
            return "bad";
        }

        return null;
    }

    private Optional<String> useOptional(int i) {
        if (i == 1) {
            return Optional.ofNullable("good");
        }

        if (i == 2) {
            return Optional.ofNullable("bad");
        }

        return Optional.empty();
    }
}

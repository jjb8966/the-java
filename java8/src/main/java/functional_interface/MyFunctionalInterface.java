package functional_interface;

@FunctionalInterface
public interface MyFunctionalInterface {

    void hi();

//    Functional Interface는 하나의 추상 메소드만 가질 수 있음. default, static 메소드는 가질 수 있음
//    void bye();

    /**
     * default 메소드
     * - 인터페이스에 추상 메소드가 아닌 구현된 메소드를 제공하는 방법
     * - 해당 인터페이스를 구현한 클래스를 깨뜨리지 않고 새 기능을 추가할 수 있음
     * - 구현체가 추가된 메소드를 모를 수도 있으므로 반드시 문서화를 해야함 (@implSpec)
     * - Object 메소드는 기본 메소드로 사용할 수 없음
     *      -> 구현체에서 오버라이딩해서 사용해야함
     * - 구현체에서 사용하고 싶지 않은 경우 다시 추상 메소드로 선언해도 되고 오버라이딩해서 사용해도 됨
     */
    default void printName() {
        System.out.println("name");
    }

    /**
     * static 메소드
     * - 해당 타입 관련 헬퍼 또는 유틸리티 메소드를 제공하는 방법
     */
    static void always() {
        System.out.println("always");
    }
}

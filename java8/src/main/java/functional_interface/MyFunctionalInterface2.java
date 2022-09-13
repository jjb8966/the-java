package functional_interface;

@FunctionalInterface
public interface MyFunctionalInterface2 {

    void hi();

    // MyFunctionalInterface에 존재하는 default 메소드
    default void printName() {
        System.out.println("name");
    }
}

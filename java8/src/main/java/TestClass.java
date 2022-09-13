public class TestClass {

    private int number;

    public TestClass() {
        System.out.println("기본 생성자 호출");
    }

    public TestClass(int number) {
        System.out.println("숫자를 매개변수로하는 생성자 호출");
        this.number = number;
    }

    public String instanceMethod(int param) {
        return "instance method " + param;
    }

    public static String staticMethod(int param) {
        return "static method " + param;
    }
}

package functional_interface;

public class ImplementClass implements MyFunctionalInterface, MyFunctionalInterface2{

    @Override
    public void hi() {

    }

    // default 메소드임에도 불구하고 반드시 오버라이딩 해줘야 함
    // -> 구현하려는 인터페이스 둘 다 해당 메소드를 default 메소드로 가지고 있으므로
    @Override
    public void printName() {
        MyFunctionalInterface.super.printName();
    }
}

package demo;

public class Student implements Person {
    @Override
    public void hello(String name) {
        System.out.println("学生："+name);
    }


}

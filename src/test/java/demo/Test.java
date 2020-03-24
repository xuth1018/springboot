package demo;

import java.lang.reflect.Proxy;

public class Test {
    public static void main(String[] args) {
        Person person = (Person) Proxy.newProxyInstance(
                Student.class.getClassLoader(),Student.class.getInterfaces(),new MyInvocationHandler(new Student()));
        person.hello("fs");

    }
}

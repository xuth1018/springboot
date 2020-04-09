package code;

public class ThreadLocalT {
    public static void main(String[] args) {
        ThreadLocal<String> t = ThreadLocal.withInitial(()->"22");
        t.set("sdsaf");
        t.set("sf");
        System.out.println(t.get());
        t.remove();
        System.out.println(t.get());

    }
}

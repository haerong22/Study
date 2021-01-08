package Adapter;

public class Main {
    public static void main(String[] args) {
        Adapter adapter = new AdapterImpl();

        System.out.println(adapter.halfOf(100f));
        System.out.println(adapter.twiceOf(10f));
    }
}

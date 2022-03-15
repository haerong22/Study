package _01_lambda;

public interface ExInterface {

    void getMsg();

    default String defaultMethod() {
        return "default method";
    }

    static void print(String msg) {
        System.out.println("msg = " + msg);
    }
}

package lambda.ex1;

public class M1After {

    public static void greet(String message) {
        System.out.println("=== 시작 ===");
        System.out.println(message);
        System.out.println("=== 끝 ===");
    }

    public static void main(String[] args) {
        greet("Good Morning");
        greet("Good Afternoon");
        greet("Good Evening");
    }

    /*
        === 시작 ===
        Good Morning
        === 끝 ===
        === 시작 ===
        Good Afternoon
        === 끝 ===
        === 시작 ===
        Good Evening
        === 끝 ===
     */
}

package updated;

public class Clamp {

    public static void main(String[] args) {

        int result = Math.clamp(10, 5, 15); // 10
        int result2 = Math.clamp(2, 5, 15); // 5
        int result3 = Math.clamp(20, 5, 15); // 15

        System.out.println("result = " + result);
        System.out.println("result2 = " + result2);
        System.out.println("result3 = " + result3);
    }
}

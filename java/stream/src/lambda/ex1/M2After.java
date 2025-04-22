package lambda.ex1;

public class M2After {

    public static void print(int weight, String unit) {
        System.out.println("무게: " + weight + unit);
    }

    public static void main(String[] args) {
        print(10, "kg");
        print(50, "kg");
        print(200, "g");
        print(40, "g");
    }

    /*
        무게: 10kg
        무게: 50kg
        무게: 200g
        무게: 40g
     */
}

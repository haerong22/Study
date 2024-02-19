public class Java07 {
    public static void main(String[] args) {

        int a = 20;
        float b = 56.7f;

        // call by value
        float v = sum(a, b);
        System.out.println(v);

        int[] arr = {1, 2, 3, 4, 5};

        // call by reference
        int vv = arrSum(arr);
        System.out.println(vv);
    }

    public static float sum(int a, float b) {
        return a + b;
    }

    public static int arrSum(int[] arr) {
        int sum = 0;
        for (int i : arr) {
            sum += i;
        }
        return sum;
    }
}

public class Java06 {
    public static void main(String[] args) {
        // 메소드 -> 동작(method), 기능(function)
        int a = 67;
        int b = 98;

        int result = sum(a, b);
        System.out.println(result);

        int[] arr = makeArr();
        int hap = 0;
        for (int i = 0; i < arr.length; i++) {
            hap += arr[i];
        }
        System.out.println(hap);
    }

    // 정수 2개를 매개변수로 받아서 합을 리턴하는 메소드를 정의하시오.
    // static 메소드에서는 static 메소드만 호출 가능
    public static int sum(int a, int b) {
        return a + b;
    }

    // 값은 하나만 리턴이 가능하다.
    // 여러 값을 리턴 받고 싶다면? -> 배열
    public static int[] makeArr() {
        int[] arr = new int[3];
        arr[0] = 10;
        arr[1] = 20;
        arr[2] = 30;
        return arr;
    }
}

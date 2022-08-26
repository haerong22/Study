public class Java04 {

    public static void main(String[] args) {
        // 변수 VS 배열
        int a = 10, b = 20, c = 30;

        // a + b + c = ?  메소드 처리 -> hap()
        hap(a, b, c);

        int[] arr = new int[3];
        arr[0] = 10;
        arr[1] = 20;
        arr[2] = 30;

        hap(arr);
    }

    // 하나의 값마다 파라미터로 받아야함 -> 데이터 이동이 불편
    public static void hap(int a, int b, int c) {
        int sum = a + b + c;
        System.out.println(sum);
    }

    // 여러 값을 배열로 한번에 이동
    public static void hap(int[] arr) {
        int sum = 0;
        for (int i : arr) {
            sum += i;
        }
        System.out.println(sum);
    }
}

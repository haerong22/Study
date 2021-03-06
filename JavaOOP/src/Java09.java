public class Java09 {
    public static void main(String[] args) {
        int a = 56;
        int b = 40;

        Java09 java09 = new Java09(); // heap area 에 객체 생성
        int v = java09.add(a, b);
        System.out.println(v);
    }

    public int add(int a, int b) {
        int sum = a + b;
        return sum;
    }
}

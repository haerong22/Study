package lambda.ex1;

import lambda.Procedure;

import java.util.Arrays;

/*
    [1부터 100 까지의 합] 결과: 4950
    실행 시간: 8536708ns
    원본 배열: [4, 3, 2, 1]
    배열 정렬: [1, 2, 3, 4]
    실행 시간: 430417ns
 */
public class M3MeasureTime {

    public static void measure(Procedure p) {
        long starNs = System.nanoTime();
        p.run();
        long endNs = System.nanoTime();
        System.out.println("실행 시간: " + (endNs - starNs) + "ns");
    }

    public static void main(String[] args) {

        measure(new Procedure() {
            @Override
            public void run() {
                int N = 100;
                long sum = 0;
                for (int i = 0; i < N; i++) {
                    sum += i;
                }
                System.out.println("[1부터 " + N + " 까지의 합] 결과: " + sum);
            }
        });

        measure(new Procedure() {
                    @Override
                    public void run() {
                        int[] arr = {4, 3, 2, 1};
                        System.out.println("원본 배열: " + Arrays.toString(arr));
                        Arrays.sort(arr);
                        System.out.println("배열 정렬: " + Arrays.toString(arr));
                    }
                }
        );
    }
}

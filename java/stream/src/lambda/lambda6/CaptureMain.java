package lambda.lambda6;

public class CaptureMain {

    public static void main(String[] args) {

        final int final1 = 10; // 명시적 불변
        int final2 = 20; // 사실상 불변, 재할당 없음
        int changedVar = 30; // 값이 변경

        // 익명 클래스에서의 캡처
        Runnable anonymous = new Runnable() {
            @Override
            public void run() {
                System.out.println("익명클래스 - final1: " + final1);
                System.out.println("익명클래스 - final2: " + final2);
                // 컴파일 오류
//                System.out.println("익명클래스 - changedVar: " + changedVar);
            }
        };

        // 람다 표현식에서의 캡처
        Runnable lambda = () -> {
            System.out.println("람다 - final1: " + final1);
            System.out.println("람다 - final2: " + final2);
            // 컴파일 오류
//            System.out.println("람다 - changedVar: " + changedVar);
        };

        changedVar++;

        anonymous.run();
        lambda.run();
    }
    /*
        익명클래스 - final1: 10
        익명클래스 - final2: 20
        람다 - final1: 10
        람다 - final2: 20
     */
}

package lambda.lambda1;

public class SamMain {

    public static void main(String[] args) {
        SamInterface samInterface = () -> {
            System.out.println("sam");
        };

        samInterface.run();

        // 컴파일 오류 - 단일 추상 메소드를 가진 인터페이스만 람다 사용 가능
//        NotSamInterface notSamInterface = () -> {
//            System.out.println("not");
//        }
    }
}

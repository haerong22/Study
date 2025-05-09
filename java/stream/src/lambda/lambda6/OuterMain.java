package lambda.lambda6;

public class OuterMain {

    private String message = "외부 클래스";

    public void execute() {
        // 익명 클래스
        Runnable annonymous = new Runnable() {

            private String message = "익명 클래스";

            @Override
            public void run() {
                // 익명 클래스에서의 this 는 익명 클래스의 인스턴스를 가리킴
                System.out.println("[익명 클래스] this: " + this);
                System.out.println("[익명 클래스] this.class: " + this.getClass());
                System.out.println("[익명 클래스] this.message: " + this.message);
            }
        };

        // 람다
        Runnable lambda = () -> {
            // 람다에서의 this 는 람다가 선언된 클래스의 인스턴스를 가리킴
            System.out.println("[람다] this: " + this);
            System.out.println("[람다] this.class: " + this.getClass());
            System.out.println("[람다] this.message: " + this.message);
        };

        annonymous.run();
        System.out.println("=================================");
        lambda.run();
    }

    public static void main(String[] args) {
        OuterMain outer = new OuterMain();
        System.out.println("[외부 클래스]: " + outer);
        System.out.println("=================================");
        outer.execute();
    }
    /*
        [외부 클래스]: lambda.lambda6.OuterMain@6acbcfc0
        =================================
        [익명 클래스] this: lambda.lambda6.OuterMain$1@10f87f48
        [익명 클래스] this.class: class lambda.lambda6.OuterMain$1
        [익명 클래스] this.message: 익명 클래스
        =================================
        [람다] this: lambda.lambda6.OuterMain@6acbcfc0
        [람다] this.class: class lambda.lambda6.OuterMain
        [람다] this.message: 외부 클래스
     */
}

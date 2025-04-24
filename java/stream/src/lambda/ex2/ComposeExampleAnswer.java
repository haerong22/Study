package lambda.ex2;

import lambda.MyTransformer;

public class ComposeExampleAnswer {

    public static MyTransformer compose(MyTransformer f1, MyTransformer f2) {
        return s -> {
            String intermediate = f1.transform(s);
            return f2.transform(intermediate);
        };

    }

    public static void main(String[] args) {
        // 대문자 변환
        MyTransformer toUpper = s -> s.toUpperCase();

        // 앞 뒤에 **
        MyTransformer addDeco = s -> "**" + s + "**";

        // 합성
        MyTransformer compose = compose(toUpper, addDeco);

        // 실행
        String result = compose.transform("hello");
        System.out.println(result);
    }

    /*
        **HELLO**
     */

}

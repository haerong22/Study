import object.PrivateConstructor;

public class Java12 {

    public static void main(String[] args) {

//        // 생성자가 private 이므로 객체 생성 불가
//        PrivateConstructor pc = new PrivateConstructor();
//
//        // non-static-method 사용 불가
//        PrivateConstructor.nonStaticMethod();

        // static-method 만 사용가능
        PrivateConstructor.staticMethod();
    }
}

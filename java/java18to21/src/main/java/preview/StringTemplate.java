package preview;

public class StringTemplate {

    public static void main(String[] args) {

        String name = "홍길동";
        int age = 20;

        String str = STR."이름: \{name} 나이: \{age}";

        System.out.println(str);
    }
}

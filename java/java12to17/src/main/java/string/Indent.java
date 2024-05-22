package string;

public class Indent {

    public static void main(String[] args) {

        String str = """
                A
                BC
                DEF
                """.indent(3);

        System.out.println(str);
    }
}

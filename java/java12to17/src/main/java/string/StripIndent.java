package string;

public class StripIndent {

    public static void main(String[] args) {

        String str = "  A\n  B\n C";

        System.out.println(str.stripIndent());
    }
}

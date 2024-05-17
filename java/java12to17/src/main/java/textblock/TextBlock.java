package textblock;

public class TextBlock {

    public static void main(String[] args) {

        /*
            A
            BC
            DEF
         */
        String str1 = "A\nBC\nDEF";

        String str2 = "A\n" +
                "BC\n" +
                "DEF";

        String textBlock = """
                A
                BC
                DEF""";

        System.out.println("str1 = " + str1);
        System.out.println("str2 = " + str2);
        System.out.println("textBlock = " + textBlock);
        System.out.println(str1.equals(textBlock));

        /**
         * 뒷 공백 사용
         */
        String textBlock1 = """
                A$$
                BC$
                DEF
                """.replace('$', ' ');

        String textBlock2 = """
                A  |
                BC |
                DEF|
                """.replace("|\n", "\n");

        // `octal escape sequence` 사용
        String textBlock3 = """
                A\040\040
                BC\040
                DEF
                """;

        // \s 사용 - 공백을 의미
        String textBlock4 = """
                A \s
                BC\s
                DEF
                """;

        System.out.println("textBlock1 = " + textBlock1);
        System.out.println("textBlock2 = " + textBlock2);
        System.out.println("textBlock3 = " + textBlock3);
        System.out.println("textBlock4 = " + textBlock4);

        /**
         * line terminator
         */
        String textBlock5 = """
                A  \
                BC \
                DEF
                """;

        System.out.println("textBlock5 = " + textBlock5);
    }
}

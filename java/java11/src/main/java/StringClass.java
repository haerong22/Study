public class StringClass {

    private void strip() {
        String str = "    A BC   ";
        System.out.println(str.strip()); // "A BC"
    }

    private void isBlank() {
        String str1 = "A";
        System.out.println(str1.isBlank()); // false

        String str2 = "   ";
        System.out.println(str2.isBlank()); // true
    }

    private void lines() {
        String str = "A\nB";

        str.lines().forEach(System.out::println);
    }

    private void repeat() {
        String str = "A ";
        System.out.println(str.repeat(3)); // "A A A "
    }
}

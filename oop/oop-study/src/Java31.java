public class Java31 {
    public static void main(String[] args) {
        String str = "apple"; // java.lang.String str = new java.lang.String("apple");

        // String 클래스의 다양한 메소드들
        System.out.println(str.toUpperCase());
        System.out.println(str.charAt(3));
        System.out.println(str.length());
        System.out.println(str.indexOf("pl"));
        System.out.println(str.indexOf("px"));
        System.out.println(str.replaceAll("p", "x"));
    }
}

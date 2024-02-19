public class Java32 {
    public static void main(String[] args) {

        // new 로 생성
        String str1 = new String("APPLE");
        String str2 = new String("APPLE");

        // str1과 str2는 객체 이므로 heap 영역에 저장된 번지수가 저장됨
        // new 로 생성하면 각각 다른 객체를 생성하여 가리키므로 다름
        System.out.println(str1 == str2);

        // 번지수가 아닌 값을 비교 하려면 equals 사용
        System.out.println(str1.equals(str2));

        // 문자열 상수로 생성
        String str3 = "APPLE";
        String str4 = "APPLE";

        System.out.println(str3 == str4);
    }
}

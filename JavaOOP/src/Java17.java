import com.google.gson.Gson;
import object.BookVO;
import object.MyUtil;

public class Java17 {
    public static void main(String[] args) {

        // 1. Java 에서 제공해주는 class 들 -> API
        String str = new String("apple");
        // new String 생략 가능
        String str1 = "apple";
        System.out.println(str1.toUpperCase()); // 소문자 -> 대문자 변환 메소드

        // 2. 직접 만들어서 사용하는 class 들 -> DTO/VO, DAO, Utility ... API
        MyUtil my = new MyUtil();
        int sum1to10 = my.hap();
        System.out.println(sum1to10);

        // 3. 다른 회사(사람) 에서 만들어 놓은 class
        // Gson 사용해보기 : object -> json 형태로 바꿔주는 API
        Gson g = new Gson();
        BookVO vo = new BookVO("자바", 13000, "출판사", 800);
        String json = g.toJson(vo);

        System.out.println(json);
    }
}


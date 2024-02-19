import poly.Radio;
import poly.RemoCon;
import poly.TV;

public class Java24 {
    public static void main(String[] args) {
        // 인터페이스
        // 객체 생성 X : RemoCon r = new RemoCon(); (X)

        RemoCon r = new TV();
        r.chUp();
        r.chDown();
        r.internet();

        r = new Radio();
        r.chUp();
        r.chDown();
        r.internet();
    }
}

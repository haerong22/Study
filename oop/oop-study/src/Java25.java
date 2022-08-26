import poly.Radio;
import poly.RemoCon;
import poly.TV;

public class Java25 {
    public static void main(String[] args) {
        RemoCon r = new TV();
        for (int i = 0; i < 10; i++) {
            r.chUp();
        }
        for (int i = 0; i < 10; i++) {
            r.chDown();
        }
        r.internet();
    }
}

package poly;

public interface RemoCon { // 객체 생성 X : RemoCon r = new RemoCon(); (X)

    // 상수를 사용가능 ( public static final 생략 )
    int MAXCH = 100;
    int MINCH = 1;

    // public abstract 생략
    void chUp();
    void chDown();
    void internet();
}

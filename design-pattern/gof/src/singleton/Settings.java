package singleton;

import java.io.Serializable;

public class Settings implements Serializable { // 직렬화

    /**
     * Thread Safe 하게 Singleton 구현하기
     * 1. synchronized 키워드 사용
     *    매번 동기화 과정에서 리소스를 사용하므로 성능저하의 원인이 될 수 있다.
     */
//    private static Settings instance;
//
//    private Settings() {}
//
//    public static synchronized Settings getInstance() {
//        if (instance == null) {
//            instance = new Settings();
//        }
//        return instance;
//    }

    /**
     * Thread Safe 하게 Singleton 구현하기
     * 2. 클래스 로딩시 초기화 (eager initialization)
     *    인스턴스를 만드는 과정이 리소스가 많이 든다면 자주 사용하지 않는 경우 낭비가 될 수 있다.
     */
//    private static final Settings INSTANCE = new Settings();
//
//    private Settings() {}
//
//    public static Settings getInstance() {
//        return INSTANCE;
//    }

    /**
     * Thread Safe 하게 Singleton 구현하기
     * 3. double checked locking (java 1.5 이상)
     *    인스턴스가 있는 경우에는 synchronized 블럭을 사용하지 않기 때문에
     *    메소드에 synchronized 를 사용한 것과 다르다.
     */
//    private static volatile Settings instance;
//
//    private Settings() {}
//
//    public static Settings getInstance() {
//        if (instance == null) {
//            synchronized (Settings.class) {
//                if (instance == null) {
//                    instance = new Settings();
//                }
//            }
//        }
//        return instance;
//    }

    /**
     * Thread Safe 하게 Singleton 구현하기
     * 4. static inner class 사용
     *    SettingsHolder.INSTANCE 가 호출 될 때 인스턴스가 생성 된다. (lazy loading)
     */

    private Settings() {}

    private static class SettingsHolder {
        private static final Settings INSTANCE = new Settings();
    }

    public static Settings getInstance() {
        return SettingsHolder.INSTANCE;
    }

    // 역직렬화 시 기본은 인스턴스를 생성하지만 readResolve()가 있으면
    // 이 메소드를 사용하게 된다.
    protected Object readResolve() {
        return getInstance();
    }
}

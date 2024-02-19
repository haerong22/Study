package singleton;

// enum 으로 구현하면 간단하게 해결 가능하다.
// enum 은 Serializable 을 상속받고 있다.
// eager loading
// 상속을 받지 못한다.
public enum SettingsEnum {

    INSTANCE;

}

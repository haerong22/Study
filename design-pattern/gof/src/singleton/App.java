package singleton;

import java.io.*;
import java.lang.reflect.Constructor;

public class App {
    public static void main(String[] args) throws Exception {

        Settings instance = Settings.getInstance();
        Settings instance1 = Settings.getInstance();
        System.out.println(instance == instance1);

        // 싱글톤 패턴 구현 방법을 깨트리는 방법

        // 1. reflection
        Constructor<Settings> constructor = Settings.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        Settings instance2 = constructor.newInstance();
        System.out.println(instance == instance2);

        // 2. 직렬화, 역직렬화 (readResolve()로 해결)
        Settings instance3 = null;
        try (ObjectOutput out = new ObjectOutputStream(new FileOutputStream("settings.obj"))) {
            out.writeObject(instance);
        }
        try (ObjectInput in = new ObjectInputStream(new FileInputStream("settings.obj"))) {
            instance3 = (Settings) in.readObject();
        }
        System.out.println(instance == instance3);


        SettingsEnum instance4 = SettingsEnum.INSTANCE;

        // enum 타입은 reflection 으로 생성 불가
        SettingsEnum instance5 = null;
//        Constructor<?>[] declaredConstructors = SettingsEnum.class.getDeclaredConstructors();
//        for (Constructor<?> declaredConstructor : declaredConstructors) {
//            declaredConstructor.setAccessible(true);
//            instance5 = (SettingsEnum) declaredConstructor.newInstance("INSTANCE");
//        }
//        System.out.println(instance4 == instance5);

        SettingsEnum instance6 = null;
        try (ObjectOutput out = new ObjectOutputStream(new FileOutputStream("settings.obj"))) {
            out.writeObject(instance4);
        }
        try (ObjectInput in = new ObjectInputStream(new FileInputStream("settings.obj"))) {
            instance6 = (SettingsEnum) in.readObject();
        }

        System.out.println(instance4 == instance6);
    }
}

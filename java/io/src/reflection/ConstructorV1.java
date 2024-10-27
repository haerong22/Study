package reflection;

import java.lang.reflect.Constructor;

public class ConstructorV1 {

    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> aClass = Class.forName("reflection.data.BasicData");

        System.out.println("====== constructors() ======");
        Constructor<?>[] constructors = aClass.getConstructors(); // public 생성자
        for (Constructor<?> constructor : constructors) {
            System.out.println("constructor = " + constructor);
        }

        System.out.println("====== declaredConstructors() ======");
        Constructor<?>[] declaredConstructors = aClass.getDeclaredConstructors(); // 모든 생성자
        for (Constructor<?> constructor : declaredConstructors) {
            System.out.println("constructor = " + constructor);
        }
    }

    /*
        ====== constructors() ======
        constructor = public reflection.data.BasicData()
        ====== declaredConstructors() ======
        constructor = private reflection.data.BasicData(java.lang.String)
        constructor = public reflection.data.BasicData()
     */
}

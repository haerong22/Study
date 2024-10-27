package reflection;

import reflection.data.BasicData;

import java.lang.reflect.Modifier;
import java.util.Arrays;

public class BasicV2 {

    public static void main(String[] args) {
        Class<BasicData> basicData = BasicData.class;

        System.out.println("basicData.getName() = " + basicData.getName());
        System.out.println("basicData.getSimpleName() = " + basicData.getSimpleName());
        System.out.println("basicData.getPackage() = " + basicData.getPackage());

        System.out.println("basicData.getSuperclass() = " + basicData.getSuperclass());
        System.out.println("basicData.getInterfaces() = " + Arrays.toString(basicData.getInterfaces()));

        System.out.println("basicData.isInterface() = " + basicData.isInterface());
        System.out.println("basicData.isEnum() = " + basicData.isEnum());
        System.out.println("basicData.isAnnotation() = " + basicData.isAnnotation());

        int modifiers = basicData.getModifiers();
        System.out.println("basicData.getModifiers() = " + modifiers);
        System.out.println("isPublic() = " + Modifier.isPrivate(modifiers));
        System.out.println("Modifier.toString() = " + Modifier.toString(modifiers));
    }

    /*
        basicData.getName() = reflection.data.BasicData
        basicData.getSimpleName() = BasicData
        basicData.getPackage() = package reflection.data
        basicData.getSuperclass() = class java.lang.Object
        basicData.getInterfaces() = []
        basicData.isInterface() = false
        basicData.isEnum() = false
        basicData.isAnnotation() = false
        basicData.getModifiers() = 1
        isPublic() = false
        Modifier.toString() = public
     */
}

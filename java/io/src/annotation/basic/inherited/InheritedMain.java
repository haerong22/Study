package annotation.basic.inherited;

import java.lang.annotation.Annotation;

public class InheritedMain {

    public static void main(String[] args) {
        print(Parent.class);
        print(Child.class);
        print(TestInterface.class);
        print(TestInterfaceImpl.class);
    }

    private static void print(Class<?> clazz) {
        System.out.println("class: " + clazz);
        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println(" - " + annotation.annotationType().getSimpleName());
        }
        System.out.println();
    }

    /*
        class: class annotation.basic.inherited.Parent
         - InheritedAnnotation
         - NoInheritedAnnotation

        class: class annotation.basic.inherited.Child
         - InheritedAnnotation

        class: interface annotation.basic.inherited.TestInterface
         - InheritedAnnotation
         - NoInheritedAnnotation

        class: class annotation.basic.inherited.TestInterfaceImpl

     */
}

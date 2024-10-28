package annotation.basic;

import java.util.Arrays;

public class ElementData1Main {

    public static void main(String[] args) {
        Class<ElementData1> annoClass = ElementData1.class;
        AnnoElement annotation = annoClass.getAnnotation(AnnoElement.class);

        String value = annotation.value();
        System.out.println("value = " + value);

        int count = annotation.count();
        System.out.println("count = " + count);

        String[] tags = annotation.tags();
        System.out.println("tags = " + Arrays.toString(tags));
    }

    /*
        value = data
        count = 10
        tags = [t1, t2]
     */
}

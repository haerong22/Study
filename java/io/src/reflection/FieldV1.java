package reflection;

import reflection.data.BasicData;

import java.lang.reflect.Field;

public class FieldV1 {

    public static void main(String[] args) {
        Class<BasicData> helloClass = BasicData.class;

        System.out.println("====== fields() ======");
        Field[] fields = helloClass.getFields();  // 모든 public 필드 반환
        for (Field field : fields) {
            System.out.println("field = " + field);
        }

        System.out.println("====== declaredFields() ======"); // 선언한 모든 필드 반환
        Field[] declaredFields = helloClass.getDeclaredFields();
        for (Field field : declaredFields) {
            System.out.println("field = " + field);
        }

        /*
            ====== fields() ======
            field = public java.lang.String reflection.data.BasicData.publicField
            ====== declaredFields() ======
            field = public java.lang.String reflection.data.BasicData.publicField
            field = private int reflection.data.BasicData.privateField
         */
    }
}

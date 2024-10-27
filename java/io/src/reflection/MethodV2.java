package reflection;

import reflection.data.BasicData;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodV2 {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 정적 메소드 호출
        BasicData helloInstance = new BasicData();
        helloInstance.call();

        // 동적 메소드 호출
        Class<? extends BasicData> helloClass = helloInstance.getClass();
        String methodName = "hello";

        Method method1 = helloClass.getDeclaredMethod(methodName, String.class);
        Object returnValue = method1.invoke(helloInstance, "hi");
        System.out.println("returnValue = " + returnValue);
    }

    /*
        BasicData.BasicData
        BasicData.call
        BasicData.hello
        returnValue = hi hello
     */
}

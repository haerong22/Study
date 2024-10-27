package reflection;

import reflection.data.BasicData;

import java.lang.reflect.Method;

public class MethodV1 {

    public static void main(String[] args) {
        Class<BasicData> basicClass = BasicData.class;

        System.out.println("===== methods() =====");
        Method[] methods = basicClass.getMethods(); // 모든 public 메소드 조회
        for (Method method : methods) {
            System.out.println("method = " + method);
        }

        System.out.println("===== declaredMethods() =====");
        Method[] declaredMethods = basicClass.getDeclaredMethods(); // 선언한 모든 메소드 조회
        for (Method declaredMethod : declaredMethods) {
            System.out.println("declaredMethod = " + declaredMethod);
        }
    }

    /*
        ===== methods() =====
        method = public void reflection.data.BasicData.call()
        method = public java.lang.String reflection.data.BasicData.hello(java.lang.String)
        method = public boolean java.lang.Object.equals(java.lang.Object)
        method = public java.lang.String java.lang.Object.toString()
        method = public native int java.lang.Object.hashCode()
        method = public final native java.lang.Class java.lang.Object.getClass()
        method = public final native void java.lang.Object.notify()
        method = public final native void java.lang.Object.notifyAll()
        method = public final void java.lang.Object.wait(long) throws java.lang.InterruptedException
        method = public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException
        method = public final void java.lang.Object.wait() throws java.lang.InterruptedException
        ===== declaredMethods() =====
        declaredMethod = public void reflection.data.BasicData.call()
        declaredMethod = private void reflection.data.BasicData.privateMethod()
        declaredMethod = void reflection.data.BasicData.defaultMethod()
        declaredMethod = public java.lang.String reflection.data.BasicData.hello(java.lang.String)
        declaredMethod = protected void reflection.data.BasicData.protectedMethod()
     */
}

import myObject.A;
import myObject.B;

public class Java28 {
    public static void main(String[] args) {
        A a = new A();
        display(a);

        B b = new B();
        display(b);
    }

    private static void display(Object o) {
        if (o instanceof A) {
            ((A) o).go();
        } else {
            ((B) o).go();
        }
    }
}

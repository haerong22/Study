import myObject.A;

public class Java27 {
    public static void main(String[] args) {

        Object o = new A();
        ((A) o).display();
        System.out.println(o.toString());
    }
}

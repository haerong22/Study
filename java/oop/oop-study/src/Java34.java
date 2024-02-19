public class Java34 {
    public static void main(String[] args) {

        int a = 1; // 기본자료형
        Integer b = new Integer(1); // 객체자료형

        System.out.println(a);
        System.out.println(b.intValue()); // 1
        System.out.println(b.toString()); // "1"

        System.out.println("=======================");

        int c = new Integer(1); // unboxing
        Integer d = 1; // boxing

        System.out.println(c);
        System.out.println(d.intValue()); // 1
        System.out.println(d.toString()); // "1"

        System.out.println("=======================");

        Object[] obj = new Object[3];
        obj[0] = new Integer(1);
        obj[1] = new Integer(2);
        obj[2] = new Integer(3);

        System.out.println(obj[0].toString());
        System.out.println(obj[1].toString());
        System.out.println(obj[2].toString());

        System.out.println("=======================");

        Object[] obj2 = new Object[3];
        obj2[0] = 1;
        obj2[1] = 2;
        obj2[2] = 3;

        System.out.println(obj2[0].toString());
        System.out.println(obj2[1].toString());
        System.out.println(obj2[2].toString());

        System.out.println("=======================");

        // "100" + "100" = 200
        String x = "100";
        String y = "100";
        System.out.println(x + y); // "100100"

        System.out.println("=======================");

        int i = Integer.parseInt(x);
        int j = Integer.parseInt(y);

        System.out.println(i + j); // 200

        System.out.println("=======================");

        // 100 + 100 = "100100"
        String v1 = String.valueOf(i);
        String v2 = String.valueOf(j);

        System.out.println(v1 + v2);
    }
}

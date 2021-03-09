import myObject.OverLoad;

public class Java16 {
    public static void main(String[] args) {

        OverLoad ov = new OverLoad();

        ov.hap(20, 50); // hap_int_int(20, 50)
        ov.hap(23.4f, 56); // hap_float_int(23.4f, 56)
        ov.hap(56.7f, 78.9f); // hap_float_float(56.7f, 78.9f)
    }
}

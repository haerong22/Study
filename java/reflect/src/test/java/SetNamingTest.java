import org.junit.jupiter.api.Test;

public class SetNamingTest {

    @Test
    public void 키값을세터로바꾸기() {
        String key = "username";

        String firstKey = "set";
        String upperkey = key.substring(0,1).toUpperCase();
        String remainKey  = key.substring(1);

        System.out.println(firstKey);
        System.out.println(upperkey);
        System.out.println(remainKey);
    }
}

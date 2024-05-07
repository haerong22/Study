import java.util.List;

public class Collection {

    private void toArray() {
        List<String> strings = List.of("A", "B", "C");

        // 기존
        String[] strArray = new String[3];
        String[] before = strings.toArray(strArray);

        // 변경
        String[] after = strings.toArray(String[]::new);
    }
}

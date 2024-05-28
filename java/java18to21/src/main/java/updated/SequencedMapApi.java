package updated;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SequencedMap;

public class SequencedMapApi {

    public static void main(String[] args) {

        SequencedMap<Integer, String> map = new LinkedHashMap<>();
        map.put(1, "A");
        map.put(2, "B");

        Map.Entry<Integer, String> entry = map.firstEntry();
        entry.setValue("D"); // UnsupportedOperationException
    }
}

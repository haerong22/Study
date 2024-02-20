package org.example.ex04;

import java.util.HashMap;
import java.util.Map;

/**
 * Map
 */
public class Exam04 {

    public Exam04() {

        var hashMap = new HashMap<String, Object>();
        hashMap.put("key1", "value");
        hashMap.put("key2", 10);

        var map = Map.of(
                "key1", "value",
                "key2", 10
        );
    }

    public static void main(String[] args) {
        new Exam04();
    }
}

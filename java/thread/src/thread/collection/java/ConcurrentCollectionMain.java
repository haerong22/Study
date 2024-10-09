package thread.collection.java;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

public class ConcurrentCollectionMain {

    public static void main(String[] args) {
        List<Integer> copyList = new CopyOnWriteArrayList<>();

        Set<Integer> copySet = new CopyOnWriteArraySet<>();
        Set<Integer> skipSet = new ConcurrentSkipListSet<>(); // 순서 O

        Map<Integer, String> map1 = new ConcurrentHashMap<>();
        Map<Integer, String> map2 = new ConcurrentSkipListMap<>(); // 순서 O
    }
}

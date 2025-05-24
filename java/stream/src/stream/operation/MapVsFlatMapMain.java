package stream.operation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MapVsFlatMapMain {

    public static void main(String[] args) {

        List<List<Integer>> outerList = List.of(
                List.of(1, 2),
                List.of(3, 4),
                List.of(5, 6)
        );

        System.out.println("outerList = " + outerList);

        // for
        List<Integer> forResult = new ArrayList<>();
        for (List<Integer> list : outerList) {
            for (Integer i : list) {
                forResult.add(i);
            }
        }
        System.out.println("forResult = " + forResult);

        // map
        List<Stream<Integer>> mapResult = outerList.stream()
                .map(list -> list.stream())
                .toList();

        System.out.println("mapResult = " + mapResult);

        // flatMap
        List<Integer> flatMapResult = outerList.stream()
                .flatMap(list -> list.stream())
                .toList();

        System.out.println("flatMapResult = " + flatMapResult);
    }

    /*
        outerList = [[1, 2], [3, 4], [5, 6]]

        forResult = [1, 2, 3, 4, 5, 6]

        mapResult = [java.util.stream.ReferencePipeline$Head@7291c18f, java.util.stream.ReferencePipeline$Head@34a245ab, java.util.stream.ReferencePipeline$Head@7cc355be]

        flatMapResult = [1, 2, 3, 4, 5, 6]
     */
}

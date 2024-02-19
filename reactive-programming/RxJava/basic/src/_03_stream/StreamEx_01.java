package _03_stream;

import java.util.Arrays;

public class StreamEx_01 {

    public static void main(String[] args) {

        String[] words = {"a", "bc", "def"};

        // stream 사용 X
        int count = 0;
        for (String word : words) {
            if (word.length() > 2) {
                count++;
            }
        }

        System.out.println("count = " + count);

        // stream 사용
        long streamCount = Arrays.stream(words)
                .filter(word -> word.length() > 2)
                .count();

        System.out.println("streamCount = " + streamCount);
    }
}

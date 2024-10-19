package io.file.text;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ReadTextFileV2 {

    private static final String PATH = "temp/hello2.txt";

    public static void main(String[] args) throws IOException {
        String writeString = "abc\n가나다";
        System.out.println("== Write String ==");
        System.out.println(writeString);

        Path path = Path.of(PATH);

        // 파일에 쓰기
        Files.writeString(path, writeString, UTF_8);

        // 라인별로 읽기
        System.out.println("== Read String - readAllLines() ==");
        List<String> lines = Files.readAllLines(path, UTF_8); // 메모리에 한번에 올림
        for (int i = 0; i < lines.size(); i++) {
            System.out.println((i + 1) + ": " + lines.get(i));
        }

        System.out.println("== Read String - lines() ==");
        Stream<String> lineStream = Files.lines(path, UTF_8); // 한 줄씩 처리
        lineStream.forEach(System.out::println);
        lineStream.close();
    }

    /*
        == Write String ==
        abc
        가나다
        == Read String - readAllLines() ==
        1: abc
        2: 가나다
        == Read String - lines() ==
        abc
        가나다
     */
}

package hello.external;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class CommandLineV1 {

    // dataA dataB
    public static void main(String[] args) {
        Arrays.stream(args).forEach(arg -> log.info("arg = {}", arg));
    }
}

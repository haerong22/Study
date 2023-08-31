package org.example.reactorpattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args) {
        log.info("start main");
        new Reactor(8080).run();
        log.info("end main");
    }
}

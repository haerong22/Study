package hello.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class TrafficController {

    @GetMapping("/cpu")
    public String cpu() {
        log.info("cpu");
        long value = 0;

        for (int i = 0; i < 1000000000L; i++) {
            value++;
        }
        return "ok value = " + value;
    }

    private List<String> list = new ArrayList<>();

    @GetMapping("/jvm")
    public String jvm() {
        log.info("jvm");
        for (int i = 0; i < 1000000; i++) {
            list.add("hello jvm!" + i);
        }
        return "ok";
    }

    @Autowired
    DataSource dataSource;

    @GetMapping("/jdbc")
    public String jdbc() throws SQLException {
        log.info("jdbc");
        Connection conn = dataSource.getConnection();
        log.info("connection info={}", conn);

//        conn.close();
        return "ok";
    }

    @GetMapping("/error-log")
    public String errorLog() {
        log.error("error log");
        return "error log";
    }
}

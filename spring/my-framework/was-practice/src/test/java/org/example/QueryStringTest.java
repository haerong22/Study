package org.example;

import org.assertj.core.api.Assertions;
import org.example.was.QueryString;
import org.junit.jupiter.api.Test;

public class QueryStringTest {

    @Test
    void createTest() {
        QueryString queryString = new QueryString("operand1", "11");

        Assertions.assertThat(queryString).isNotNull();
    }
}

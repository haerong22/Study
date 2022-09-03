package org.example;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class QueryStringsTest {

    @Test
    void createTest() {
        QueryStrings queryStrings = new QueryStrings("operand1=11&operator=*&operand2=55");

        Assertions.assertThat(queryStrings).isNotNull();
    }
}

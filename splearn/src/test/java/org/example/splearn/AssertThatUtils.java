package org.example.splearn;

import org.assertj.core.api.AssertProvider;
import org.assertj.core.api.Assertions;
import org.springframework.test.json.JsonPathValueAssert;

import java.util.function.Consumer;

public class AssertThatUtils {
    public static Consumer<AssertProvider<JsonPathValueAssert>> notNull() {
        return v -> Assertions.assertThat(v).isNotNull();
    }

    public static Consumer<AssertProvider<JsonPathValueAssert>> equalsTo(String expected) {
        return v -> Assertions.assertThat(v).isEqualTo(expected);
    }
}

package org.example.splearn.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmailTest {

    @Test
    void equality() {
        var email1 = new Email("test@example.com");
        var email2 = new Email("test@example.com");

        assertThat(email1).isEqualTo(email2);
    }

}
package org.example.splearn.adapter.integration;

import org.example.splearn.domain.shared.Email;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.StdIo;
import org.junitpioneer.jupiter.StdOut;

import static org.assertj.core.api.Assertions.assertThat;

class DummyEmailSenderTest {

    @Test
    @StdIo
    void dummyEmailSender(StdOut out) {
        DummyEmailSender dummyEmailSender = new DummyEmailSender();

        dummyEmailSender.send(new Email("bobby@email.com"), "subject", "body");

        assertThat(out.capturedLines()[0]).isEqualTo("DummyEmailSender send email: Email[address=bobby@email.com]");
    }
}
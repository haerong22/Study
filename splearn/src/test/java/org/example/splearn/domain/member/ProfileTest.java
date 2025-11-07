package org.example.splearn.domain.member;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProfileTest {

    @Test
    void profile() {
        new Profile("bobby");
        new Profile("bobby123");
        new Profile("123");
    }

    @Test
    void profileFail() {
        assertThatThrownBy(() -> new Profile("")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("toolongtoolongtoolongtoolongtoolong")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("A")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("프로필")).isInstanceOf(IllegalArgumentException.class);
    }

}
package com.example.effectivejava.chapter01.item05.dependencyinjection;

import com.example.effectivejava.chapter01.item05.DefaultDictionary;
import org.junit.jupiter.api.Test;

class SpellCheckerTest {

    @Test
    void isValid() {
        SpellChecker spellChecker = new SpellChecker(new DefaultDictionary());
        spellChecker.isValid("test");
    }

}
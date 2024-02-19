package com.example.effectivejava.chapter01.item05.singleton;

import com.example.effectivejava.chapter01.item05.DefaultDictionary;
import com.example.effectivejava.chapter01.item05.Dictionary;

import java.util.List;

public class SpellChecker {

    private final Dictionary dictionary = new DefaultDictionary();

    private SpellChecker() {}

    public static final SpellChecker INSTANCE = new SpellChecker();

    public boolean isValid(String word) {
        // TODO 여기 SpellChecker 코드
        return dictionary.contains(word);
    }

    public List<String> suggestions(String typo) {
        // TODO 여기 SpellChecker 코드
        return dictionary.closeWordsTo(typo);
    }
}
package com.example.effectivejava.chapter01.item05.springioc;

import com.example.effectivejava.chapter01.item05.Dictionary;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpellChecker {

    private Dictionary dictionary;

    public SpellChecker(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public boolean isValid(String word) {
        // TODO 여기 SpellChecker 코드
        return dictionary.contains(word);
    }

    public List<String> suggestions(String typo) {
        // TODO 여기 SpellChecker 코드
        return dictionary.closeWordsTo(typo);
    }
}

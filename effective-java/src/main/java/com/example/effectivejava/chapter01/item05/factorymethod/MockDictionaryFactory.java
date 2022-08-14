package com.example.effectivejava.chapter01.item05.factorymethod;

import com.example.effectivejava.chapter01.item05.Dictionary;
import com.example.effectivejava.chapter01.item05.MockDictionary;

public class MockDictionaryFactory implements DictionaryFactory {
    @Override
    public Dictionary getDictionary() {
        return new MockDictionary();
    }
}

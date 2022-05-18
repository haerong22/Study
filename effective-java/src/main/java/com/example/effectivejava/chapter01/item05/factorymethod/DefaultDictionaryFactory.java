package com.example.effectivejava.chapter01.item05.factorymethod;

import com.example.effectivejava.chapter01.item05.DefaultDictionary;
import com.example.effectivejava.chapter01.item05.Dictionary;

public class DefaultDictionaryFactory implements DictionaryFactory {
    @Override
    public Dictionary getDictionary() {
        return new DefaultDictionary();
    }
}

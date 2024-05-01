package com.bobby.api.service;

import com.bobby.domain.service.StringRepository;

public class StringSaveComponent {

    private final StringRepository memoryStringRepository = StringRepositoryLoader.getDefaultRepository();

    public void logic() {
        memoryStringRepository.save("문자열");
    }

    public static void main(String[] args) {
        StringSaveComponent component = new StringSaveComponent();
        component.logic();
    }
}

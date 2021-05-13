package com.example.springbatch.utils;

import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class FileUtils {

    @SneakyThrows
    public static Stream<File> stream(Path path) {
        return Files.list(path).map(Path::toFile);
    }
}

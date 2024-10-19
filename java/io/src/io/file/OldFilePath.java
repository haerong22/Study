package io.file;

import java.io.File;
import java.io.IOException;

public class OldFilePath {

    public static void main(String[] args) throws IOException {
        File file = new File("temp/..");
        System.out.println("path = " + file.getPath());

        // 절대 경로(입력한 모든 경로)
        System.out.println("Absolute path = " + file.getAbsolutePath());

        // 정규 경롞(경로 계산이 끝난 경로)
        System.out.println("Canonical path = " + file.getCanonicalPath());

        File[] files = file.listFiles();
        for (File f : files) {
            System.out.println((f.isFile() ? "F" : "D") + " | " + f.getName());
        }
    }

    /*
        path = temp/..
        Absolute path = /Users/bobby/Desktop/kim/study/Study/java/io/temp/..
        Canonical path = /Users/bobby/Desktop/kim/study/Study/java/io
        D | temp
        F | io.iml
        D | out
        F | .gitignore
        D | .idea
        D | src
     */
}

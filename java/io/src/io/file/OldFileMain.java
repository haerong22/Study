package io.file;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class OldFileMain {

    public static void main(String[] args) throws IOException {
        File file = new File("temp/example.txt");
        File directory = new File("temp/exampleDir");

        // 파일, 디렉토리 존재 여부 확인
        System.out.println("File exists: " + file.exists());

        // 파일 생성
        boolean created = file.createNewFile();
        System.out.println("File created: " + created);

        // 디렉토리 생성
        boolean dirCreated = directory.mkdir();
        System.out.println("Directory created: " + dirCreated);

        // 파일, 디렉토리 삭제
//        boolean deleted = file.delete();
//        System.out.println("File deleted: " + deleted);

        // 파일인지 확인
        System.out.println("Is file: " + file.isFile());

        // 디렉토리인지 확인
        System.out.println("Is Directory: " + directory.isDirectory());

        // 파일, 디렉토리 이름 반환
        System.out.println("File name: " + file.getName());

        // 파일 크기 바이트 단위로 반환
        System.out.println("File size: " + file.length() + " bytes");

        // 파일 이름 변경, 이동
        File newFile = new File("temp/newExample.txt");
        boolean renamed = file.renameTo(newFile);
        System.out.println("File renamed: " + renamed);

        // 마지막으로 수정된 시간 반환
        long lastModified = newFile.lastModified();
        System.out.println("Last modified: " + new Date(lastModified));
    }

    /*
        File exists: false
        File created: true
        Directory created: false
        Is file: true
        Is Directory: true
        File name: example.txt
        File size: 0 bytes
        File renamed: true
        Last modified: Sat Oct 19 20:14:27 KST 2024
     */
}

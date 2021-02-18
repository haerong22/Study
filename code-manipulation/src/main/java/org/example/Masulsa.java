package org.example;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.pool.TypePool;

import java.io.File;
import java.io.IOException;

public class Masulsa {

    public static void main(String[] args) {

        System.out.println(new Moja().pullOut());



        /* ============================================================= */

//        ClassLoader classLoader = Masulsa.class.getClassLoader();
//        TypePool typePool = TypePool.Default.of(classLoader);
//
//        /**
//         * redefine() : Moja 클래스를 재정의
//         * method() : Moja 클래스의 pullOut 메소드의 값을 Rabbit 으로 재정의
//         * make().saveIn() : 재정의 후 지정한 위치에 저장 ( Moja.class 가 생성되는 파일 위치 지정 )
//         */
//        try {
//            new ByteBuddy().redefine(typePool.describe("org.example.Moja").resolve(),
//                    ClassFileLocator.ForClassLoader.of(classLoader))
//                    .method(ElementMatchers.named("pullOut")).intercept(FixedValue.value("Rabbit!!"))
//                    .make().saveIn(new File("C:\\Users\\Woojin\\Desktop\\study\\Study\\code-manipulation\\target\\classes\\"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(new Moja().pullOut());
    }
}

package com.example.apptest.study;

import org.junit.Before;
import org.junit.Test;

public class StudyJUnit4Test {

    @Before
    public void before() {
        System.out.println("before");
    }

    @Test
    public void junit4_test() {
        System.out.println("JUnit4_test");
    }
}

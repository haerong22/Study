package com.bobby.api.feature;

import java.util.List;
import java.util.stream.Collectors;

public class StackWalkerFeature {

    public static void main(String[] args) {
        callA();
    }

    private static void callA() {
        callB();
    }

    private static void callB() {
        callC();
    }

    private static void callC() {
        List<String> walk = StackWalker.getInstance()
                .walk(s -> s.map(StackWalker.StackFrame::getMethodName)
                        .collect(Collectors.toList()));

        for (String s : walk) {
            System.out.println("Stack : " + s);
        }
    }
}

package com.bobby.api.feature;

public class InnerClass {

    public static void main(String[] args) {

        Inner<Integer> inner = new Inner<>(3); // diamond syntax 사용 가능
    }

    public static class Inner<T> {
        private final T t;

        public Inner(T t) {
            this.t = t;
        }
    }
}

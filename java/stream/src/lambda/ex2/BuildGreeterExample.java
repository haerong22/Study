package lambda.ex2;

import lambda.StringFunction;

public class BuildGreeterExample {

    public static StringFunction buildGreeter(String greeting) {
        return null;
    }

    public static void main(String[] args) {
        StringFunction helloGreeter = buildGreeter("Hello");
        StringFunction hiGreeter = buildGreeter("Hi");

        helloGreeter.apply("Java"); // Hello, Java
        hiGreeter.apply("Lambda"); // Hello, Lambda
    }
}

package lambda.ex2;

import lambda.StringFunction;

public class BuildGreeterExampleAnswer {

    public static StringFunction buildGreeter(String greeting) {
        return name -> greeting + ", " + name;
    }

    public static void main(String[] args) {
        StringFunction helloGreeter = buildGreeter("Hello");
        StringFunction hiGreeter = buildGreeter("Hi");

        String hello = helloGreeter.apply("Java");
        String hi = hiGreeter.apply("Lambda");

        System.out.println(hello);
        System.out.println(hi);
    }

    /*
        Hello, Java
        Hi, Lambda
     */
}

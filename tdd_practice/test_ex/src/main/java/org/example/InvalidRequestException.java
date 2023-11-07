package org.example;

public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException() {
        super("Invalid input size, you must input 3 length");
    }
}

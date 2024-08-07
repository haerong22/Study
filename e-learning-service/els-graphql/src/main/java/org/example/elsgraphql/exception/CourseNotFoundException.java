package org.example.elsgraphql.exception;

public class CourseNotFoundException extends RuntimeException {

    public CourseNotFoundException(String message) {
        super(message);
    }
}

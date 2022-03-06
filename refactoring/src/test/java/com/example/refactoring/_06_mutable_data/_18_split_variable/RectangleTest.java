package com.example.refactoring._06_mutable_data._18_split_variable;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RectangleTest {

    @Test
    void updateGeomerty() {
        Rectangle rectangle = new Rectangle();
        rectangle.updateGeometry(10, 5);
        assertEquals(50d,rectangle.getArea());
        assertEquals(30d, rectangle.getPerimeter());

        rectangle.updateGeometry(5, 5);
        assertEquals(25d,rectangle.getArea());
        assertEquals(20d, rectangle.getPerimeter());
    }

}
package com.example.refactoring._06_mutable_data._18_split_variable.before;

public class Rectangle {

    private double perimeter;
    private double area;

    public void updateGeometry(double height, double width) {
        double temp = 2 * (height + width);
        System.out.println("Perimeter: " + temp);
        perimeter = temp;

        temp = height * width;
        System.out.println("Area: " + temp);
        area = temp;
    }

    public double getPerimeter() {
        return perimeter;
    }

    public double getArea() {
        return area;
    }
}

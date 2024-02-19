package com.example.refactoring._06_mutable_data._18_split_variable;

public class Rectangle {

    private double perimeter;
    private double area;

    public void updateGeometry(double height, double width) {
        // 가독성을 위해 변수의 이름을 역할에 맞도록 변경 변수의 값이 변경되지 않도록 final 키워드 사용
        final double perimeter = 2 * (height + width);
        System.out.println("Perimeter: " + perimeter);
        this.perimeter = perimeter;

        final double area = height * width;
        System.out.println("Area: " + area);
        this.area = area;
    }

    public double getPerimeter() {
        return perimeter;
    }

    public double getArea() {
        return area;
    }
}

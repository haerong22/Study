package org.example;

import java.util.Scanner;

public class CalculationRequestReader {

    public static CalculationRequest read() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter two numbers and an operator (e.g 1 + 2): ");

        String[] parts = scanner.nextLine().split(" ");

        return CalculationRequest.of(parts);
    }
}

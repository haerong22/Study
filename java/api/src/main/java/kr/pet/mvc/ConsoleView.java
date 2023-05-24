package kr.pet.mvc;

import java.util.List;
import java.util.Scanner;

public class ConsoleView {

    private Scanner scanner = new Scanner(System.in);

    public String getPhoneNumber() {
        System.out.print("전화번호를 입력하세요: ");
        return scanner.nextLine();
    }

    public Customer getCustomerInfo() {
        System.out.println("신규 고객 정보를 입력하세요. ");

        System.out.print("전화번호: ");
        String phoneNumber = scanner.nextLine();

        System.out.print("주인 이름: ");
        String ownerName = scanner.nextLine();

        System.out.print("동물 이름: ");
        String petName = scanner.nextLine();

        System.out.print("주소: ");
        String address = scanner.nextLine();

        System.out.print("종류: ");
        String species = scanner.nextLine();

        System.out.print("출생년(yyyy): ");
        int birthYear = scanner.nextInt();
        scanner.nextLine();

        return new Customer(phoneNumber, ownerName, petName, address, species, birthYear);
    }

    public MedicalRecord getMedicalRecordInfo() {
        System.out.print("진료일을 입력하세요: ");
        String date = scanner.nextLine();

        System.out.print("진료내용을 입력하세요: ");
        String content = scanner.nextLine();

        return new MedicalRecord(null, date, content);
    }

    public void printMedicalRecordInfo(Customer customer) {
        List<MedicalRecord> records = customer.getMedicalRecords();

        for (MedicalRecord record : records) {
            System.out.println("-  진료일: " + record.getDate());
            System.out.println("   진료내용: " + record.getContent());
            System.out.println("   주인 이름: " + customer.getOwner());
            System.out.println("   동물 이름: " + customer.getPetName());
            System.out.println("   주소: " + customer.getAddress());
            System.out.println("   종류: " + customer.getSpecies());
            System.out.println("   출생년도: " + customer.getBirthYear());
        }
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}

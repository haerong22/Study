package kr.pet.mvc;

import java.util.List;
import java.util.Scanner;

public class PetMain {

    public static void main(String[] args) {
        MedicalRecordController recordController = new MedicalRecordController();
        CustomerController customerController = new CustomerController(recordController);
        ConsoleView view = new ConsoleView();

        while (true) {
            System.out.println("===애완동물 진료관리 시스템===");
            System.out.println("1. 신규 고객 정보 입력");
            System.out.println("2. 진료 기록 저장");
            System.out.println("3. 진료 기록 조회");
            System.out.println("4. 진료 기록 삭제");
            System.out.println("5. 종료");
            System.out.print("원하는 기능을 선택하세요: ");

            Scanner scanner = new Scanner(System.in);
            int select = scanner.nextInt();

            switch (select) {
                case 1:
                    Customer newCustomer = view.getCustomerInfo();
                    String phoneNumber = newCustomer.getPhoneNumber();
                    if (customerController.isPhoneNumberExist(phoneNumber)) {
                        view.printMessage("이미 등록된 전화번호 입니다.");
                        continue;
                    }
                    customerController.addCustomer(newCustomer);
                    view.printMessage("고객 정보가 추가되었습니다.");
                    break;

                case 2:
                    phoneNumber = view.getPhoneNumber();
                    if (customerController.findCustomer(phoneNumber) == null) {
                        view.printMessage("해당 전화번호를 가진 고객이 없습니다.");
                        break;
                    }

                    Customer customer = customerController.findCustomer(phoneNumber);
                    MedicalRecord newRecord = view.getMedicalRecordInfo();
                    newRecord.setPhoneNumber(phoneNumber);
                    recordController.addMedicalRecord(newRecord);
                    customer.addMedicalRecords(newRecord);
                    view.printMessage("진료기록이 저장되었습니다.");
                    break;

                case 3:
                    phoneNumber = view.getPhoneNumber();
                    List<MedicalRecord> records = recordController.findMedicalRecord(phoneNumber);
                    if (records.isEmpty()) {
                        view.printMessage("해당 전화번호를 가진 진료기록이 없습니다.");
                        break;
                    }

                    customer = customerController.findCustomer(phoneNumber);
                    view.printMedicalRecordInfo(customer);
                    break;

                case 4:
                    phoneNumber = view.getPhoneNumber();
                    if (customerController.findCustomer(phoneNumber) == null) {
                        view.printMessage("해당 전화번호를 가진 고객이 없습니다.");
                        break;
                    }

                    recordController.removeMedicalRecord(phoneNumber);
                    view.printMessage("진료기록 정보가 삭제되었습니다.");
                    break;

                case 5:
                    view.printMessage("프로그램을 종료합니다.");
                    return;

                default:
                    view.printMessage("잘못된 선택입니다.");
                    break;
            }
        }
    }
}

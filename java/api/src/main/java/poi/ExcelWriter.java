package poi;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ExcelWriter {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<MemberVo> members = new ArrayList<>();

        while (true) {
            System.out.print("이름을 입력하세요:");
            String name = scanner.nextLine();
            if (name.equals("quit")) {
                break;
            }

            System.out.print("나이을 입력하세요:");
            int age = scanner.nextInt();
            scanner.nextLine();

            System.out.print("생년월일을 입력하세요:");
            String birthDate = scanner.nextLine();

            System.out.print("전화번호를 입력하세요:");
            String phone = scanner.nextLine();

            System.out.print("주소를 입력하세요:");
            String address = scanner.nextLine();

            System.out.print("결혼여부를 입력하세요 (true/false):");
            boolean isMarried = scanner.nextBoolean();
            scanner.nextLine();

            MemberVo member = new MemberVo(name, age, birthDate, phone, address, isMarried);
            members.add(member);
        }
        scanner.close();

        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("회원 정보");

            // 헤더
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("이름");
            header.createCell(1).setCellValue("나이");
            header.createCell(2).setCellValue("생년월일");
            header.createCell(3).setCellValue("전화번호");
            header.createCell(4).setCellValue("주소");
            header.createCell(5).setCellValue("결혼여부");

            // 데이터 생성
            for (int i = 0; i < members.size(); i++) {
                MemberVo member = members.get(i);
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(member.getName());
                row.createCell(1).setCellValue(member.getAge());
                row.createCell(2).setCellValue(member.getBirthDate());
                row.createCell(3).setCellValue(member.getPhone());
                row.createCell(4).setCellValue(member.getAddress());
                row.createCell(5).setCellValue(member.isMarried());
            }

            final String filename = "members.xlsx";
            FileOutputStream outputStream = new FileOutputStream(filename);
            workbook.write(outputStream);
            workbook.close();
            System.out.println("엑셀 파일 저장 완료: " + filename);
        } catch (IOException e) {
            System.out.println("오류 발생");
            e.printStackTrace();
        }

    }
}

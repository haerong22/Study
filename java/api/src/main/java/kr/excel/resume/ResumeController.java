package kr.excel.resume;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

public class ResumeController {

    private final ResumeView view;
    private final Workbook workbook;

    public ResumeController() {
        this.view = new ResumeView();
        this.workbook = new XSSFWorkbook();
    }

    public static void main(String[] args) {
        ResumeController controller = new ResumeController();
        controller.createResume();
    }

    private void createResume() {
        PersonInfo personInfo = view.inputPersonInfo();
        List<Education> educationList = view.inputEducationList();
        List<Career> careerList = view.inputCareerList();
        String selfIntroduction = view.inputSelfIntroduction();

        createResumeSheet(personInfo, educationList, careerList);
        createSelfIntroductionSheet(selfIntroduction);

        saveWorkbookToFile();

        System.out.println("이력서 생성이 완료되었습니다.");
    }

    private void createResumeSheet(PersonInfo personInfo, List<Education> educationList, List<Career> careerList) {
        Sheet sheet = workbook.createSheet("이력서");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("사진");
        headerRow.createCell(1).setCellValue("이름");
        headerRow.createCell(2).setCellValue("이메일");
        headerRow.createCell(3).setCellValue("주소");
        headerRow.createCell(4).setCellValue("전화번호");
        headerRow.createCell(5).setCellValue("생년월일");

        Row dataRow = sheet.createRow(1);
        String photoFilename = personInfo.getPhoto();

        try (InputStream photoStream = new FileInputStream(photoFilename)) {

            BufferedImage originImage = ImageIO.read(photoStream);

            int newWidth = (int) (35 * 2.83465);
            int newHeight = (int) (45 * 2.83465);
            Image resizedImage = originImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            BufferedImage resizedBufferedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g2d = resizedBufferedImage.createGraphics();
            g2d.drawImage(resizedImage, 0, 0, null);
            g2d.dispose();

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(resizedBufferedImage, "png", os);
            byte[] imageBytes = os.toByteArray();
            int imageIndex = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);

            XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();
            XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0, 0, 1, 1, 2);
            drawing.createPicture(anchor, imageIndex);

            dataRow.setHeightInPoints(newHeight * 72 / 96);

            int columnWidth = (int) Math.floor(((float) newWidth / (float) 8) * 256);
            sheet.setColumnWidth(0, columnWidth);

        } catch (IOException e) {
            e.printStackTrace();
        }

        dataRow.createCell(1).setCellValue(personInfo.getName());
        dataRow.createCell(2).setCellValue(personInfo.getEmail());
        dataRow.createCell(3).setCellValue(personInfo.getAddress());
        dataRow.createCell(4).setCellValue(personInfo.getPhoneNumber());
        dataRow.createCell(5).setCellValue(personInfo.getBirthDate());

        int educationStartRow = 3;
        Row educationHeaderRow = sheet.createRow(educationStartRow - 1);
        educationHeaderRow.createCell(0).setCellValue("졸업년도");
        educationHeaderRow.createCell(1).setCellValue("학교명");
        educationHeaderRow.createCell(2).setCellValue("전공");
        educationHeaderRow.createCell(3).setCellValue("졸업여부");

        int educationRowNum = educationStartRow;
        for (Education education : educationList) {
            Row educationDataRow = sheet.createRow(educationRowNum++);
            educationDataRow.createCell(0).setCellValue(education.getGraduationYear());
            educationDataRow.createCell(1).setCellValue(education.getSchoolName());
            educationDataRow.createCell(2).setCellValue(education.getMajor());
            educationDataRow.createCell(3).setCellValue(education.getGraduationStatus());
        }

        int careerStartRow = educationRowNum + 1;
        Row careerHeaderRow = sheet.createRow(careerStartRow - 1);
        careerHeaderRow.createCell(0).setCellValue("근무기간");
        careerHeaderRow.createCell(1).setCellValue("근무처");
        careerHeaderRow.createCell(2).setCellValue("담당업무");
        careerHeaderRow.createCell(3).setCellValue("근속연수");

        int careerRowNum = careerStartRow;
        for (Career career : careerList) {
            Row careerDataRow = sheet.createRow(careerRowNum++);
            careerDataRow.createCell(0).setCellValue(career.getWorkPeriod());
            careerDataRow.createCell(1).setCellValue(career.getCompanyName());
            careerDataRow.createCell(2).setCellValue(career.getJobTitle());
            careerDataRow.createCell(3).setCellValue(career.getEmploymentYears());
        }
    }

    private void createSelfIntroductionSheet(String selfIntroduction) {
        Sheet sheet = workbook.createSheet("자기소개서");

        Row dataRow = sheet.createRow(0);
        Cell selfIntroductionCell = dataRow.createCell(0);
        selfIntroductionCell.setCellStyle(getWrapCellStyle());
        selfIntroductionCell.setCellValue(
                new XSSFRichTextString(selfIntroduction.replaceAll("\n", String.valueOf((char) 10)))
        );
    }

    private XSSFCellStyle getWrapCellStyle() {
        XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
        style.setWrapText(true);
        return style;
    }

    private void saveWorkbookToFile() {
        try (FileOutputStream fo = new FileOutputStream("이력서.xlsx")) {
            workbook.write(fo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

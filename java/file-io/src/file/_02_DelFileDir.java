package file;

import java.io.File;

public class _02_DelFileDir {

    public static void main(String[] args) {
        String tmpPath = "./tmp";
        File targetDir = new File(tmpPath);
        if (targetDir.exists()) {
            File testFile01 = new File(tmpPath + "/test01.txt");
            File testFile02 = new File(tmpPath + "/test02.txt");

            // 파일이 있는 디렉토리 삭제 X
            if (targetDir.delete()) {
                System.out.println("Tmp Directory Deleted");
            } else {
                System.out.println("Failed to Delete Tmp Directory");
            }
        }
    }
    /*
        Failed to Delete Tmp Directory
     */
}

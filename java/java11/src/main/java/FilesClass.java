import java.nio.file.Files;
import java.nio.file.Paths;

public class FilesClass {

    public static void main(String[] args) throws Exception {
        var path = Paths.get("./test.txt");
        String str = Files.readString(path);
        System.out.println(str);


        Files.writeString(path, "Hello Java!!");
    }
}

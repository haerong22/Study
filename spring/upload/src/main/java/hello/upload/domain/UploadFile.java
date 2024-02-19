package hello.upload.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadFile {

    private String uploadFileName;
    private String storeFileName;
}

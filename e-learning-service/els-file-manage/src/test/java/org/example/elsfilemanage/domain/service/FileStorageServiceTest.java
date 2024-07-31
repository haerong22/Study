package org.example.elsfilemanage.domain.service;

import org.example.elsfilemanage.domain.entity.SessionFile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class FileStorageServiceTest {

    @InjectMocks
    private FileStorageService fileStorageService;

    @Test
    public void test_store_file_success() throws IOException {
        // given
        Long sessionId = 1L;
        String originalFileName = "test.mp4";
        MockMultipartFile mockFile = new MockMultipartFile("file", originalFileName,
                "video/mp4", new ByteArrayInputStream("some data".getBytes()));
        String uploadDir = "./uploads";
        ReflectionTestUtils.setField(fileStorageService, "uploadDir", uploadDir);

        // when
        SessionFile storedFile = fileStorageService.storeFile(mockFile, sessionId);

        // then
        assertThat(storedFile).isNotNull();
        assertThat(storedFile.getSessionId()).isEqualTo(sessionId);
        assertThat(storedFile.getFileName().contains("_" + originalFileName)).isTrue();
        assertThat(storedFile.getFilePath().contains(storedFile.getFileName())).isTrue();
        assertThat(storedFile.getFileType()).isEqualTo("mp4");
    }

    @Test
    public void test_store_file_throws_exception_for_invalid_path() throws IOException {
        // given
        Long sessionId = 1L;
        String invalidFileName = "../test.mp4";
        MockMultipartFile mockFile = new MockMultipartFile("file", invalidFileName,
                "video/mp4", new ByteArrayInputStream("some data".getBytes()));

        // when, then
        assertThatThrownBy(() -> fileStorageService.storeFile(mockFile, sessionId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Filename contains invalid path sequence");
    }
}
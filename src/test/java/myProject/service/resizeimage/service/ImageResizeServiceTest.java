package myProject.service.resizeimage.service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ImageResizeServiceTest {

    private ImageResizeService imageResizeService;

    @BeforeEach
    void setUp() {
        imageResizeService = new ImageResizeService();
    }

    @Test
    void resizeSingleImageTest() throws IOException {
        // 가상의 이미지 파일 생성
        MockMultipartFile mockFile = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test data".getBytes());
        MultipartFile[] files = new MultipartFile[] {mockFile};

        // 이미지 리사이징 테스트
        byte[] resizedImage = imageResizeService.resizeImage(files, 100, 100);

        // 결과 검증
        assertNotNull(resizedImage);
        assertTrue(resizedImage.length > 0);
    }

    @Test
    void resizeMultipleImagesAsZipTest() throws IOException {
        // 가상의 이미지 파일들 생성
        MockMultipartFile mockFile1 = new MockMultipartFile("file", "test1.jpg", "image/jpeg", "test data 1".getBytes());
        MockMultipartFile mockFile2 = new MockMultipartFile("file", "test2.jpg", "image/jpeg", "test data 2".getBytes());
        MultipartFile[] files = new MultipartFile[] {mockFile1, mockFile2};

        // 이미지 리사이징 및 ZIP 파일 생성 테스트
        byte[] zipFile = imageResizeService.resizeImage(files, 100, 100);

        // 결과 검증
        assertNotNull(zipFile);
        assertTrue(zipFile.length > 0);
    }
}
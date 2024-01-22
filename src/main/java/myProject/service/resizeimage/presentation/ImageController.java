package myProject.service.resizeimage.presentation;

import myProject.service.resizeimage.service.ImageResizeService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/image")
public class ImageController {

    private final ImageResizeService imageResizeService;

    public ImageController(ImageResizeService imageResizeService) {
        this.imageResizeService = imageResizeService;
    }

    @PostMapping("/resize")
    public ResponseEntity<?> resizeImages(@RequestParam("files") MultipartFile[] files,
                                          @RequestParam("width") int width,
                                          @RequestParam("height") int height) {
        try {
            List<InputStreamResource> resizedImages = new ArrayList<>();

            for (MultipartFile file : files) {
                byte[] resizedImageBytes = imageResizeService.resizeImage(file, width, height);
                resizedImages.add(new InputStreamResource(new ByteArrayInputStream(resizedImageBytes)));
            }

            // 로직에 따라 여러 이미지를 반환하는 방식을 결정하세요. 예를 들어, zip 파일로 묶거나,
            // 각각의 이미지를 반환할 수 있습니다.

            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resizedImages);
        } catch (IOException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error resizing images: " + e.getMessage());
        }
    }
}